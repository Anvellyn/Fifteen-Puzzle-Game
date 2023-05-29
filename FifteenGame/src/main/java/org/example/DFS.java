package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.*;

public class DFS {

    char[]order;

    public DFS(char[] order) {
        this.order = order;
    }
    public int deepest = 0;


    public Vertex solve(Vertex vertex) {
        if (Arrays.deepEquals(vertex.getBoard(), vertex.getWhatWeWant())) {
            return vertex;
        }
        int counter = 0;
        Stack<Vertex> stack = new Stack<>();
        Set<Vertex> closed = new HashSet<>();
        stack.push(vertex);
        counter++;
        while (!stack.isEmpty()) {
            Vertex currentVertex = stack.pop();
            if(currentVertex.depth > deepest) {
                deepest = currentVertex.depth;
            }
            if (currentVertex.depth > 20) {
                continue;
            }

            closed.add(currentVertex);
            currentVertex.generateNeighbors(order);
            currentVertex.reverse();
            for (Vertex n : currentVertex.neighbors
            ) {
                if (Arrays.deepEquals(n.getBoard(), n.getWhatWeWant())) {
                    n.time = elapsedTime;
                    n.solutionSize = n.order.length();
                    n.processedStatesNumber = closed.size() + 1;
                    n.visitedStatesNumber = counter;
                    n.maxx = deepest;
                    return n;
                }
                if (!stack.contains(n)) {
                    stack.push(n);
                    counter++;
                }
                if (closed.contains(n)) {
                    closed.remove(n);
                }

            }
        }
        vertex.solutionSize = -1;
        vertex.processedStatesNumber = closed.size();
        vertex.visitedStatesNumber = counter;
        vertex.maxx = deepest;
        return vertex;
    }
    public int getDeepest() {
        return deepest;
    }
}


