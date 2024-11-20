package edu.ryder_czarnecki.engine.generational_setup;

import lombok.Builder;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

@Builder
public class CustomGenerationalSetup {
    int maxGenerations;
    int generationSize;
    @Range(from = 0, to = 1) double mutationIntensity;
    @Range(from = 0, to = 1) double crossoverIntensity;
    @Range(from = 0, to = 1) double minimalDiversity;
    @Range(from = 0, to = 1) double mutationPart;
    @Range(from = 0, to = 1) double randomPart;
    long maxGenerationTime;
    TimeUnit maxGenerationTimeUnit;

    public GenerationalSetup parseIntoRecord() {
        validateRanges();
        return new GenerationalSetup(
                maxGenerations,
                generationSize,
                mutationIntensity,
                crossoverIntensity,
                minimalDiversity,
                mutationPart,
                randomPart,
                maxGenerationTime,
                maxGenerationTimeUnit
        );
    }

    private void validateRanges() {
        if (mutationIntensity < 0 || mutationIntensity > 1) {
            throw new IllegalArgumentException("mutationIntensity must be in range [0, 1]");
        }
        if (crossoverIntensity < 0 || crossoverIntensity > 1) {
            throw new IllegalArgumentException("crossoverIntensity must be in range [0, 1]");
        }
        if (minimalDiversity < 0 || minimalDiversity > 1) {
            throw new IllegalArgumentException("minimalDiversity must be in range [0, 1]");
        }
        if (mutationPart < 0 || mutationPart > 1) {
            throw new IllegalArgumentException("mutationPart must be in range [0, 1]");
        }
        if (randomPart < 0 || randomPart > 1) {
            throw new IllegalArgumentException("randomPart must be in range [0, 1]");
        }
        if (maxGenerationTime <= 0) {
            throw new IllegalArgumentException("maxGenerationTime must be non-negative");
        }
    }
}
