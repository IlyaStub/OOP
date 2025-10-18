package ru.nsu.gstubarev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {

    private AdjacencyMatrixGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph<>(10);
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");

        assertTrue(graph.hasVertex("A"));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", 5);

        assertTrue(graph.hasEdge("A", "B", 5));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 2);

        List<String> neighbors = graph.getNeighbors("A");

        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
    }

    @Test
    void testDeleteVertex() {
        graph.addEdge("A", "B", 3);
        graph.addEdge("B", "C", 2);

        graph.deleteVertex("B");

        assertFalse(graph.hasVertex("B"));
        assertFalse(graph.hasEdge("A", "B", 3));
        assertFalse(graph.hasEdge("B", "C", 2));
    }

    @Test
    void testDeleteEdge() {
        graph.addEdge("A", "B", 3);
        graph.addEdge("B", "C", 2);

        // Удаляем ребро
        graph.deleteEdge("A", "B", 3);

        assertFalse(graph.hasEdge("A", "B", 3));
        assertTrue(graph.hasEdge("B", "C", 2));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B", 3);
        String result = graph.toString();

        assertTrue(result.contains("Adjacency Matrix Graph"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }

    @Test
    void testEqualsAndHashCode() {
        AdjacencyMatrixGraph<String> graph1 = new AdjacencyMatrixGraph<>(5);
        AdjacencyMatrixGraph<String> graph2 = new AdjacencyMatrixGraph<>(5);

        graph1.addEdge("A", "B", 2);
        graph2.addEdge("A", "B", 2);

        assertEquals(graph1, graph2);
        assertEquals(graph1.hashCode(), graph2.hashCode());
    }
}
