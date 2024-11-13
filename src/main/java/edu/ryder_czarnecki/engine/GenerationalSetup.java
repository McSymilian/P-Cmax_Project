package edu.ryder_czarnecki.engine;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

@Data
public class GenerationalSetup {
    private final int maxGenerations;
    private final int generationSize;

    private final double minimalSimilarity;
    private final int crossoverProbability;

    private final long maxGenerationTime;
    private final TimeUnit maxGenerationTimeUnit;

    @Builder
    public GenerationalSetup(
            int maxGenerations,
            int generationSize,
            double minimalSimilarity,
            @Range(from = 0, to = 100)
            int crossoverProbabilityPercent,
            long maxGenerationTime,
            TimeUnit maxGenerationTimeUnit
    ) {
        this.maxGenerations = maxGenerations;
        this.generationSize = generationSize;
        this.minimalSimilarity = minimalSimilarity;
        this.crossoverProbability = crossoverProbabilityPercent;
        this.maxGenerationTime = maxGenerationTime;
        this.maxGenerationTimeUnit = maxGenerationTimeUnit;
    }
}
