package edu.ryder_czarnecki.engine.generational_setup;

import lombok.Builder;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

@Builder
public class CustomGenerationalSetup {
    @Range(from = 1, to = Integer.MAX_VALUE) int maxGenerations;
    @Range(from = 1, to = Integer.MAX_VALUE) int generationSize;
    @Range(from = 0, to = 1) double mutationIntensity;
    @Range(from = 0, to = 1) double crossoverIntensity;
    @Range(from = 0, to = 1) double minimalDiversity;
    @Range(from = 0, to = 1) double mutationPart;
    @Range(from = 0, to = 1) double randomPart;
    @Range(from = 1, to = Long.MAX_VALUE) long maxGenerationTime;
    TimeUnit maxGenerationTimeUnit;

    public GenerationalSetup parseIntoRecord() {
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
}
