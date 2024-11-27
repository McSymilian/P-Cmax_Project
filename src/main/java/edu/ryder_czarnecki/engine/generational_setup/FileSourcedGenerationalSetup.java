package edu.ryder_czarnecki.engine.generational_setup;

import org.jetbrains.annotations.Range;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FileSourcedGenerationalSetup{
    int maxGenerations;
    int generationSize;
    @Range(from = 0, to = 1) double mutationIntensity;
    @Range(from = 0, to = 1) double crossoverIntensity;
    @Range(from = 0, to = 1) double minimalDiversity;
    @Range(from = 0, to = 1) double mutationPart;
    @Range(from = 0, to = 1) double randomPart;
    long maxGenerationTime;
    TimeUnit maxGenerationTimeUnit;
    String separator;

    private static final Set<String> REQUIERD_KEYS = Set.of("maxGenerations", "generationSize", "mutationIntensity", "crossoverIntensity", "minimalDiversity", "mutationPart", "randomPart", "maxGenerationTime", "maxGenerationTimeUnit");
    private static final Set<String> activatedKeys= new HashSet<>();

    public FileSourcedGenerationalSetup separator(String sep){
        separator = sep;
        return this;
    }

    public FileSourcedGenerationalSetup createWithFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, keyWord, value;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] seperatedLine = line.split(separator);
                    if (seperatedLine.length != 2) {
                        throw new IllegalArgumentException("Generational Setup input file invalid format: " + line);
                    }

                    keyWord = seperatedLine[0].trim();
                    value = seperatedLine[1].trim();

                    switch (keyWord) {
                        case "maxGenerations" -> {
                            maxGenerations = Integer.parseInt(value);
                            activatedKeys.add(keyWord);
                        }
                        case "generationSize" -> {
                            generationSize = Integer.parseInt(value);
                            activatedKeys.add(keyWord);
                        }
                        case "mutationIntensity" -> {
                            mutationIntensity = Double.parseDouble(value);
                            activatedKeys.add(keyWord);
                        }
                        case "crossoverIntensity" -> {
                            crossoverIntensity = Double.parseDouble(value);
                            activatedKeys.add(keyWord);
                        }
                        case "minimalDiversity" -> {
                            minimalDiversity = Double.parseDouble(value);
                            activatedKeys.add(keyWord);
                        }
                        case "mutationPart" -> {
                            mutationPart = Double.parseDouble(value);
                            activatedKeys.add(keyWord);
                        }
                        case "randomPart" -> {
                            randomPart = Double.parseDouble(value);
                            activatedKeys.add(keyWord);
                        }
                        case "maxGenerationTime" -> {
                            maxGenerationTime = Long.parseLong(value);
                            activatedKeys.add(keyWord);
                        }
                        case "maxGenerationTimeUnit" -> {
                            maxGenerationTimeUnit = TimeUnit.valueOf(value);
                            activatedKeys.add(keyWord);
                        }
                        default -> throw new IllegalArgumentException("Unknown keyword in Generational Setup input file: " + keyWord);
                    }

                }
                catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (!activatedKeys.containsAll(REQUIERD_KEYS)){
                List<String> missingKeys = new ArrayList<>();
                for (String key : REQUIERD_KEYS) {
                    if (!activatedKeys.contains(key)) {
                        missingKeys.add(key);
                    }
                }
                throw new IllegalStateException("Generational Setup input file missing required keys: " + missingKeys);
            }
        }
        catch (IOException e) {
            System.err.println("Error while reading Generational Setup input file: " + e.getMessage());
        } catch (IllegalStateException e){
            System.err.println(e.getMessage());
        }
    return this;
    }

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
