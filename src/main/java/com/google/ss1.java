package com.google;

public class ss1 {
    private boolean solve(char[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == '.') {
                    for(char k = '1'; k <= '9'; k++) {
                        if(isValid(board, i, j, k)) {
                            board[i][j] = k;
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

    private boolean isValid(char[][] board, int row, int col, char ch) {
        for(int  i = 0; i < 9; i++) {
            if(board[row][i] == ch
                    || board[i][col] == ch
                    || board[3 * (row / 3) + i / 3][ 3 * (col / 3) + i % 3] == ch) {
                return false;
            }
        }
        return true;
    }
}
