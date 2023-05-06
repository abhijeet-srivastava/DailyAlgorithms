package com.google.gfg;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class April {
    public static void main(String[] args) {
        April apr = new April();
        apr.testRotateMatrx();
    }

    private void testRotateMatrx() {
        int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
        rotateMatrix(matrix);
        //printMatrix(matrix);
    }

    private void printMatrix(int[][] matrix) {
        for(int[] arr: matrix) {
            System.out.printf("[%s]\n", IntStream.of(arr).mapToObj(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    private void rotateMatrix(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n; i++) {
            for(int j = i+1; j < n; j++) {
                if(i == j) {
                    continue;
                }
                swap(i, j, matrix);
            }
        }
        printMatrix(matrix);
    }

    private void swap(int i, int j, int[][] matrix) {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = tmp;
    }
}
