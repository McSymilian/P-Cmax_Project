package edu.ryder_czarnecki.process_manager;

import edu.ryder_czarnecki.process_stack.ProcessStack;

import java.util.List;

public interface ProcessManager extends ProcessManagerInput, ProcessManagerOutput {
    List<ProcessStack> getProcessStacks();
    static String prettyPrint(List<ProcessStack> processStacks) {
        StringBuilder sb = new StringBuilder();
        sb.append("ProcessManager\n");
        processStacks.forEach(stack -> sb.append(stack.toString()).append("\n"));
        return sb.toString();
    }
}
