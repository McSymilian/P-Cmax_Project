package edu.ryder_czarnecki.engine.util;

import edu.ryder_czarnecki.process.ProcessInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Permute {
    private Permute() {
        throw new IllegalStateException("Utility class");
    }

    private static final Random random = new Random();
    public static List<ProcessInstance> permute(
            final List<ProcessInstance> processList
    ) {
        List<ProcessInstance> permuted = new ArrayList<>(processList);
        for (int i = 0; i < processList.size(); i++) {
            int j = random.nextInt(0, processList.size());

            if (i == j) continue;

            ProcessInstance temp = permuted.get(i);
            permuted.set(i, permuted.get(j));
            permuted.set(j, temp);
        }

        return permuted;
    }
}
