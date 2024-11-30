package edu.ryder_czarnecki.engine.util;

import java.util.List;

import edu.ryder_czarnecki.process_stack.ProcessStack;

public class Compare {
    private Compare() {
        throw new IllegalStateException("Utility class");
    }

    public static double compare(
            List<ProcessStack> base,
            List<ProcessStack> compared
    ) {
        double sum = 0;
        for (int i = 0; i < base.size(); i++)
            sum += base.get(i).getFullLength() / compared.get(i).getFullLength();

        return sum / base.size();
    }
}
