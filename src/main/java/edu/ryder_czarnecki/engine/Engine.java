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

import static edu.ryder_czarnecki.engine.util.Compare.compare;
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

        long begin = System.nanoTime();
        final int maxGenerations = generationalSetup.maxGenerations();
        final int generationSize = generationalSetup.generationSize();
        for (int i = 0; i < maxGenerations; i++) {
            final int finalI = i;
            CountDownLatch latch = new CountDownLatch(generationalSetup.generationSize());
            for (int j = 0; j < generationSize; j++) {
                final int finalJ = j;
                threadFactory.newThread(() -> {
                    List<ProcessInstance> variation;
                    if (finalI == 0) {
                        variation = permute(inputInstance.processList());
                    } else if (finalJ < mutationPart) {
                        variation = mutate(
                                previousGenerations.get().get(finalJ).processList(),
                                generationalSetup.mutationIntensity()
                        );
                    } else if (finalJ < mutationPart + randomPart) {
                        variation = permute(inputInstance.processList());
                    } else {
                        variation = crossover(
                                previousGenerations.get().get(finalJ).processList(),
                                previousGenerations.get().get(generationSize - finalJ).processList(),
                                generationalSetup.crossoverIntensity()
                        );
                    }

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
                    .stream()
                    .sorted(Comparator.comparingInt(TemporalInstance::cMax))
                    .limit(generationSize)
                    .toList()));

            log.info("Generation " + i + " finished\n" + previousGenerations.get().size() + " instances in next generation\nSmallest CMax: " + previousGenerations.get().getFirst().cMax());
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

    @Override
    public int getCMax() {
        return resultInstance.getCMax();
    }

    @Override
    public String prettyPrint() {
        return "Evaluation time: " + evaluationTime + "ns\n" + ProcessManager.prettyPrint(resultInstance.getProcessStacks());
    }
}
