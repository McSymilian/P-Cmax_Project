package edu.ryder_czarnecki.process_manager;

import org.jetbrains.annotations.Range;

public interface ProcessManagerFactory {
    ProcessManager create(@Range(from = 1, to = Integer.MAX_VALUE) int processorsCount);
}
