package edu.ryder_czarnecki.process_manager;

import edu.ryder_czarnecki.process_stack.DefaultProcessStackBuilder;
import edu.ryder_czarnecki.process_stack.ProcessStack;
import edu.ryder_czarnecki.process_stack.ProcessStackBuilder;
import edu.ryder_czarnecki.process.Process;
import lombok.Builder;
import lombok.ToString;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ProcessManager {
    private final List<ProcessStack> stacks = new ArrayList<>();

    @Builder
    private ProcessManager(@Range(from = 1, to = Integer.MAX_VALUE) int processorsCount) {
        this(processorsCount, DefaultProcessStackBuilder.create());
    }

    private ProcessManager(@Range(from = 1, to = Integer.MAX_VALUE) int processorsCount, ProcessStackBuilder processStackBuilder) {
        for(int i = 0; i < processorsCount; i++)
            stacks.add(processStackBuilder.build());

    }

    public int getCMax() {
        return stacks.stream()
                .mapToInt(ProcessStack::getFullLength)
                .max()
                .orElseThrow(EmptyProcessorStackListException::new);
    }

    public ProcessManager addProcesses(List<Process> processes) {
        processes.forEach(process -> shortestStack(stacks).addProcess(process));
        return this;
    }

    private static ProcessStack shortestStack(List<ProcessStack> stacks) {
        return stacks.stream()
                .min((ProcessStack::compareTo))
                .orElseThrow(EmptyProcessorStackListException::new);
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProcessManager\n");
        stacks.forEach(stack -> sb.append(stack.toString()).append("\n"));
        return sb.toString();
    }

}
