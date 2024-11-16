package edu.ryder_czarnecki.engine.util;

import java.util.List;

import edu.ryder_czarnecki.process.ProcessInstance;

public class Compare {
    private Compare() {
        throw new IllegalStateException("Utility class");
    }

    public static double compare(
            final List<ProcessInstance> list1,
            final List<ProcessInstance> list2
    ) {
        double total = list1.size();
        double matching = 0;

        for (int i = 0; i < total; i++)
            if (list1.get(i).equals(list2.get(i)))
                matching++;

        return matching / total;
    }
}
