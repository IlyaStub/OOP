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
import ru.nsu.gstubarev.graph.exeptions.GraphFileReadException;
import ru.nsu.gstubarev.graph.storages.IncidenceMatrixGraph;

class IncidenceMatrixGraphTest {

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
    void testAddEdgeDuplicate() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "B", 1);
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testAddEdgeWithDifferentWeight() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "B", 2);
        assertTrue(graph.hasEdge("A", "B", 1));
        assertTrue(graph.hasEdge("A", "B", 2));
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testDeleteVertex() {
        graph.addEdge("A", "B", 1);
        graph.deleteVertex("B");
        assertFalse(graph.hasVertex("B"));
        assertFalse(graph.hasEdge("A", "B", 1));
    }

    @Test
    void testDeleteNonExistentEdge() {
        graph.addEdge("A", "B", 1);
        graph.deleteEdge("A", "C", 1);
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testDeleteEdgeWithWrongWeight() {
        graph.addEdge("A", "B", 1);
        graph.deleteEdge("A", "B", 2);
        assertTrue(graph.hasEdge("A", "B", 1));
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
    void testGetNeighborsNonExistentVertex() {
        List<String> neighbors = graph.getNeighbors("X");
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B", 1);
        String result = graph.toString();
        assertTrue(result.contains("Incidence Matrix Graph"));
        assertTrue(result.contains("Vertices: 2, Edges: 1"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }

    @Test
    void testGetVertexCount() {
        assertEquals(0, graph.getVertexCount());
        graph.addVertex("A");
        assertEquals(1, graph.getVertexCount());
        graph.addVertex("B");
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testGetEdgeCount() {
        assertEquals(0, graph.getEdgeCount());
        graph.addEdge("A", "B", 1);
        assertEquals(1, graph.getEdgeCount());
        graph.addEdge("B", "C", 2);
        assertEquals(2, graph.getEdgeCount());
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
    void testEqualsAndHashCode() {
        IncidenceMatrixGraph<String> graph1 = new IncidenceMatrixGraph<>(5);
        IncidenceMatrixGraph<String> graph2 = new IncidenceMatrixGraph<>(5);

        graph1.addEdge("A", "B", 1);
        graph1.addEdge("B", "C", 2);

        graph2.addEdge("A", "B", 1);
        graph2.addEdge("B", "C", 2);

        assertEquals(graph1, graph2);
        assertEquals(graph1.hashCode(), graph2.hashCode());

        graph2.addEdge("C", "D", 3);
        assertFalse(graph1.equals(graph2));
    }

    @Test
    void testEqualsWithDifferentOrder() {
        IncidenceMatrixGraph<String> graph1 = new IncidenceMatrixGraph<>(5);
        IncidenceMatrixGraph<String> graph2 = new IncidenceMatrixGraph<>(5);

        graph1.addVertex("A");
        graph1.addVertex("B");

        graph2.addVertex("B");
        graph2.addVertex("A");

        assertEquals(graph1, graph2);
    }

    @Test
    void testCapacityExpansion() {
        IncidenceMatrixGraph<String> smallGraph = new IncidenceMatrixGraph<>(2);

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
    void testExceptionCreation() {
        GraphFileReadException exception = new GraphFileReadException("testic");

        assertEquals("testic", exception.getMessage());
    }

    @Test
    void testGetVertices() {
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
        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "B", 1);
        graph.addEdge("B", "D", 1);

        List<String> incomingToB = graph.getIncomingNeighbors("B");
        assertEquals(2, incomingToB.size());
        assertTrue(incomingToB.contains("A"));
        assertTrue(incomingToB.contains("C"));
        List<String> incomingToD = graph.getIncomingNeighbors("D");
        assertEquals(1, incomingToD.size());
        assertTrue(incomingToD.contains("B"));
        List<String> incomingToA = graph.getIncomingNeighbors("A");
        assertTrue(incomingToA.isEmpty());
    }

    @Test
    void testTopologicalSortMethod() {
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);

        List<String> result = graph.topologicalSort();

        assertEquals(3, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
    }
}