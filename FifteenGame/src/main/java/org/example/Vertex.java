package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Vertex implements Comparable<Vertex>{

    public int priority;

    private int[][] board;
    public int processedStatesNumber;
    public int visitedStatesNumber;
    public int[][] whatWeWant;
    public List<Vertex> neighbors = new ArrayList<>();
    public Vertex parent;
    int k;
    int w;
    public int depth;
    public int zerox;
    public int zeroy;
    public int solutionSize = 0;
    public int maxx;

    public BigDecimal time;

    public String order;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        Vertex matrix = (Vertex) obj;
        return Arrays.deepEquals(this.board, matrix.board);
    }

    public void readFromFile(String file) throws IOException {
        File file1 = new File(file);
        Scanner scanner = new Scanner(file1);
        w = Integer.parseInt(scanner.next());
        k = Integer.parseInt(scanner.next());
        board = new int[k][w];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++){
                set(j, i, Integer.parseInt(scanner.next()));
            }
        }
        whatWeWant = new int[k][w];
        int count = 1;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++){
                if (j == (k - 1) && i == (w - 1)){
                    whatWeWant[j][i] = 0;
                }
                else {
                    whatWeWant[j][i] = count;
                    count++;
                }
            }
        }
        order="";
        depth = 0;
        findEmptyField();
        solutionSize = 0;
        this.priority = 0;
    }

    public int getDepth() {
        return depth;
    }

    public int[][] getWhatWeWant() {
        return whatWeWant;
    }

    public void findEmptyField() {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++) {
                if (get(j, i) == 0) {
                    zerox = j;
                    zeroy = i;
                }
            }
        }
    }


    public void set (int x, int y, int value) {
        board[x][y] = value;
    }

    public int get (int x, int y){
        return board[x][y];
    }


    public void generateNeighbors(char[] order) {
        findEmptyField();
        for (int i = 0; i < order.length; i++) {
            switch (order[i]) {
                case 'U':
                    moveUp(board, zeroy, zerox);

                    break;
                case 'D':
                    moveDown(board, zeroy, zerox);
                    break;
                case 'R':
                    moveRight(board, zeroy, zerox);
                    break;
                case 'L':
                    moveLeft(board, zeroy, zerox);
                    break;
                default:
                    System.out.print("Niepoprawne");
            }
        }

    }

    public void moveDown(int[][] newBoard, int y, int x) {
        if (y + 1 < w) {
            move(newBoard, y + 1, x, "D");

        }
    }

    public void moveUp(int[][] newBoard, int y, int x) {
        if (y - 1 >= 0) {
            move(newBoard, y - 1, x, "U");

        }
    }

    public void moveRight(int[][] newBoard, int y, int x) {
        if (x + 1 < k) {
            move(newBoard, y, x+1, "R");
       }
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();

        strb.append(order + visitedStatesNumber + processedStatesNumber);
        return strb.toString();
    }


    public void moveLeft(int[][] newBoard, int y, int x) {
        if (x - 1>= 0) {
            move(newBoard, y , x - 1, "L");

        }
    }

    public Vertex() {
    }

    public Vertex(int[][] board, int k, int w) {
        this.board = board;
        this.k = k;
        this.w = w;
        findEmptyField();
    }

    public void move(int[][] g, int i2, int i1, String letter)
    {
        int[][] newBoard = new int[k][w];
        copyBoard(newBoard, g);

        int tmp = newBoard[i1][i2];
        newBoard[i1][i2] = newBoard[zerox][zeroy];
        newBoard[zerox][zeroy] = tmp;
        Vertex child = new Vertex(newBoard, k, w);
        child.zeroy = i2;
        child.zerox = i1;
        child.parent = this;

        child.whatWeWant = this.whatWeWant;
        child.order = child.parent.order + letter;
        child.depth = this.depth + 1;
        child.priority = 0;
        neighbors.add(child);
        child.getBoard();
    }
    public void reverse() {
        Collections.reverse(neighbors);
    }

    public void copyBoard(int[][] a, int[][] b)
    {
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < k ; j++) {
                a[j][i] = b[j][i];
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println("");
        }
    }
    public int hammingMetric(){
        int hamming = 0;
        for (int i = 1; i <= w; i++) {
            for (int j = 1; j <= k; j++) {
                if (this.board[j - 1][i - 1] == (i - 1) * 4 + j || this.board[j - 1][i - 1] == 0) {
                    continue;
                } else {
                    hamming++;
                }
            }
        }
        hamming += this.depth;
        return hamming;
    }
    public int manhattan() {
        int distance = 0;
        int currentRow;
        int currentColumn;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(this.board[j][i] != 0 && this.board[j][i] != i * 4 + j + 1){
                    int boardValue = this.board[j][i] -1;
                    currentRow = i;
                    currentColumn = j;
                    int correctRow = boardValue / 4;
                    int correctColumn = boardValue % 4 - 1;
                    distance += Math.abs(correctRow - currentRow) + Math.abs(correctColumn - currentColumn);
                }
            }
        }
        distance += this.depth;
        return distance;
    }

    @Override
    public int compareTo(Vertex vertex) {

        return Integer.compare(this.priority, vertex.priority);
    }

    public void solution(String file) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(solutionSize + "\n");
        fw.write(order);
        fw.close();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    public void addition(String file, int maxDepth) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(solutionSize + "\n");
        fw.write(visitedStatesNumber + "\n");
        fw.write(processedStatesNumber + "\n");
        fw.write(maxDepth + "\n");
        fw.write(String.valueOf(time));
        fw.close();
    }
}
