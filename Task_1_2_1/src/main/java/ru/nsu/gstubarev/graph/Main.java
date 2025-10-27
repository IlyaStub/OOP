package ru.nsu.gstubarev.graph;

import ru.nsu.gstubarev.graph.storages.AdjacencyListGraph;
import ru.nsu.gstubarev.graph.storages.AdjacencyMatrixGraph;
import ru.nsu.gstubarev.graph.storages.IncidenceMatrixGraph;

/**
 * Demonstration class for testing graph implementations.
 */
public class Main {

    /**
     * Main method to demonstrate graph functionality.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        testAdjacencyMatrixGraph();
        testIncidenceMatrixGraph();
        testAdjacencyListGraph();
    }


    private static void testAdjacencyMatrixGraph() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(5);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "D", 3);
        graph.addEdge("C", "D", 4);

        System.out.println(graph);
    }

    private static void testIncidenceMatrixGraph() {
        IncidenceMatrixGraph<String> graph = new IncidenceMatrixGraph<>(5);

        graph.addVertex("X");
        graph.addVertex("Y");
        graph.addVertex("Z");

        graph.addEdge("X", "Y", 24);
        graph.addEdge("Y", "Z", 2);
        graph.addEdge("Z", "X", 3);

        System.out.println(graph);
    }

    private static void testAdjacencyListGraph() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge("1", "2", 5);
        graph.addEdge("1", "3", 2);
        graph.addEdge("2", "4", 8);
        graph.addEdge("3", "4", 3);
        graph.addEdge("4", "1", 12);

        System.out.println(graph);
    }
}