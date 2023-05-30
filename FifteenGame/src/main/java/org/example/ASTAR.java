package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ASTAR {

    public int deepest = 0;

    public ASTAR(){

    }

    public Vertex astar(Vertex vertex, String heuristics){
        int f = 0;
        Queue<Vertex> queue = new PriorityQueue<>();
        Set<Vertex> closed = new HashSet<>();
        if (Arrays.deepEquals(vertex.getBoard(), vertex.getWhatWeWant())){
            return vertex;
        }
        queue.add(vertex);
        while(!queue.isEmpty()){
            Vertex tmp = queue.remove();
            if((Arrays.deepEquals(tmp.getBoard(), tmp.getWhatWeWant()))){
                tmp.solutionSize = tmp.order.length();
                tmp.printBoard();
                tmp.processedStatesNumber = closed.size();
                tmp.visitedStatesNumber = closed.size() + queue.size();
                return tmp;
            }
            closed.add(tmp);
            char[] myCharArray = {'L', 'U', 'R', 'D'};
            tmp.generateNeighbors(myCharArray);
            for (Vertex n : tmp.neighbors
            ) {
                if(!closed.contains(n)){
                    if (n.depth > deepest) {
                        deepest = n.depth;
                    }
                    if(Objects.equals(heuristics, "hamm")){
                        f = n.hammingMetric();

                    }
                    if(Objects.equals(heuristics, "manh")){
                        f = n.manhattan();

                    }
                    if(!queue.contains(n)){
                        n.priority = f;
                        queue.add(n);
                    }

                    else if(queue.element().priority > f){
                        queue.remove(n);
                        n.priority = f;
                        queue.add(n);
                    }
                }
            }
        }
        return null;
    }
}