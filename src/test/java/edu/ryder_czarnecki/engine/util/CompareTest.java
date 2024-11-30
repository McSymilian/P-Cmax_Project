package edu.ryder_czarnecki.engine.util;

import java.util.List;
import java.util.Arrays;


import edu.ryder_czarnecki.process.Process;
import edu.ryder_czarnecki.process_manager.GreedyProcessManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompareTest {

    @Test
    void comparison0(){
        List<Process> listA = Arrays.asList(new Process(4), new Process(3), new Process(2), new Process(1));
        GreedyProcessManager processManager = GreedyProcessManager.builder().processorsCount(1).build();
        processManager.addProcesses(listA);

        List<Process> listB = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));
        GreedyProcessManager processManager2 = GreedyProcessManager.builder().processorsCount(1).build();
        processManager2.addProcesses(listB);

        double result = Compare.compare(processManager.getProcessStacks(), processManager2.getProcessStacks());

        assertEquals(0.0, result, 1);
    }

    @Test
    void comparison1() {
        List<Process> listA = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));
        GreedyProcessManager processManager = GreedyProcessManager.builder().processorsCount(1).build();
        processManager.addProcesses(listA);

        List<Process> listB = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));
        GreedyProcessManager processManager2 = GreedyProcessManager.builder().processorsCount(1).build();
        processManager2.addProcesses(listB);

        double result = Compare.compare(processManager.getProcessStacks(), processManager2.getProcessStacks());

        assertEquals(1.0, result, 0.0001);
    }

    @Test
    void comparison05(){
        List<Process> listA = Arrays.asList(new Process(1), new Process(2), new Process(4), new Process(3));
        GreedyProcessManager processManager = GreedyProcessManager.builder().processorsCount(1).build();
        processManager.addProcesses(listA);

        List<Process> listB = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));
        GreedyProcessManager processManager2 = GreedyProcessManager.builder().processorsCount(1).build();
        processManager2.addProcesses(listB);

        double result = Compare.compare(processManager.getProcessStacks(), processManager2.getProcessStacks());

        assertEquals(0.5, result, 1);
    }
}
