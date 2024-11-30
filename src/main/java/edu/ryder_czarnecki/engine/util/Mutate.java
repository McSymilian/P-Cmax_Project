package edu.ryder_czarnecki.engine.util;

import edu.ryder_czarnecki.process.ProcessInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutate {
    private Mutate() {
        throw new IllegalStateException("Utility class");
    }
    
    private static final Random random = new Random();
    public static List<ProcessInstance> mutate(
            final List<ProcessInstance> processList,
            final double mutationRate
    ) {
        int size = processList.size();
        int mutationCount = (int) (size * mutationRate);
        List<ProcessInstance> mutatedList = new ArrayList<>(processList);
        for (int i = 0; i < mutationCount; i++) {
            int firstIndex = random.nextInt(0, size);
            int secondIndex = random.nextInt(1, size);

            ProcessInstance temp = mutatedList.get(firstIndex);
            mutatedList.set(firstIndex, mutatedList.get(secondIndex));
            mutatedList.set(secondIndex, temp);
        }

        return mutatedList;
    }
}
