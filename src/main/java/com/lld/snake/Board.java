package com.lld.snake;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private Cell[][] board;

    public  final Integer MAX_HEIGHT;
    public  final Integer MAX_WIDTH;

    public Board(int width, int height) {
        this.MAX_HEIGHT = width;
        this.MAX_WIDTH = height;
        this.board = new Cell[height][width];
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                board[row][col] = new Cell(row, col, MAX_HEIGHT, MAX_WIDTH);
            }
        }
    }

    public  Cell getCell(int row, int col) {
        return this.board[row][col];
    }

    public void printBoard() {
        String start = IntStream.range(0, 8*this.MAX_WIDTH).mapToObj(e -> "-").collect(Collectors.joining());
        System.out.printf("%s\n", start);
        for(Cell[] row: this.board) {
            String rowVal = Arrays.stream(row).map(e -> String.valueOf(e.getVal())).collect(Collectors.joining("."));
            System.out.printf("|%s|\n", rowVal);
        }
        System.out.printf("%s\n", start);
    }
}
