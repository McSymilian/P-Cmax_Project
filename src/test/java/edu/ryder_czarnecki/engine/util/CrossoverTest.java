package edu.ryder_czarnecki.engine.util;

import edu.ryder_czarnecki.data_input.InputStrategy;
import edu.ryder_czarnecki.data_input.InstanceInputException;
import edu.ryder_czarnecki.data_input.RandomInputStream;
import edu.ryder_czarnecki.data_input.StandardInputStrategy;
import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.instance.util.DeepCopy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrossoverTest {

    @Test
    void crossover() throws InstanceInputException {
        InputStrategy strategy = new StandardInputStrategy();
        DataInstance mother = strategy.parse(new RandomInputStream().getInputStream());
        DataInstance father = new DeepCopy().copy(mother);
        int motherCount = mother.processList().size();
        int fatherCount = father.processList().size();

        DataInstance crossover = new DataInstance(fatherCount, new Crossover().crossover(
                mother.processList(),
                father.processList(),
                0.8
        ));


        assertEquals(motherCount, crossover.processList().size());
        assertEquals(fatherCount, crossover.processList().size());
        assertEquals(fatherCount, father.processList().size());
        assertEquals(motherCount, mother.processList().size());
    }
}