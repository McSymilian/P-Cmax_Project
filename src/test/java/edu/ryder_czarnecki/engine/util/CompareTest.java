package edu.ryder_czarnecki.engine.util;

import java.util.List;
import java.util.Arrays;


import edu.ryder_czarnecki.process.Process;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompareTest {

    @Test
    void comparison0(){
        List<Process> listA = Arrays.asList(new Process(4), new Process(3), new Process(2), new Process(1));
        List<Process> listB = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));

        double result = new Compare().compare(listA, listB);

        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void comparison1() {
        List<Process> listA = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));
        List<Process> listB = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));

        double result = new Compare().compare(listA, listB);

        assertEquals(1.0, result, 0.0001);
    }

    @Test
    void comparison05(){
        List<Process> listA = Arrays.asList(new Process(1), new Process(2), new Process(4), new Process(3));
        List<Process> listB = Arrays.asList(new Process(1), new Process(2), new Process(3), new Process(4));

        double result = new Compare().compare(listA, listB);

        assertEquals(0.5, result, 0.0001);
    }


}
