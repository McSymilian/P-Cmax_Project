package edu.ryder_czarnecki.generator;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class GenerationalSetup {
    private final int maxGenerations;
    private final int generationSize;

    private final double mutationProbability;
    private final double crossoverProbability;

    private final long maxGenerationTime;
    private final TimeUnit maxGenerationTimeUnit;
}
