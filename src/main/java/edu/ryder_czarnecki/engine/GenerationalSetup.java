package edu.ryder_czarnecki.engine;

import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;


public record GenerationalSetup(
        int maxGenerations,
        int generationSize,
        @Range(from = 0, to = 1) double mutationIntensity,
        @Range(from = 0, to = 1) double crossoverIntensity,
        @Range(from = 0, to = 1) double minimalDiversity,
        @Range(from = 0, to = 1) double mutationPart,
        @Range(from = 0, to = 1) double randomPart,
        long maxGenerationTime,
        TimeUnit maxGenerationTimeUnit
) {}
