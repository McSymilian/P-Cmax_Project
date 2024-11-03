package edu.ryder_czarnecki.generator;

import edu.ryder_czarnecki.data_input.InputStrategy;
import edu.ryder_czarnecki.data_input.StandardInputStrategy;
import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.process.Process;
import edu.ryder_czarnecki.process_manager.GreedyProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManagerOutput;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Engine implements ProcessManagerOutput {
    public static final InputStrategy SEQUENCIAL_INPUT = new StandardInputStrategy();

    private final DataInstance inputInstance;
    private final GreedyProcessManager. GreedyProcessManagerBuilder processManagerBuilder;
    private final GenerationalSetup generationalSetup;
    @Getter
    private Long evaluationTime = 0L;

    private final AtomicInteger minCmax = new AtomicInteger(Integer.MAX_VALUE);
    private final AtomicReference<DataInstance> resultInstance = new AtomicReference<>();

    @SneakyThrows
    @Builder
    public Engine(InputStream stream, InputStrategy strategy, GreedyProcessManager. GreedyProcessManagerBuilder processManagerBuilder, GenerationalSetup generationalSetup) {
        inputInstance = strategy.parse(stream);
        this.processManagerBuilder = processManagerBuilder.processorsCount(inputInstance.processorsCount());
        this.generationalSetup = generationalSetup;
    }

    public Engine mashupAnalyze(ThreadFactory threadFactory) {
        long begin = System.nanoTime();

        for (int i = 0; i < generationalSetup.getMaxGenerations(); i++) {
            CountDownLatch latch = new CountDownLatch(generationalSetup.getGenerationSize());
            for (int j = 0; j < generationalSetup.getGenerationSize(); j++) {
                threadFactory.newThread(() -> {
                    List<Process> variation = inputInstance.processList();

                    ProcessManager processManager = processManagerBuilder.build();
                    processManager.addProcesses(variation);

                    synchronized (minCmax) {
                        int cMax = processManager.getCMax();
                        if (cMax < minCmax.get()) {
                            minCmax.set(cMax);
                            resultInstance.set(new DataInstance(inputInstance.processorsCount(), variation));
                        }
                    }

                    latch.countDown();
                }).start();
            }

            try {
                latch.await(generationalSetup.getMaxGenerationTime(), generationalSetup.getMaxGenerationTimeUnit());
            } catch (InterruptedException ignored) {}
        }

        evaluationTime = System.nanoTime() - begin;
        return this;
    }

    @Override
    public int getCMax() {
        return buildResultInstance().getCMax();
    }

    @Override
    public String prettyPrint() {
        return "Evaluation time: " + evaluationTime + "ns\n" + buildResultInstance().prettyPrint();
    }

    private ProcessManager buildResultInstance() {
        synchronized (resultInstance) {
            return processManagerBuilder.processorsCount(resultInstance.get().processorsCount())
                    .build()
                    .addProcesses(resultInstance.get().processList());
        }
    }
}
