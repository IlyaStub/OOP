package ru.nsu.gstubarev.graph.interfaces;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ru.nsu.gstubarev.graph.exeptions.GraphFileReadException;

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
     */
    @SuppressWarnings("unchecked")
    default void readFile(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("v")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        V vertex = (V) parts[1];
                        addVertex(vertex);
                    }
                } else if (line.startsWith("e")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        V from = (V) parts[1];
                        V to = (V) parts[2];
                        if (parts.length >= 4) {
                            addEdge(from, to, Integer.parseInt(parts[3]));
                        } else {
                            addEdge(from, to, 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new GraphFileReadException("Failed to read graph from file: " + name, e);
        }
    }

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