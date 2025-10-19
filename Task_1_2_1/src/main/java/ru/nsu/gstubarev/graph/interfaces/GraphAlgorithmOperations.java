package ru.nsu.gstubarev.graph.interfaces;

import java.util.List;
import java.util.Set;

public interface GraphAlgorithmOperations<V> {
    /**
     * Returns all vertices in the graph.
     *
     * @return set of all vertices
     */
    Set<V> getVertices();

    /**
     * Returns the neighbors of a vertex (outgoing edges).
     *
     * @param vertex the vertex to get neighbors for
     * @return list of neighbors
     */
    List<V> getNeighbors(V vertex);

    /**
     * Returns the in-degree of a vertex (number of incoming edges).
     *
     * @param vertex the vertex to check
     * @return the in-degree
     */
    int getInDegree(V vertex);

    /**
     * Returns the incoming neighbors of a vertex.
     *
     * @param vertex the vertex to get incoming neighbors for
     * @return list of incoming neighbors
     */
    List<V> getIncomingNeighbors(V vertex);
}
