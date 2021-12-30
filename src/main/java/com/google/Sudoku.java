package com.google;

import java.util.Arrays;

public class Sudoku {

    int[][] POSSIBLE_VALUES;
    int[][] BOARD;
    public Sudoku(char[][] sudoku) {
        this.POSSIBLE_VALUES = new int[9][9];
        this.BOARD = new int[9][9];
        for(int i = 0; i < 9; i++) {
            Arrays.fill(POSSIBLE_VALUES[i], 9);
        }
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(sudoku[i][j] == '#') {

                } else {
                    BOARD[i][j] = Character.getNumericValue(sudoku[i][j]);
                    decrementPossibleValue(i, j);
                }

            }
        }
    }

    private void decrementPossibleValue(int i, int j) {
        POSSIBLE_VALUES[i][j] = -1;
    }
}
