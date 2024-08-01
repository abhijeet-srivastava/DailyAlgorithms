package com.lld.snake;

public class Cell {
    private int row;
    private int col;

    private char val;

    public Cell(int row, int col, Integer maxHeight, Integer maxWidth) {
        if(!isValid(row, col)) {
            throw new AssertionError("Invalid cells");
        }
        this.row = row;
        this.col = col;
        this.val = ' ';
    }

    public Cell(int row, int col, Integer maxHeight, Integer maxWidth, char val) {
        if(!isValid(row, col)) {
            throw new AssertionError("Invalid cells");
        }
        this.row = row;
        this.col = col;
        this.val = val;
    }

    private boolean isValid(int row, int col) {
        return row >= 0  || col >= 0;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val = val;
    }
}
