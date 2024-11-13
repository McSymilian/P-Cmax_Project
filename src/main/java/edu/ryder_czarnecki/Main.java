package edu.ryder_czarnecki;

import edu.ryder_czarnecki.data_input.RandomInputStream;
import edu.ryder_czarnecki.engine.Engine;
import edu.ryder_czarnecki.engine.GenerationalSetup;
import edu.ryder_czarnecki.instance.ResultInstance;
import edu.ryder_czarnecki.process_manager.GreedyProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

@Log
public class Main {

    private static final String FILE_ENDPOINT = "https://ekursy.put.poznan.pl/pluginfile.php/1690125/mod_resource/content/1/m30.txt";
    private static final File inputA = new File("instance2_nasze.txt");
    public static void main(String[] args) throws FileNotFoundException {
        ResultInstance result = Engine.builder()
                .processManagerFactory(
                        processorsCount -> GreedyProcessManager
                                .builder()
                                .processorsCount(processorsCount)
                                .build()
                )
//                .stream(new FileInputStream(inputA))
                .stream(new RandomInputStream().getInputStream())
                .strategy(Engine.SEQUENTIAL_INPUT)
                .generationalSetup(GenerationalSetup
                        .builder()
                        .generationSize(100_000)
                        .maxGenerations(1)
                        .crossoverProbabilityPercent(20)
                        .minimalSimilarity(0.2)
                        .maxGenerationTime(10)
                        .maxGenerationTimeUnit(TimeUnit.SECONDS)
                        .build()
                )
                .threadFactory(Thread.ofVirtual().factory())
                .build()
                .mashupAnalyze();

        String resultPrint = new StringBuilder()
                .append("\n")
                .append("CMax: ")
                .append(result.getCMax())
                .append("\n")
                .append("Evaluation time:")
                .append(result.getEvaluationTime() / 10E9)
                .append("s")
                .append("\n")
//                .append(ProcessManager.prettyPrint(result.getProcessStacks()))
                .toString();
        log.info(resultPrint);
    }
}