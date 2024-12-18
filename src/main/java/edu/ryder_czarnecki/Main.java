package edu.ryder_czarnecki;

import edu.ryder_czarnecki.engine.Engine;
import edu.ryder_czarnecki.engine.GenerationalSetup;
import edu.ryder_czarnecki.instance.ResultInstance;
import edu.ryder_czarnecki.process_manager.GreedyProcessManager;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

@Log
public class Main {

    private static final String FILE_ENDPOINT = "https://ekursy.put.poznan.pl/pluginfile.php/1690125/mod_resource/content/1/m30.txt";
    private static final File inputA = new File("m50n200.txt");

    public static void main(String[] args) throws FileNotFoundException {
        ResultInstance result = Engine.builder()
                .processManagerFactory(
                        processorsCount -> GreedyProcessManager
                                .builder()
                                .processorsCount(processorsCount)
                                .build()
                )
//                .stream(System.in)
                .stream(new FileInputStream(inputA))
//                .stream(new RandomInputStream().getInputStream())
                .strategy(Engine.SEQUENTIAL_INPUT)
                .generationalSetup(new GenerationalSetup(
                        1_000,
                        1_000,
                        0.02,
                        0.48,
                        0.2,
                        0.3,
                        0.6,
                        2,
                        TimeUnit.SECONDS
                ))
//                .threadFactory(Thread.ofPlatform().factory())
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