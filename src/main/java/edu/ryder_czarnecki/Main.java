package edu.ryder_czarnecki;

import edu.ryder_czarnecki.data_input.DataInput;
import edu.ryder_czarnecki.data_input.InstanceInputException;
import edu.ryder_czarnecki.data_input.StandardDataInput;
import edu.ryder_czarnecki.instance.DataInstance;
import edu.ryder_czarnecki.process_manager.ProcessManager;
import lombok.extern.java.Log;

@Log
public class Main {
    public static void main(String[] args) {
        DataInput input = new StandardDataInput();
        DataInstance instance = null;
        try {
            instance = input.parse(System.in);
        } catch (InstanceInputException e) {
            log.warning("Unable to parse data input");
            System.exit(1);
        }

        ProcessManager processManager = ProcessManager
                .builder()
                .processorsCount(instance.processorsCount())
                .build()
                .addProcesses(instance.processList());

        int res = processManager.getCMax();


        log.info("CMax: " + res);
        log.info(processManager.prettyPrint());
    }
}