package edu.ryder_czarnecki.engine.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.ryder_czarnecki.process.ProcessInstance;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Range;

public class Crossover {
    private Crossover() {
        throw new IllegalStateException("Utility class");
    }

    private static final Random random = new Random(System.nanoTime());

    public static List<ProcessInstance> crossover(
            final List<ProcessInstance> mother,
            final List<ProcessInstance> father,
            @Range(from = 0, to = 1) final double intensity
    ) {
        int max = (int) (mother.size() * intensity);
        int min = mother.size() - max;
        int n;

        if (min == max)
            n = min;
        else if(min < max)
            n = random.nextInt(min, max);
        else
            n = random.nextInt(max, min);

        Set<Box> crossed = new LinkedHashSet<>(mother
                .subList(0, n)
                .stream()
                .map(Box::new)
                .toList()
        );

        crossed.addAll(father
                .stream()
                .map(Box::new)
                .toList()
        );

        return crossed
                .parallelStream()
                .map(it -> it.process)
                .toList();
    }

    @AllArgsConstructor
    private static class Box {
        private ProcessInstance process;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Box box)
                return process == box.process;

            return false;
        }

        @Override
        public int hashCode() {
            return process.hashCode();
        }
    }
}
