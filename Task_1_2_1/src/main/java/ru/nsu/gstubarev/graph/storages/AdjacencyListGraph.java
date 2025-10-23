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
    private final Map<V, List<V>> adjacencyList;
    private final Map<String, Integer> edgeWeights;
    private int edgeCount;

    /**
     * Constructs an empty graph.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
        this.edgeWeights = new HashMap<>();
        this.edgeCount = 0;
    }

    private String sanitizeVertex(V vertex) {
        if (vertex == null) {
            return "null";
        }
        return vertex.toString().replace("-", "_");
    }

    private String createEdgeKey(V from, V to) {
        return sanitizeVertex(from) + "-" + sanitizeVertex(to);
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

        for (List<V> neighbors : adjacencyList.values()) {
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                if (neighbors.get(i).equals(vertex)) {
                    neighbors.remove(i);
                    edgeCount--;
                }
            }
        }

        String sanitizedVertex = sanitizeVertex(vertex);
        edgeWeights.entrySet().removeIf(entry -> {
            String key = entry.getKey();
            return key.startsWith(sanitizedVertex + "-") || key.endsWith("-" + sanitizedVertex);
        });
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
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void addEdge(V from, V to, int weight) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Вершины не могут быть null");
        }
        addVertex(from);
        addVertex(to);
        List<V> neighbors = adjacencyList.get(from);
        if (!neighbors.contains(to)) {
            neighbors.add(to);
            edgeCount++;
        }
        String edgeKey = createEdgeKey(from, to);
        edgeWeights.put(edgeKey, weight);
    }

    @Override
    public void deleteEdge(V from, V to, int weight) {
        if (!adjacencyList.containsKey(from)) {
            return;
        }
        List<V> neighbors = adjacencyList.get(from);
        if (neighbors.remove(to)) {
            edgeCount--;
            String edgeKey = createEdgeKey(from, to);
            edgeWeights.remove(edgeKey);
        }
    }

    @Override
    public boolean hasEdge(V from, V to, int weight) {
        if (!adjacencyList.containsKey(from)) {
            return false;
        }
        String edgeKey = createEdgeKey(from, to);
        return adjacencyList.get(from).contains(to)
                && edgeWeights.getOrDefault(edgeKey, 0) == weight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency List Graph\n");
        sb.append("Vertices: ").append(adjacencyList.size())
                .append(", Edges: ").append(edgeCount).append("\n");
        for (V vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(" -> ");
            List<V> neighbors = adjacencyList.get(vertex);
            if (neighbors.isEmpty()) {
                sb.append("[]");
            } else {
                sb.append("[");
                for (int i = 0; i < neighbors.size(); i++) {
                    V neighbor = neighbors.get(i);
                    String edgeKey = createEdgeKey(vertex, neighbor);
                    int weight = edgeWeights.getOrDefault(edgeKey, 1);
                    sb.append(neighbor);
                    if (weight != 1) {
                        sb.append("(").append(weight).append(")");
                    }
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
            if (!new HashSet<>(adjacencyList.get(vertex)).equals(
                    new HashSet<>(other.adjacencyList.get(vertex)))) {
                return false;
            }
        }
        return edgeWeights.equals(other.edgeWeights);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(adjacencyList.size(), edgeCount);
        result = 31 * result + adjacencyList.keySet().hashCode();
        for (List<V> neighbors : adjacencyList.values()) {
            result = 31 * result + new HashSet<>(neighbors).hashCode();
        }
        result = 31 * result + edgeWeights.hashCode();
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
        for (Map.Entry<V, List<V>> entry : adjacencyList.entrySet()) {
            if (entry.getValue().contains(vertex)) {
                inDegree++;
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

        for (Map.Entry<V, List<V>> entry : adjacencyList.entrySet()) {
            V sourceVertex = entry.getKey();
            List<V> neighbors = entry.getValue();
            if (neighbors.contains(vertex)) {
                incomingNeighbors.add(sourceVertex);
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