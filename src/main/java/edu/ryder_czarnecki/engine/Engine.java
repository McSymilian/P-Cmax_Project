package edu.ryder_czarnecki.engine;

import edu.ryder_czarnecki.data_input.InputStrategy;
import edu.ryder_czarnecki.data_input.StandardInputStrategy;
import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.instance.ResultInstance;
import edu.ryder_czarnecki.process.ProcessInstance;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManagerFactory;
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
    public static final InputStrategy SEQUENTIAL_INPUT = new StandardInputStrategy();

    private final DataInstance inputInstance;
    private final ProcessManagerFactory processManagerFactory;
    private final GenerationalSetup generationalSetup;
    @Getter
    private Long evaluationTime = 0L;

    private final AtomicInteger minCmax = new AtomicInteger(Integer.MAX_VALUE);
    private final AtomicReference<ResultInstance> resultInstance = new AtomicReference<>();
    private final ThreadFactory threadFactory;

    @SneakyThrows
    @Builder
    public Engine(InputStream stream, InputStrategy strategy, ProcessManagerFactory processManagerFactory, GenerationalSetup generationalSetup, ThreadFactory threadFactory) {
        inputInstance = strategy.parse(stream);
        this.processManagerFactory = processManagerFactory;
        this.generationalSetup = generationalSetup;
        this.threadFactory = threadFactory;
    }

    public ResultInstance mashupAnalyze() {
        long begin = System.nanoTime();

        for (int i = 0; i < generationalSetup.getMaxGenerations(); i++) {
            CountDownLatch latch = new CountDownLatch(generationalSetup.getGenerationSize());
            for (int j = 0; j < generationalSetup.getGenerationSize(); j++) {
                threadFactory.newThread(() -> {
                    List<ProcessInstance> variation = inputInstance.processList();

                    ProcessManager processManager = processManagerFactory.create(inputInstance.processorsCount());
                    processManager.addProcesses(variation);

                    synchronized (minCmax) {
                        int cMax = processManager.getCMax();
                        if (cMax < minCmax.get()) {
                            minCmax.set(cMax);
                            resultInstance.set(ResultInstance.builder()
                                    .evaluationTime(System.nanoTime() - begin)
                                    .processStacks(processManager.getProcessStacks())
                                    .cMax(processManager.getCMax())
                                    .build()
                            );
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
        return resultInstance.get();
    }

    @Override
    public int getCMax() {
        return resultInstance.get().getCMax();
    }

    @Override
    public String prettyPrint() {
        return "Evaluation time: " + evaluationTime + "ns\n" + ProcessManager.prettyPrint(resultInstance.get().getProcessStacks());
    }
}
