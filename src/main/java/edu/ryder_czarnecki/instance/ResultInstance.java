package edu.ryder_czarnecki.instance;

import edu.ryder_czarnecki.process_stack.ProcessStack;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResultInstance {
    private final Integer cMax;
    private final List<ProcessStack> processStacks;
    private final Long evaluationTime;

    public int getProcessorsCount() {
        return processStacks.size();
    }
}
