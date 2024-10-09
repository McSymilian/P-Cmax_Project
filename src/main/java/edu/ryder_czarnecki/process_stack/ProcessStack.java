package edu.ryder_czarnecki.process_stack;

import edu.ryder_czarnecki.process.Process;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ProcessStack implements Comparable<ProcessStack> {
    @Getter
    @NonNull
    private final String name;
    private final List<Process> processesStack = new ArrayList<>();

    @Contract(pure = true)
    public Stream<Process> getProcessesStream() {
        return processesStack.stream();
    }

    @Contract
    public void addProcess(Process process) {
        processesStack.add(process);
    }

    @Contract
    public void removeProcess(Process process) {
        processesStack.remove(process);
    }

    @Contract
    public void removeProcess(@Range(from = 0, to = Integer.MAX_VALUE) int index) {
        processesStack.remove(index);
    }

    @Contract(pure = true)
    public int getFullLength() {
        return processesStack.stream().mapToInt(Process::getMaxLength).sum() ;
    }


    @Override
    public int compareTo(@NotNull ProcessStack o) {
        if (this.getFullLength() > o.getFullLength())
            return 1;
        else if (this.getFullLength() < o.getFullLength())
            return -1;

        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProcessStack that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(processesStack, that.processesStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, processesStack);
    }

    @Override
    public String toString() {
        return "Stack " + name + ": " + processesStack.stream().map(Process::getMaxLength).toList();
    }
}
