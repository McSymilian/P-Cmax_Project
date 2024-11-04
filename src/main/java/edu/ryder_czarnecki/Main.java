package edu.ryder_czarnecki;

import edu.ryder_czarnecki.generator.Engine;
import edu.ryder_czarnecki.generator.GenerationalSetup;
import edu.ryder_czarnecki.instance.ResultInstance;
import edu.ryder_czarnecki.process_manager.GreedyProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import lombok.extern.java.Log;

import java.util.concurrent.TimeUnit;

@Log
public class Main {

    public static final String FILE_ENDPOINT = "https://ekursy.put.poznan.pl/pluginfile.php/1690125/mod_resource/content/1/m30.txt";

    public static void main(String[] args) {
        ResultInstance result = Engine.builder()
                .processManagerFactory(
                        processorsCount -> GreedyProcessManager
                                .builder()
                                .processorsCount(processorsCount)
                                .build()
                )
                .stream(System.in)
                .strategy(Engine.SEQUENTIAL_INPUT)
                .generationalSetup(GenerationalSetup
                        .builder()
                        .generationSize(20)
                        .maxGenerations(20)
                        .crossoverProbability(0.2)
                        .mutationProbability(0.2)
                        .maxGenerationTime(1000)
                        .maxGenerationTimeUnit(TimeUnit.SECONDS)
                        .build()
                )
                .build()
                .mashupAnalyze(Thread.ofVirtual().factory());

        String resultPrint = new StringBuilder()
                .append("\n")
                .append("CMax: ")
                .append(result.getCMax())
                .append("\n")
                .append("Evaluation time:")
                .append(result.getEvaluationTime() / 10E9)
                .append("s")
                .append("\n")
                .append(ProcessManager.prettyPrint(result.getProcessStacks()))
                .toString();
        log.info(resultPrint);
    }
}