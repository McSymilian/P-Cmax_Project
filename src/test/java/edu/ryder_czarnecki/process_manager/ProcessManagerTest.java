package edu.ryder_czarnecki.process_manager;

import edu.ryder_czarnecki.process.Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
class ProcessManagerTest {
    private GreedyProcessManager processManager;

    @BeforeEach
    void setUp() {
        processManager = GreedyProcessManager.builder().processorsCount(3).build();
    }

    @Test
    void testProcessingVariantA() {
        assertEquals(
                7,
                processManager.addProcesses(
                                Stream.of(4, 3, 4, 4, 2, 1, 1)
                                        .map(Process::new)
                                        .toList()
                        )
                        .getCMax()
        );
    }

    @Test
    void testProcessingVariantB() {
        assertEquals(
                16,
                processManager.addProcesses(
                                Stream.of(7, 4, 4, 3, 1, 6, 7, 9)
                                        .map(Process::new)
                                        .toList()
                        )
                        .getCMax()
        );
    }

    @Test
    void testProcessingVariantC() {
        assertEquals(
                6,
                processManager.addProcesses(
                                Stream.of(1, 3, 4, 5, 2, 1, 1)
                                        .map(Process::new)
                                        .toList()
                        )
                        .getCMax()
        );
    }

    @Test
    void testProcessingVariantD() {
        assertEquals(
                18,
                processManager.addProcesses(
                                Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                        .map(Process::new)
                                        .toList()
                        )
                        .getCMax()
        );
    }
  
}