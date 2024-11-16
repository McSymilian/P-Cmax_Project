package edu.ryder_czarnecki.process_manager;

import edu.ryder_czarnecki.process.ProcessInstance;

import java.util.List;

public interface ProcessManagerInput {

    ProcessManagerInput addProcesses(List<ProcessInstance> processes);


}
