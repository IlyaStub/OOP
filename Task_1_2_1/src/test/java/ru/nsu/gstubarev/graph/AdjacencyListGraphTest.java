package ru.nsu.gstubarev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AdjacencyListGraphTest {

    private AdjacencyListGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        assertTrue(graph.hasVertex("A"));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdge() {
        graph.addEdge("A", "B", 1);
        assertTrue(graph.hasEdge("A", "B", 1));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testDeleteVertex() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);

        graph.deleteVertex("B");

        assertFalse(graph.hasVertex("B"));
        assertFalse(graph.hasEdge("A", "B", 1));
        assertFalse(graph.hasEdge("B", "C", 2));
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testDeleteEdge() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);

        graph.deleteEdge("A", "B", 1);

        assertFalse(graph.hasEdge("A", "B", 1));
        assertTrue(graph.hasEdge("B", "C", 2));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);

        List<String> neighbors = graph.getNeighbors("A");

        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
    }

    @Test
    void testHasVertex() {
        graph.addVertex("A");
        assertTrue(graph.hasVertex("A"));
        assertFalse(graph.hasVertex("B"));
    }

    @Test
    void testHasEdge() {
        graph.addEdge("A", "B", 1);
        assertTrue(graph.hasEdge("A", "B", 1));
        assertFalse(graph.hasEdge("A", "C", 1));
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B", 1);
        String result = graph.toString();

        assertTrue(result.contains("Adjacency List Graph"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }
}