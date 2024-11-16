package edu.ryder_czarnecki.engine.util;

import java.util.List;
import java.util.Arrays;


import edu.ryder_czarnecki.process.ProcessInstance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompareTest {

    @Test
    void comparison0(){
        List<ProcessInstance> listA = Arrays.asList(new ProcessInstance(4), new ProcessInstance(3), new ProcessInstance(2), new ProcessInstance(1));
        List<ProcessInstance> listB = Arrays.asList(new ProcessInstance(1), new ProcessInstance(2), new ProcessInstance(3), new ProcessInstance(4));

        double result = Compare.compare(listA, listB);

        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void comparison1() {
        List<ProcessInstance> listA = Arrays.asList(new ProcessInstance(1), new ProcessInstance(2), new ProcessInstance(3), new ProcessInstance(4));
        List<ProcessInstance> listB = Arrays.asList(new ProcessInstance(1), new ProcessInstance(2), new ProcessInstance(3), new ProcessInstance(4));

        double result = Compare.compare(listA, listB);

        assertEquals(1.0, result, 0.0001);
    }

    @Test
    void comparison05(){
        List<ProcessInstance> listA = Arrays.asList(new ProcessInstance(1), new ProcessInstance(2), new ProcessInstance(4), new ProcessInstance(3));
        List<ProcessInstance> listB = Arrays.asList(new ProcessInstance(1), new ProcessInstance(2), new ProcessInstance(3), new ProcessInstance(4));

        double result = Compare.compare(listA, listB);

        assertEquals(0.5, result, 0.0001);
    }
}
