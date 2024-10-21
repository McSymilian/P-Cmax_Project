package edu.ryder_czarnecki.instance;

import edu.ryder_czarnecki.process.Process;

import java.util.List;

public record DataInstance(
        int processorsCount,
        List<Process> processList
) {
}
