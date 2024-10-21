package edu.ryder_czarnecki.data_input;

import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.process.Process;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandardDataInput implements DataInput {

    @Override
    public DataInstance parse(InputStream stream) throws InstanceInputException {
        int processorsCount;
        List<Process> processList;
        try {
            Scanner sc = new Scanner(stream);
            processorsCount = sc.nextInt();

            int size = sc.nextInt();
            processList = new ArrayList<>(size);

            for (int i = 0; i < size; i++)
                processList.add(new Process(sc.nextInt()));

        } catch (NoSuchElementException | IllegalStateException e) {
            throw new InstanceInputException(e.getMessage());
        }

        return new DataInstance(processorsCount, processList);
    }
}
