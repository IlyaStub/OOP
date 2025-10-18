package ru.nsu.gstubarev.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Graph implementation using adjacency matrix representation.
 *
 * @param <V> the type of vertices in the graph
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {
    private List<V> vertices;
    private double[][] matrix;
    private int edgeCount;

    /**
     * Constructs an empty graph with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the graph
     * @throws IllegalArgumentException if initialCapacity is not positive
     */
    public AdjacencyMatrixGraph(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной");
        }
        this.vertices = new ArrayList<>(initialCapacity);
        this.matrix = new double[initialCapacity][initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            Arrays.fill(matrix[i], 0);
        }
        edgeCount = 0;
    }

    private void ensureCapacity(int newSize) {
        if (newSize > matrix.length) {
            int newCapacity = Math.max(newSize, matrix.length * 2);
            double[][] newMatrix = new double[newCapacity][newCapacity];
            for (int i = 0; i < newCapacity; i++) {
                Arrays.fill(newMatrix[i], 0);
            }
            for (int i = 0; i < vertices.size(); i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, vertices.size());
            }
            matrix = newMatrix;
        }
    }

    @Override
    public void addVertex(V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Вершина не может быть null");
        }
        if (!vertices.contains(vertex)) {
            ensureCapacity(vertices.size() + 1);
            vertices.add(vertex);
        }
    }

    @Override
    public void deleteVertex(V vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return;
        }

        for (int i = 0; i < vertices.size(); i++) {
            if (matrix[vertexIndex][i] != 0) {
                edgeCount--;
            }
            if (matrix[i][vertexIndex] != 0) {
                edgeCount--;
            }
        }

        vertices.remove(vertexIndex);
        double[][] newMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(newMatrix[i], 0);
        }

        for (int i = 0, newI = 0; i < vertices.size() + 1; i++) {
            if (i == vertexIndex) {
                continue;
            }
            for (int j = 0, newJ = 0; j < vertices.size() + 1; j++) {
                if (j == vertexIndex) {
                    continue;
                }
                newMatrix[newI][newJ] = matrix[i][j];
                newJ++;
            }
            newI++;
        }
        matrix = newMatrix;
    }

    @Override
    public boolean hasVertex(V vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
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
        List<V> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex != -1) {
            for (int i = 0; i < vertices.size(); i++) {
                if (matrix[vertexIndex][i] != 0) {
                    neighbors.add(vertices.get(i));
                }
            }
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
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (matrix[fromIndex][toIndex] == 0) {
            edgeCount++;
        }
        matrix[fromIndex][toIndex] = weight;
    }

    @Override
    public void deleteEdge(V from, V to, int weight) {
        if (hasEdge(from, to, weight)) {
            int fromIndex = vertices.indexOf(from);
            int toIndex = vertices.indexOf(to);
            matrix[fromIndex][toIndex] = 0;
            edgeCount--;
        }
    }

    @Override
    public boolean hasEdge(V from, V to, int weight) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        return fromIndex != -1 && toIndex != -1 && matrix[fromIndex][toIndex] == weight;
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
                        addVertex((V) parts[1]);
                    }
                } else if (line.startsWith("e")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        V from = ((V) parts[1]);
                        V to = ((V) parts[2]);
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
        sb.append("Adjacency Matrix Graph\n");
        sb.append("Vertices: ").append(vertices.size())
                .append(", Edges: ").append(edgeCount).append("\n");
        sb.append("\t");
        for (V vertex : vertices) {
            sb.append(String.format("%s\t", vertex));
        }
        sb.append("\n");
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(String.format("%s:\t", vertices.get(i)));
            for (int j = 0; j < vertices.size(); j++) {
                sb.append(String.format("%.0f\t", matrix[i][j]));
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
        AdjacencyMatrixGraph<?> other = (AdjacencyMatrixGraph<?>) obj;
        if (vertices.size() != other.vertices.size() || edgeCount != other.edgeCount) {
            return false;
        }
        if (!new HashSet<>(vertices).equals(new HashSet<>(other.vertices))) {
            return false;
        }
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (matrix[i][j] != other.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(vertices.size(), edgeCount);
        result = 31 * result + new HashSet<>(vertices).hashCode();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                result = 31 * result + Double.hashCode(matrix[i][j]);
            }
        }
        return result;
    }
}