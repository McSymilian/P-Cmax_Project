package edu.ryder_czarnecki;

import edu.ryder_czarnecki.process.Process;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import lombok.extern.java.Log;

import java.util.List;

@Log
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


        log.info("CMax: " + res);
        log.info(processManager.prettyPrint());
    }
}