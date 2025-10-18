package ru.nsu.gstubarev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidenceMatrixGraphTest {

    private IncidenceMatrixGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph<>(10);
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
        graph.deleteVertex("B");
        assertFalse(graph.hasVertex("B"));
        assertFalse(graph.hasEdge("A", "B", 1));
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
    void testToString() {
        graph.addEdge("A", "B", 1);
        String result = graph.toString();
        assertTrue(result.contains("Incidence Matrix Graph"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }
}