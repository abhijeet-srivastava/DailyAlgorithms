package com.tree;
//https://leetcode.com/problems/range-sum-query-2d-mutable/
public class NumMatrix {
    private final int rows;
    private final int cols;
    private final int[][] tree;
    private final int[][] nums;

    public NumMatrix(int[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.nums = new int[rows][cols];
        this.tree = new int[rows+1][cols+1];
        for(int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                update(r, c, matrix[r][c]);
            }
        }
    }

    public void update(int row, int col, int val) {
        int delta = val - nums[row][col];
        nums[row][col] = delta;
        for(int r = row+1; r <= rows; r += lsb(r)) {
            for(int c = col+1; c <= cols; c += lsb(c)) {
                tree[r][c] += delta;
            }
        }
    }
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefixSum(row2, col2)
                - prefixSum(row1-1, col2)
                - prefixSum(row2, col1-1)
                + prefixSum(row1-1, col1-1);
    }

    private int prefixSum(int row, int col) {
        if(row < 0 || col < 0) {
            return 0;
        }
        int sum = 0;
        for(int r = row+1; r > 0; r -= lsb(r)) {
            for(int c = col+1; c > 0; c -= lsb(c)) {
                sum += tree[r][c];
            }
        }
        return sum;
    }
    private int lsb(int i) {
        return i & (-i);
    }

    public static void main(String[] args) {
        int[][] matrix = {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}};
        NumMatrix nm = new NumMatrix(matrix);
        System.out.println("sum = " + nm.sumRegion(2, 1, 4, 3));
        nm.update(3, 2, 2);
        System.out.println("sum after update = " + nm.sumRegion(2, 1, 4, 3));
    }
}
