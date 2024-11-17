package edu.ryder_czarnecki.engine.util;

import edu.ryder_czarnecki.process.ProcessInstance;

import java.util.List;

public record TemporalInstance(
        int cMax,
        List<ProcessInstance> processList
) {
}
