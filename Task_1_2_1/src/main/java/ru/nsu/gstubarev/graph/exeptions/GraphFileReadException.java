package ru.nsu.gstubarev.graph.exeptions;

/**
 * Exception thrown when graph file reading fails.
 */
public class GraphFileReadException extends RuntimeException {

    /**
     * Constructs a new GraphFileReadException with the specified detail message.
     *
     * @param message the detail message
     */
    public GraphFileReadException(String message) {
        super(message);
    }
}
