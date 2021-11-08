package com.google;

public class SudokuSolver1 {

    private void solveSudoku(char[][] board){
        if(board == null || board.length == 0) {
            return;
        }
        solve(board);
    }

    private boolean solve(char[][] board) {
        for(int  i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] == '.') {
                    for(char ch = '1'; ch < '9'; ch++) {
                        if(isValid(i, j, ch, board)) {
                            board[i][j] = ch;
                        } else {
                            continue;
                        }
                        if(solve(board)) {
                            return true;
                        } else {
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, char ch, char[][] board) {
        for(int i = 0; i < 9; i++) {
            if(board[row][i] == ch
                    || board[i][col] == ch
                    || board[3*(row/3) + i/3][3*(col/3) + i%3] == ch) {
                return false;
            }
        }
        return true;
    }

}
