package edu.ryder_czarnecki.process_manager;

import edu.ryder_czarnecki.process_stack.DefaultProcessStackBuilder;
import edu.ryder_czarnecki.process_stack.ProcessStack;
import edu.ryder_czarnecki.process_stack.ProcessStackBuilder;
import edu.ryder_czarnecki.process.Process;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class GreedyProcessManager implements ProcessManager {
    private final List<ProcessStack> processStacks = new ArrayList<>();

    @Builder
    private GreedyProcessManager(@Range(from = 1, to = Integer.MAX_VALUE) int processorsCount) {
        this(processorsCount, DefaultProcessStackBuilder.create());
    }

    private GreedyProcessManager(@Range(from = 1, to = Integer.MAX_VALUE) int processorsCount, ProcessStackBuilder processStackBuilder) {
        for(int i = 0; i < processorsCount; i++)
            processStacks.add(processStackBuilder.build());

    }

    @Override
    public int getCMax() {
        return processStacks.stream()
                .mapToInt(ProcessStack::getFullLength)
                .max()
                .orElseThrow(EmptyProcessorStackListException::new);
    }

    @Override
    public ProcessManager addProcesses(List<Process> processes) {
        processes.forEach(process -> shortestStack(processStacks).addProcess(process));
        return this;
    }

    private static ProcessStack shortestStack(List<ProcessStack> stacks) {
        return stacks.stream()
                .min((ProcessStack::compareTo))
                .orElseThrow(EmptyProcessorStackListException::new);
    }

    @Override
    public String prettyPrint() {
        return ProcessManager.prettyPrint(processStacks);
    }

}
