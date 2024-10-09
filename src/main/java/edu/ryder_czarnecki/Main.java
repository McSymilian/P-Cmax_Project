package edu.ryder_czarnecki;

import edu.ryder_czarnecki.process.Process;
import edu.ryder_czarnecki.process_manager.ProcessManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> input = List.of(4, 3, 4, 4, 2, 1, 1);

        ProcessManager processManager = ProcessManager
                .builder()
                .processorsCount(3)
                .build()
                .addProcesses(input
                        .stream()
                        .map(Process::new)
                        .toList()
                );

        int res = processManager.getCMax();

        System.out.println(res);
        System.out.println(processManager.prettyPrint());
    }
}