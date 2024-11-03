package edu.ryder_czarnecki;

import edu.ryder_czarnecki.data_input.InputStrategy;
import edu.ryder_czarnecki.data_input.InstanceInputException;
import edu.ryder_czarnecki.data_input.StandardInputStrategy;
import edu.ryder_czarnecki.generator.Engine;
import edu.ryder_czarnecki.generator.GenerationalSetup;
import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.process_manager.GreedyProcessManager;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import lombok.extern.java.Log;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Log
public class Main {

    public static final String FILE_ENDPOINT = "https://ekursy.put.poznan.pl/pluginfile.php/1690125/mod_resource/content/1/m30.txt";

    public static void main(String[] args) {
        Engine engine = Engine.builder()
                .processManagerBuilder(GreedyProcessManager.builder())
                .stream(System.in)
                .strategy(Engine.SEQUENCIAL_INPUT)
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


        log.info("CMax: " + engine.getCMax());
        log.info(engine.prettyPrint());
    }
}