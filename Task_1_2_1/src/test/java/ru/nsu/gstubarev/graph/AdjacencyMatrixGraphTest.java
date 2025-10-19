package ru.nsu.gstubarev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.gstubarev.graph.storages.AdjacencyListGraph;
import ru.nsu.gstubarev.graph.storages.AdjacencyMatrixGraph;

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
    void testAddEdgeUpdatesWeight() {
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "B", 10);

        assertTrue(graph.hasEdge("A", "B", 10));
        assertFalse(graph.hasEdge("A", "B", 5));
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

        graph.deleteEdge("A", "B", 3);

        assertFalse(graph.hasEdge("A", "B", 3));
        assertTrue(graph.hasEdge("B", "C", 2));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testDeleteNonExistentEdge() {
        graph.addEdge("A", "B", 1);
        graph.deleteEdge("A", "C", 1);
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testDeleteEdgeWithWrongWeight() {
        graph.addEdge("A", "B", 3);
        graph.deleteEdge("A", "B", 5);
        assertTrue(graph.hasEdge("A", "B", 3));
    }

    @Test
    void testHasVertex() {
        graph.addVertex("A");
        assertTrue(graph.hasVertex("A"));
        assertFalse(graph.hasVertex("B"));
    }

    @Test
    void testHasEdge() {
        graph.addEdge("A", "B", 5);
        assertTrue(graph.hasEdge("A", "B", 5));
        assertFalse(graph.hasEdge("A", "C", 5));
    }

    @Test
    void testGetVertexCount() {
        assertEquals(0, graph.getVertexCount());
        graph.addVertex("A");
        assertEquals(1, graph.getVertexCount());
        graph.addEdge("B", "C", 1);
        assertEquals(3, graph.getVertexCount());
    }

    @Test
    void testGetEdgeCount() {
        assertEquals(0, graph.getEdgeCount());
        graph.addEdge("A", "B", 1);
        assertEquals(1, graph.getEdgeCount());
        graph.addEdge("B", "C", 2);
        assertEquals(2, graph.getEdgeCount());
        graph.deleteEdge("A", "B", 1);
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

    @Test
    void testCapacityExpansion() {
        AdjacencyMatrixGraph<String> smallGraph = new AdjacencyMatrixGraph<>(2);

        smallGraph.addVertex("A");
        smallGraph.addVertex("B");
        smallGraph.addVertex("C");

        assertEquals(3, smallGraph.getVertexCount());
        assertTrue(smallGraph.hasVertex("A"));
        assertTrue(smallGraph.hasVertex("B"));
        assertTrue(smallGraph.hasVertex("C"));
    }

    @Test
    void testReadFile() throws IOException {
        String testFileName = "graph.txt";

        try {
            Files.write(Paths.get(testFileName), Arrays.asList(
                    "# Test graph file",
                    "v A",
                    "v B",
                    "v C",
                    "e A B 1",
                    "e B C 2",
                    "e A C 3"
            ));

            graph.readFile(testFileName);

            assertTrue(graph.hasVertex("A"));
            assertTrue(graph.hasVertex("B"));
            assertTrue(graph.hasVertex("C"));
            assertEquals(3, graph.getVertexCount());

            assertTrue(graph.hasEdge("A", "B", 1));
            assertTrue(graph.hasEdge("B", "C", 2));
            assertTrue(graph.hasEdge("A", "C", 3));
            assertEquals(3, graph.getEdgeCount());

        } finally {
            Files.deleteIfExists(Paths.get(testFileName));
        }
    }

    @Test
    void testGetVertices() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "C", 1);

        Set<String> vertices = graph.getVertices();

        assertEquals(3, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
    }

    @Test
    void testGetInDegree() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "B", 1);
        graph.addEdge("B", "D", 1);

        assertEquals(0, graph.getInDegree("A"));
        assertEquals(2, graph.getInDegree("B"));
        assertEquals(0, graph.getInDegree("C"));
        assertEquals(1, graph.getInDegree("D"));
        assertEquals(0, graph.getInDegree("X"));
    }

    @Test
    void testGetIncomingNeighbors() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "B", 1);
        graph.addEdge("B", "D", 1);

        List<String> incomingToB = graph.getIncomingNeighbors("B");
        List<String> incomingToD = graph.getIncomingNeighbors("D");
        List<String> incomingToA = graph.getIncomingNeighbors("A");

        assertEquals(2, incomingToB.size());
        assertTrue(incomingToB.contains("A"));
        assertTrue(incomingToB.contains("C"));

        assertEquals(1, incomingToD.size());
        assertTrue(incomingToD.contains("B"));

        assertTrue(incomingToA.isEmpty());
    }

    @Test
    void testTopologicalSortMethod() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);

        List<String> result = graph.topologicalSort();

        assertEquals(3, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
    }
}
