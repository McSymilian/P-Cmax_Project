package edu.ryder_czarnecki.engine.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import edu.ryder_czarnecki.process.ProcessInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class MutateTest {

    @Test
    void mutate() {
        List<ProcessInstance> base = Arrays.asList(
                new ProcessInstance(1),
                new ProcessInstance(2),
                new ProcessInstance(3),
                new ProcessInstance(4)
        );

        List<ProcessInstance> mutation = Mutate.mutate(
                base,
                0.25
        );

        assertEquals(4, mutation.size());
        assertNotSame(base.toArray(), mutation.toArray());
    }

}