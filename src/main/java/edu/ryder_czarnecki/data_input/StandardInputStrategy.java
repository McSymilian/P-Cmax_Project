package edu.ryder_czarnecki.data_input;

import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.process.ProcessInstance;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
@Log
public class StandardInputStrategy implements InputStrategy {

    @Override
    public DataInstance parse(InputStream stream) throws InstanceInputException {
        int processorsCount;
        List<ProcessInstance> processList;
        try {
            Scanner sc = new Scanner(stream);
            processorsCount = sc.nextInt();
            log.info("processorsCount: " + processorsCount);
            int size = sc.nextInt();
            log.info("size: " + size);
            processList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                int time = sc.nextInt();
                processList.add(new ProcessInstance(time));
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new InstanceInputException(e.getMessage());
        }

        return new DataInstance(processorsCount, processList);
    }
}
