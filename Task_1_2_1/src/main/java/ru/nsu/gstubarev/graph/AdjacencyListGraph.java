package ru.nsu.gstubarev.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Graph implementation using adjacency list representation.
 *
 * @param <V> the type of vertices in the graph
 */
public class AdjacencyListGraph<V> implements Graph<V> {
    private Map<V, List<V>> adjacencyList;
    private Map<String, Integer> edgeWeights;
    private int edgeCount;

    /**
     * Constructs an empty graph.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
        this.edgeWeights = new HashMap<>();
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
        if (!adjacencyList.containsKey(vertex)) return;

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

        edgeWeights.entrySet().removeIf(entry ->
                entry.getKey().startsWith(vertex + "-") || entry.getKey().endsWith("-" + vertex));
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
        String edgeKey = from + "-" + to;
        edgeWeights.put(edgeKey, weight);
    }

    @Override
    public void deleteEdge(V from, V to, int weight) {
        if (!adjacencyList.containsKey(from)) return;
        List<V> neighbors = adjacencyList.get(from);
        if (neighbors.remove(to)) {
            edgeCount--;
            String edgeKey = from + "-" + to;
            edgeWeights.remove(edgeKey);
        }
    }

    @Override
    public boolean hasEdge(V from, V to, int weight) {
        if (!adjacencyList.containsKey(from)) return false;
        String edgeKey = from + "-" + to;
        return adjacencyList.get(from).contains(to) &&
                edgeWeights.getOrDefault(edgeKey, 0) == weight;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readFile(String name) throws IOException {
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
        }
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
                for (int i = 0; i < neighbors.size(); i++) {
                    V neighbor = neighbors.get(i);
                    String edgeKey = vertex + "-" + neighbor;
                    int weight = edgeWeights.getOrDefault(edgeKey, 1);
                    sb.append(neighbor);
                    if (weight != 1) {
                        sb.append("(").append(weight).append(")");
                    }
                    if (i < neighbors.size() - 1) {
                        sb.append(", ");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
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
}