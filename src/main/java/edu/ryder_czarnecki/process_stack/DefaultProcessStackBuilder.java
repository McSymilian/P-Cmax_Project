package edu.ryder_czarnecki.process_stack;

public class DefaultProcessStackBuilder implements ProcessStackBuilder {
    private int nextIndex = 0;
    @Override
    public ProcessStack build() {
        return new ProcessStack(String.valueOf(nextIndex++));
    }

    public static ProcessStackBuilder create() {
        return new DefaultProcessStackBuilder();
    }
}
