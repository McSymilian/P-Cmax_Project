package edu.ryder_czarnecki.instance;

import edu.ryder_czarnecki.process.ProcessInstance;

import java.util.List;

public record DataInstance(
        int processorsCount,
        List<ProcessInstance> processList
) {
}
