package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BFS {

    private char[] order;
    public BFS(char[] order) {
        this.order = order;
    }

    public int deepest = 0;


    public Vertex solve(Vertex vertex) {
        Queue<Vertex> queue = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        queue.add(vertex);
        visited.add(vertex);
        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            currentVertex.generateNeighbors(order);
            for (int i = 0; i < currentVertex.neighbors.size(); i++) {
                if (currentVertex.neighbors.get(i).depth > deepest) {
                    deepest = currentVertex.neighbors.get(i).depth;
                }
                if (Arrays.deepEquals(currentVertex.neighbors.get(i).getBoard(), currentVertex.neighbors.get(i).getWhatWeWant())) {
                    end = BigDecimal.valueOf(System.nanoTime());
                    currentVertex.neighbors.get(i).processedStatesNumber = visited.size();
                    currentVertex.neighbors.get(i).visitedStatesNumber = visited.size() + queue.size();
                    currentVertex.neighbors.get(i).solutionSize = currentVertex.neighbors.get(i).order.length();
                    return currentVertex.neighbors.get(i);
                }
                if (!visited.contains(currentVertex.neighbors.get(i))) {
                    queue.add(currentVertex.neighbors.get(i));
                    visited.add(currentVertex.neighbors.get(i));
                }
            }
        }
    return null;
    }

    public int getDeepest() {
        return deepest;
    }
}
