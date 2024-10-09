package edu.ryder_czarnecki.process_manager;

public class EmptyProcessorStackListException extends RuntimeException {
    public EmptyProcessorStackListException(String message) {
        super(message);
    }
    public EmptyProcessorStackListException() {
        super();
    }
}
