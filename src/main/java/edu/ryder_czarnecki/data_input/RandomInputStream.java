package edu.ryder_czarnecki.data_input;

import edu.ryder_czarnecki.process_manager.ProcessManagerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomInputStream {
    private final Random random = new Random();
    private final int processorsCount = random.nextInt(2, 100);
    private int processesCount = random.nextInt((int) Math.pow(processorsCount, 2), (int) Math.pow(processorsCount, 3));
    private List<Integer> data = new ArrayList<>(processorsCount + 2);


    public RandomInputStream() {
        data.add(processorsCount);
        data.add(processesCount);
        for (int i = 0; i < processesCount; i++)
            data.add(random.nextInt(1, 50));
    }


    public InputStream getInputStream() {
        StringBuilder sb = new StringBuilder();
        for (Integer num : data) {
            sb.append(num).append("\n");  // Using newline as a delimiter
        }

        // Step 2: Convert the resulting string to a byte array
        byte[] byteArray = sb.toString().getBytes(StandardCharsets.UTF_8);

        // Step 3: Create and return a ByteArrayInputStream from the byte array
        return new ByteArrayInputStream(byteArray);
    }

    public boolean hasNext() throws IOException {
        return processesCount > 0;
    }
}
