package ru.nsu.gstubarev.graph.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.graph.exeptions.TopologicalSortImpossible;
import ru.nsu.gstubarev.graph.storages.AdjacencyListGraph;

class GraphAlgorithmsTest {

    @Test
    void testTopologicalSortLinearGraph() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(3, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
    }

    @Test
    void testTopologicalSortDag() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 1);
        graph.addEdge("B", "D", 1);
        graph.addEdge("C", "D", 1);

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(4, result.size());
        assertTrue(result.indexOf("A") < result.indexOf("B"));
        assertTrue(result.indexOf("A") < result.indexOf("C"));
        assertTrue(result.indexOf("B") < result.indexOf("D"));
        assertTrue(result.indexOf("C") < result.indexOf("D"));
    }

    @Test
    void testTopologicalSortWithCycle() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "A", 1);

        assertThrows(TopologicalSortImpossible.class, () -> {
            GraphAlgorithms.topologicalSort(graph);
        });
    }

    @Test
    void testTopologicalSortSingleVertex() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addVertex("A");

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
    }

    @Test
    void testTopologicalSortEmptyGraph() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertTrue(result.isEmpty());
    }

    @Test
    void testTopologicalSortMultipleRoots() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "C", 1);
        graph.addEdge("B", "C", 1);

        List<String> result = GraphAlgorithms.topologicalSort(graph);

        assertEquals(3, result.size());
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        assertEquals("C", result.get(2));
    }
}