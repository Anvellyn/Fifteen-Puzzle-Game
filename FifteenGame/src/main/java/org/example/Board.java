package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Board {

    private int[][] board;
    FileReader fr;
    int k;
    int w;
    public void readFromFile() throws IOException {
        File file = new File("C:\\Users\\patry\\Desktop\\studia\\sise\\repo\\15-Puzzle-Game\\FifteenGame\\danedowczytania.txt");
        Scanner scanner = new Scanner(file);
        w = Integer.parseInt(scanner.next());
        k = Integer.parseInt(scanner.next());
        System.out.println(k);
        System.out.println(w);
        board = new int[k][w];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++){
                set(i, j, Integer.parseInt(scanner.next()));
            }
        }
    }

    public void set (int x, int y, int value) {
        board[x][y] = value;
    }

    public int get (int x, int y){
        return board[x][y];
    }

    public void printBoard() {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println("");
        }
    }

}
