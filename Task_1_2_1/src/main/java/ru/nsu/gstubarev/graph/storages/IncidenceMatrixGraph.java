package ru.nsu.gstubarev.graph.storages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import ru.nsu.gstubarev.graph.algorithms.GraphAlgorithms;
import ru.nsu.gstubarev.graph.interfaces.Graph;
import ru.nsu.gstubarev.graph.interfaces.GraphAlgorithmOperations;


/**
 * Graph implementation using incidence matrix representation.
 *
 * @param <V> the type of vertices in the graph
 */
public class IncidenceMatrixGraph<V> implements Graph<V>, GraphAlgorithmOperations<V> {
    private final List<V> vertices;
    private int[][] matrix;
    private final int[] edgeWeights;
    private int edgeCount;
    private final int OUTGOING = 1;
    private final int INCOMING = -1;
    private final int NOTHING = 0;

    /**
     * Constructs an empty graph with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the graph
     * @throws IllegalArgumentException if initialCapacity is not positive
     */
    public IncidenceMatrixGraph(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной");
        }
        this.vertices = new ArrayList<>(initialCapacity);
        this.matrix = new int[initialCapacity][initialCapacity];
        this.edgeWeights = new int[initialCapacity];
        this.edgeCount = 0;
    }

    private void ensureCapacity(int newSize) {
        if (newSize > matrix.length) {
            int newCapacity = Math.max(newSize, matrix.length * 2);
            int[][] newMatrix = new int[newCapacity][newCapacity];
            for (int i = 0; i < vertices.size(); i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, edgeCount);
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

        for (int j = edgeCount - 1; j >= 0; j--) {
            if (matrix[vertexIndex][j] != NOTHING) {
                for (int i = 0; i < vertices.size(); i++) {
                    matrix[i][j] = matrix[i][edgeCount - 1];
                }
                edgeCount--;
            }
        }

        vertices.remove(vertexIndex);
        int[][] newMatrix = new int[matrix.length - 1][matrix[0].length];
        for (int i = 0, newI = 0; i < vertices.size() + 1; i++) {
            if (i == vertexIndex) {
                continue;
            }
            System.arraycopy(matrix[i], 0, newMatrix[newI], 0, edgeCount);
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
            for (int j = 0; j < edgeCount; j++) {
                if (matrix[vertexIndex][j] == OUTGOING) {
                    for (int i = 0; i < vertices.size(); i++) {
                        if (matrix[i][j] == INCOMING) {
                            neighbors.add(vertices.get(i));
                        }
                    }
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
        for (int j = 0; j < edgeCount; j++) {
            if (matrix[fromIndex][j] == OUTGOING && matrix[toIndex][j] == INCOMING
                    && edgeWeights[j] == weight) {
                return;
            }
        }
        ensureCapacity(edgeCount + 1);
        for (int i = 0; i < vertices.size(); i++) {
            if (i == fromIndex) {
                matrix[i][edgeCount] = OUTGOING;
            } else if (i == toIndex) {
                matrix[i][edgeCount] = INCOMING;
            } else {
                matrix[i][edgeCount] = NOTHING;
            }
        }
        edgeWeights[edgeCount] = weight;
        edgeCount++;
    }

    @Override
    public void deleteEdge(V from, V to, int weight) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex == -1 || toIndex == -1) {
            return;
        }

        for (int j = 0; j < edgeCount; j++) {
            if (matrix[fromIndex][j] == OUTGOING && matrix[toIndex][j] == INCOMING
                    && edgeWeights[j] == weight) {
                for (int i = 0; i < vertices.size(); i++) {
                    for (int k = j; k < edgeCount - 1; k++) {
                        matrix[i][k] = matrix[i][k + 1];
                    }
                }
                for (int k = j; k < edgeCount - 1; k++) {
                    edgeWeights[k] = edgeWeights[k + 1];
                }
                edgeCount--;
                break;
            }
        }
    }

    @Override
    public boolean hasEdge(V from, V to, int weight) {
        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);
        if (fromIndex == -1 || toIndex == -1) {
            return false;
        }

        for (int j = 0; j < edgeCount; j++) {
            if (matrix[fromIndex][j] == OUTGOING && matrix[toIndex][j] == INCOMING
                    && edgeWeights[j] == weight) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incidence Matrix Graph\n");
        sb.append("Vertices: ").append(vertices.size())
                .append(", Edges: ").append(edgeCount).append("\n");
        sb.append("\t");
        for (int j = 0; j < edgeCount; j++) {
            sb.append(String.format("e%d\t", j));
        }
        sb.append("\n");
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(String.format("%s:\t", vertices.get(i)));
            for (int j = 0; j < edgeCount; j++) {
                sb.append(String.format("%d\t", matrix[i][j]));
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
        IncidenceMatrixGraph<?> other = (IncidenceMatrixGraph<?>) obj;
        if (vertices.size() != other.vertices.size() || edgeCount != other.edgeCount) {
            return false;
        }
        if (!new HashSet<>(vertices).equals(new HashSet<>(other.vertices))) {
            return false;
        }
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < edgeCount; j++) {
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
            for (int j = 0; j < edgeCount; j++) {
                result = 31 * result + matrix[i][j];
            }
        }
        return result;
    }

    @Override
    public Set<V> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public int getInDegree(V vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return 0;
        }

        int inDegree = 0;
        for (int j = 0; j < edgeCount; j++) {
            if (matrix[vertexIndex][j] == INCOMING) {
                inDegree++;
            }
        }
        return inDegree;
    }

    @Override
    public List<V> getIncomingNeighbors(V vertex) {
        List<V> incomingNeighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            return incomingNeighbors;
        }

        for (int j = 0; j < edgeCount; j++) {
            if (matrix[vertexIndex][j] == INCOMING) {
                for (int i = 0; i < vertices.size(); i++) {
                    if (matrix[i][j] == OUTGOING) {
                        incomingNeighbors.add(vertices.get(i));
                    }
                }
            }
        }
        return incomingNeighbors;
    }

    /**
     * The wrapper method.
     *
     * @return topological sorted graph
     */
    public List<V> topologicalSort() {
        return GraphAlgorithms.topologicalSort(this);
    }
}