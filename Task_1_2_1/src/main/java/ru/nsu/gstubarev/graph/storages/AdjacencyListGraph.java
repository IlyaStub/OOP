package ru.nsu.gstubarev.graph.storages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import ru.nsu.gstubarev.graph.algorithms.GraphAlgorithms;
import ru.nsu.gstubarev.graph.interfaces.Graph;
import ru.nsu.gstubarev.graph.interfaces.GraphAlgorithmOperations;

/**
 * Graph implementation using adjacency list representation.
 *
 * @param <V> the type of vertices in the graph
 */
public class AdjacencyListGraph<V> implements Graph<V>, GraphAlgorithmOperations<V> {
    private final Map<V, List<Edge<V>>> adjacencyList;
    private int edgeCount;

    private static class Edge<V> {
        final V target;
        final int weight;

        Edge(V target, int weight) {
            this.target = target;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Edge<?> edge = (Edge<?>) o;
            return weight == edge.weight && Objects.equals(target, edge.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(target, weight);
        }
    }

    /**
     * Constructs an empty graph.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Вершина не может быть null");
        }
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void deleteVertex(V vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return;
        }

        edgeCount -= adjacencyList.get(vertex).size();
        adjacencyList.remove(vertex);

        for (List<Edge<V>> neighbors : adjacencyList.values()) {
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                if (neighbors.get(i).target.equals(vertex)) {
                    neighbors.remove(i);
                    edgeCount--;
                }
            }
        }
    }

    @Override
    public boolean hasVertex(V vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Returns the number of edges in the graph.
     *
     * @return the number of edges
     */
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public List<V> getNeighbors(V vertex) {
        List<Edge<V>> edges = adjacencyList.getOrDefault(vertex, new ArrayList<>());
        List<V> neighbors = new ArrayList<>();
        for (Edge<V> edge : edges) {
            neighbors.add(edge.target);
        }
        return neighbors;
    }

    @Override
    public void addEdge(V from, V to, int weight) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Вершины не могут быть null");
        }
        addVertex(from);
        addVertex(to);
        List<Edge<V>> neighbors = adjacencyList.get(from);
        Edge<V> newEdge = new Edge<>(to, weight);
        if (!neighbors.contains(newEdge)) {
            neighbors.add(newEdge);
            edgeCount++;
        }
    }

    @Override
    public void deleteEdge(V from, V to, int weight) {
        if (!adjacencyList.containsKey(from)) {
            return;
        }
        List<Edge<V>> neighbors = adjacencyList.get(from);
        Edge<V> edgeToRemove = new Edge<>(to, weight);
        if (neighbors.remove(edgeToRemove)) {
            edgeCount--;
        }
    }

    @Override
    public boolean hasEdge(V from, V to, int weight) {
        if (!adjacencyList.containsKey(from)) {
            return false;
        }
        List<Edge<V>> edges = adjacencyList.get(from);
        return edges.contains(new Edge<>(to, weight));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency List Graph\n");
        sb.append("Vertices: ").append(adjacencyList.size())
                .append(", Edges: ").append(edgeCount).append("\n");
        for (V vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(" -> ");
            List<Edge<V>> neighbors = adjacencyList.get(vertex);
            if (neighbors.isEmpty()) {
                sb.append("[]");
            } else {
                sb.append("[");
                for (int i = 0; i < neighbors.size(); i++) {
                    Edge<V> neighbor = neighbors.get(i);
                    sb.append(neighbor.target);
                    sb.append("(").append(neighbor.weight).append(")");
                    if (i < neighbors.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AdjacencyListGraph<?> other = (AdjacencyListGraph<?>) obj;
        if (adjacencyList.size() != other.adjacencyList.size() || edgeCount != other.edgeCount) {
            return false;
        }

        if (!adjacencyList.keySet().equals(other.adjacencyList.keySet())) {
            return false;
        }

        for (V vertex : adjacencyList.keySet()) {
            List<Edge<V>> thisEdges = adjacencyList.get(vertex);
            List<?> otherEdges = other.adjacencyList.get(vertex);

            if (!new HashSet<>(thisEdges).equals(new HashSet<>(otherEdges))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(adjacencyList.size(), edgeCount);
        result = 31 * result + adjacencyList.keySet().hashCode();
        for (List<Edge<V>> edges : adjacencyList.values()) {
            result = 31 * result + new HashSet<>(edges).hashCode();
        }
        return result;
    }

    @Override
    public Set<V> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public int getInDegree(V vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return 0;
        }

        int inDegree = 0;
        for (List<Edge<V>> edges : adjacencyList.values()) {
            for (Edge<V> edge : edges) {
                if (edge.target.equals(vertex)) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    @Override
    public List<V> getIncomingNeighbors(V vertex) {
        List<V> incomingNeighbors = new ArrayList<>();
        if (!adjacencyList.containsKey(vertex)) {
            return incomingNeighbors;
        }

        for (Map.Entry<V, List<Edge<V>>> entry : adjacencyList.entrySet()) {
            V sourceVertex = entry.getKey();
            List<Edge<V>> edges = entry.getValue();
            for (Edge<V> edge : edges) {
                if (edge.target.equals(vertex)) {
                    incomingNeighbors.add(sourceVertex);
                    break;
                }
            }
        }
        return incomingNeighbors;
    }

    /**
     * The wrapper method for topological sort.
     *
     * @return topological sorted graph
     */
    public List<V> topologicalSort() {
        return GraphAlgorithms.topologicalSort(this);
    }
}