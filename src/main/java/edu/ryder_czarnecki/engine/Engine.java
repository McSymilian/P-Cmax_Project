package edu.ryder_czarnecki.engine;

import edu.ryder_czarnecki.data_input.InputStrategy;
import edu.ryder_czarnecki.data_input.StandardInputStrategy;
import edu.ryder_czarnecki.engine.util.TemporalInstance;
import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.instance.ResultInstance;
import edu.ryder_czarnecki.process.ProcessInstance;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManagerFactory;
import edu.ryder_czarnecki.process_manager.ProcessManagerOutput;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import static edu.ryder_czarnecki.engine.util.Crossover.crossover;
import static edu.ryder_czarnecki.engine.util.Mutate.mutate;
import static edu.ryder_czarnecki.engine.util.Permute.permute;

@Log
public class Engine implements ProcessManagerOutput {
    public static final InputStrategy SEQUENTIAL_INPUT = new StandardInputStrategy();

    private final DataInstance inputInstance;
    private final ProcessManagerFactory processManagerFactory;
    private final GenerationalSetup generationalSetup;
    @Getter
    private Long evaluationTime = 0L;

    private ResultInstance resultInstance;
    private final ThreadFactory threadFactory;

    @SneakyThrows
    @Builder
    public Engine(
            InputStream stream,
            InputStrategy strategy,
            ProcessManagerFactory processManagerFactory,
            GenerationalSetup generationalSetup,
            ThreadFactory threadFactory
    ) {
        inputInstance = strategy.parse(stream);
        this.processManagerFactory = processManagerFactory;
        this.generationalSetup = generationalSetup;
        this.threadFactory = threadFactory;
    }

    public ResultInstance mashupAnalyze() {
        final int mutationPart = (int) (generationalSetup.mutationPart() * generationalSetup.generationSize());
        final int randomPart = (int) (generationalSetup.randomPart() * generationalSetup.generationSize());
        AtomicReference<List<TemporalInstance>> previousGenerations = new AtomicReference<>(new ArrayList<>());

        final int maxGenerations = generationalSetup.maxGenerations();
        final int generationSize = generationalSetup.generationSize();

        final int significantGenerations = maxGenerations / 10;

        long begin = System.nanoTime();
        for (int i = 0; i < maxGenerations; i++) {
            final int finalI = i;
            CountDownLatch latch = new CountDownLatch(generationalSetup.generationSize());
            for (int j = 0; j < generationSize; j++) {
                final int finalJ = j;
                threadFactory.newThread(() -> {
                    List<ProcessInstance> variation = getPermutation(
                            finalI,
                            finalJ,
                            mutationPart,
                            previousGenerations,
                            randomPart,
                            generationSize,
                            inputInstance,
                            generationalSetup
                    );

                    ProcessManager processManager = processManagerFactory.create(inputInstance.processorsCount());
                    processManager.addProcesses(variation);
                    synchronized (previousGenerations) {
                        previousGenerations.get().add(new TemporalInstance(processManager.getCMax(), variation));
                    }
                    latch.countDown();
                }).start();
            }

            try {
                latch.await(generationalSetup.maxGenerationTime(), generationalSetup.maxGenerationTimeUnit());
            } catch (InterruptedException ignored) {
                log.warning("Generation " + i + " was interrupted");
            }

            previousGenerations.set(new ArrayList<>(previousGenerations
                    .get()
                    .parallelStream()
                    .sorted(Comparator.comparingInt(TemporalInstance::cMax))
                    .limit(generationSize)
                    .toList()));

            if(finalI % significantGenerations == 0)
                log.info("Generation %d finished\nSmallest CMax: %d\nPassed time: %fs"
                        .formatted(
                                i,
                                previousGenerations.get().getFirst().cMax(),
                                (System.nanoTime() - begin) / 10E9
                        )
                );

        }

        evaluationTime = System.nanoTime() - begin;

        ProcessManager processManager = processManagerFactory.create(inputInstance.processorsCount());
        processManager.addProcesses(previousGenerations.get().getFirst().processList());

        resultInstance = ResultInstance.builder()
                .processStacks(processManager.getProcessStacks())
                .evaluationTime(evaluationTime)
                .cMax(processManager.getCMax())
                .build();

        return resultInstance;
    }

    private static List<ProcessInstance> getPermutation(
            int finalI,
            int finalJ,
            int mutationPart,
            AtomicReference<List<TemporalInstance>> previousGenerations,
            int randomPart,
            int generationSize,
            DataInstance inputInstance,
            GenerationalSetup generationalSetup
    ) {
        if (finalI == 0)
            return permute(inputInstance.processList());

        if (finalJ < mutationPart)
            return  mutate(
                    previousGenerations.get().get(finalJ).processList(),
                    generationalSetup.mutationIntensity()
            );

        if (finalJ < mutationPart + randomPart)
            return permute(inputInstance.processList());

        return crossover(
                previousGenerations.get().get(finalJ - mutationPart - randomPart).processList(),
                previousGenerations.get().get(generationSize - finalJ).processList(),
                generationalSetup.crossoverIntensity()
        );
    }

    @Override
    public int getCMax() {
        return resultInstance.getCMax();
    }

    @Override
    public String prettyPrint() {
        return "Evaluation time: " + evaluationTime + "ns\n" + ProcessManager.prettyPrint(resultInstance.getProcessStacks());
    }
}
