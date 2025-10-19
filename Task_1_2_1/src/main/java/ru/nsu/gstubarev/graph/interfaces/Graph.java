package ru.nsu.gstubarev.graph.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * Interface representing a graph data structure.
 *
 * @param <V> the type of vertices in the graph
 */
public interface Graph<V> {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add
     * @throws IllegalArgumentException if vertex is null
     */
    void addVertex(V vertex);

    /**
     * Delete vertex from the graph.
     *
     * @param vertex the vertex to delete
     */
    void deleteVertex(V vertex);

    /**
     * Checks if the graph contains the specified vertex.
     *
     * @param vertex the vertex to check
     * @return true if the graph contains the vertex, false otherwise
     */
    boolean hasVertex(V vertex);

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    int getVertexCount();

    /**
     * Adds a directed edge between two vertices with the specified weight.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @param weight the weight of the edge
     * @throws IllegalArgumentException if either vertex is null
     */
    void addEdge(V from, V to, int weight);

    /**
     * Delete an edge between two vertices with the specified weight.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @param weight the weight of the edge to delete
     */
    void deleteEdge(V from, V to, int weight);

    /**
     * Checks if there is an edge between two vertices with the specified weight.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @param weight the weight to check
     * @return true if the edge exists with the specified weight, false otherwise
     */
    boolean hasEdge(V from, V to, int weight);

    /**
     * Reads graph data from a file.
     *
     * @param name the name of the file to read
     * @throws IOException if an I/O error occurs
     */
    void readFile(String name) throws IOException;

    /**
     * Compares this graph to the specified object.
     *
     * @param obj the object to compare with
     * @return true if the graphs are equal, false otherwise
     */
    boolean equals(Object obj);

    /**
     * Returns a hash code value for the graph.
     *
     * @return a hash code value for this graph
     */
    int hashCode();

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the graph
     */
    String toString();
}