package ru.nsu.gstubarev.graph.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;

import ru.nsu.gstubarev.graph.exeptions.TopologicalSortImpossible;
import ru.nsu.gstubarev.graph.interfaces.GraphAlgorithmOperations;

/**
 * Graph algorithms implementation.
 */
public class GraphAlgorithms {

    /**
     * Performs topological sort using Kahn's algorithm.
     *
     * @param graph the graph to sort
     * @return vertices in topological order
     * @throws TopologicalSortImpossible if graph contains cycles
     */
    public static <V> List<V> topologicalSort(GraphAlgorithmOperations<V> graph) {
        List<V> result = new ArrayList<>();

        Map<V, Integer> inDegreeCache = new HashMap<>();
        for (V vertex : graph.getVertices()) {
            inDegreeCache.put(vertex, graph.getInDegree(vertex));
        }

        Queue<V> queue = new LinkedList<>();
        for (Map.Entry<V, Integer> entry : inDegreeCache.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            V vertex = queue.poll();
            result.add(vertex);
            count++;

            for (V neighbor : graph.getNeighbors(vertex)) {
                int newInDegree = inDegreeCache.get(neighbor) - 1;
                inDegreeCache.put(neighbor, newInDegree);
                if (newInDegree == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (count != inDegreeCache.size()) {
            throw new TopologicalSortImpossible("Топологическая сортировка невозможна");
        }

        return result;
    }
}
