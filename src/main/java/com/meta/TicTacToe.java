package com.meta;

public class TicTacToe {
    int[][] GRID;
    public TicTacToe(int n) {
        this.GRID = new int[n][n];
    }

    public int move(int row, int col, int player) {
        GRID[row][col] = player;
        boolean byRow = true;
        boolean byCol = true;
        boolean byDiag1 = (row == col);
        boolean byDiag2 = (row+col) == GRID.length-1;
        for(int i = 0; i < GRID.length; i++) {
            if(GRID[row][i] != player) {
                byRow = false;
            }
            if(GRID[i][col] != player) {
                byCol = false;
            }
            if(byDiag1 && GRID[i][i] != player) {
                byDiag1 = false;
            }
            if(byDiag2 && GRID[i][GRID.length -1 - i] != player) {
                byDiag2 = false;
            }
            if(!byRow && !byCol && !byDiag1 && !byDiag2) {
                return 0;
            }
        }
        return player;
    }
}
