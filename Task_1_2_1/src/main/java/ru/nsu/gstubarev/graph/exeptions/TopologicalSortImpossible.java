package ru.nsu.gstubarev.graph.exeptions;

/**
 * Thrown when topological sort impossible.
 */
public class TopologicalSortImpossible extends RuntimeException {
    /**
     * Constructor of exception about topological sort impossible.
     *
     * @param message the invalid graph
     */
    public TopologicalSortImpossible(String message) {
        super(message);
    }
}
