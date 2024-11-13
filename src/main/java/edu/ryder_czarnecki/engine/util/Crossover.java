package edu.ryder_czarnecki.engine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ryder_czarnecki.process.Process;

public class Crossover {
    private static final Random random = new Random(System.nanoTime());
    public List<Process> crossover(List<Process> mother, List<Process> father, double intensity) {
        int max = (int) (mother.size() * intensity);
        int min =  mother.size() - max;
        int n;
        if(min < max)
            n = random.nextInt(min, max);
        else
            n = random.nextInt(max, min);
        List<Process> crossed = new ArrayList<>(mother.subList(0, n));

        for (Process p : father)
            if (crossed.stream().noneMatch(it -> p == it))
                crossed.add(p);

        return crossed;
    }
}
