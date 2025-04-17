package com.uber;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TestArray {

    public static void main(String[] args) {
        TestArray ta = new TestArray();
        //ta.testMoveEvenFront();
        //ta.testNegativeToFront();
        //ta.testDutchFlag();
        //ta.testPartitionArr();
        //ta.testSort();
        //ta.validateSubSets();
        //ta.validatePermutations();
        //ta.validateLongestCommonSubstirng();
        //ta.testNQueens();
        ta.testH2OOutput();
    }

    private void testH2OOutput() {
        String molecules = "HHO HHO HOH HOH OHH HHO HHO HHO HHO HHO OHH HOH HHH HOOHHOHHOOHHOHHHOHOHH";
        for(int i = 0; i < molecules.length(); i += 3) {
            System.out.printf("Atom[%d,%d]: [%s]\n", i, i+3, molecules.substring(i, i+3));
        }
    }

    private void testNQueens() {
        List<List<String>> res = solveNQueens(4);
        for(List<String> board: res) {
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.printf("%s\n", board.stream().collect(Collectors.joining(",\n")));
        }
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] board = new char[n][n];
        for(char[] row: board) {
            Arrays.fill(row, '.');
        }
        backtrack(0, board, res);
        return res;
    }

    private void backtrack(int row, char[][] board, List<List<String>> res) {
        int n = board.length;
        if(row == n) {
            res.add(Arrays.stream(board).map(boardRow -> new String(boardRow)).toList());
        }
        for(int col = 0; col < n; col++) {
            if(canPlaceQueen(row, col, board)) {
                board[row][col] = 'Q';
                backtrack(row+1, board, res);
                board[row][col] = '.';
            }
        }
    }
    private boolean canPlaceQueen(int row, int col, char[][] board) {
        int n = board.length;
        for(int i = 0; i < row; i++) {
            if(board[i][col] == 'Q') {
                return false;
            }
            // (r, c) => (r-1,c-1), (r-1)(c+1) X
            // 2, 3 =>(0, 1) (1,2)
            //1,3 => 0,2, 0, 4

        }
        return true;
    }

    private void validateLongestCommonSubstirng() {
        String text1 = "abcde", text2 = "abdce";
        int max = longestCommonSubstring(text1, text2);
        System.out.printf("Max common len: %d\n", max);
    }

    private int longestCommonSubstring(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] DP = new int[m+1][n+1];
        for(int l1 = 1; l1 <= m; l1++) {
            for(int l2 = 1; l2 <= n; l2++) {
                if(text1.charAt(l1-1) == text2.charAt(l2-1)) {
                    DP[l1][l2] = Math.max(
                            1 + DP[l1-1][l2-1],
                            Math.max(DP[l1][l2-1], DP[l1-1][l2])
                    );
                } else {
                    DP[l1][l2] = Math.max(DP[l1][l2-1], DP[l1-1][l2]);
                }
            }
        }
        return DP[m][n];
    }

    private void validatePermutations() {
        //int[] arr = {1,2,3,4};
        int[] arr = {2,4,7,3,8};
        //generatePermutationsIterative(arr);
        List<int[]> res = generatePermutations(arr);
        for(int i = 0; i < res.size(); i++) {
            System.out.printf("Permutation-%d = [%s]\n", i, arrToString(res.get(i)));
        }
    }

    private void validateSubSets() {
        int[] arr = {6, 9, 11, 3, 8};
        List<List<Integer>> res = generateSubsets(arr);
        for(int i = 0; i < res.size(); i++) {
            System.out.printf("Subset-%d (%s)= [%s]\n", i, Integer.toBinaryString(i), res.get(i).stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
    }

    private void testSort() {
        int[] arr = {6, 9, 11, 3, 8, 5, 12, 19, 21, 2,5, 10,15,22};
        //bubbleSort(arr);
        quickSort(arr);
        System.out.printf("After sort:[%s]\n", arrToString(arr));
    }

    private void quickSort(int[] arr) {
        Random rand = new Random(System.currentTimeMillis());
        quickSort(0, arr.length-1, arr, rand);
    }

    private void quickSort(int l, int r, int[] arr, Random rand) {
        if(l >= r) {
            return;
        }
        int pivot = partition(l, r, arr, rand);
        quickSort(l, pivot-1, arr, rand);
        quickSort(pivot+1, r, arr, rand);
    }

    private List<List<Integer>> generateSubsets(int[] arr) {
        int len = arr.length;
        int permCount = (1 << len);
        List<List<Integer>> res = new ArrayList<>();
        for(int perm = 0; perm < permCount; perm++) {
            List<Integer> currPerm = new ArrayList<>();
            for(int i = 0; i < len; i++) {
                if((perm & (1<<i)) != 0) {
                    currPerm.add(arr[i]);
                }
            }
            res.add(currPerm);
        }
        return res;
    }

    private void generatePermutationsIterative(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        int idx = 0;
        System.out.printf("Permutation-%d = [%s]\n", idx++, arrToString(arr));
        for(int i = 1; i < n; i++) {
            if(dp[i] < i) {
                if(i%2 == 0) {
                    swap(0, i, arr);
                } else {
                    swap(dp[i], i, arr);
                }
                System.out.printf("Permutation-%d = [%s]\n", idx++, arrToString(arr));
                dp[i] += 1;
                i = 1;
            } else {
                dp[i] = 0;
                i += 1;
            }
        }

    }
    private List<int[]> generatePermutations(int[] arr) {
        int n = arr.length;
        List<int[]> res = new ArrayList<>();
        permutations(n, arr, res);
        return res;
    }

    private void permutations(int n, int[] arr, List<int[]> res) {
        if(n == 1) {
            //System.out.printf("Permutation: [%s]\n", arrToString(arr));
            res.add(arr.clone());
            return;
        }
        boolean isEven = n%2 == 0;
        permutations(n-1, arr, res);
        for(int i = 0; i < n-1; i++) {
            if(isEven) {
               swap(i, n-1, arr);
            } else {
                swap(0, n-1, arr);
            }
            permutations(n-1, arr, res);
        }
    }

    private int partition(int l, int r, int[] arr, Random rand) {
        int pi = l + rand.nextInt(r-l);
        int pivot = arr[pi];
        swap(pi, r, arr);
        int si = l;
        for(int i = l; i <= r; i++){
            if(arr[i] < pivot) {
                swap(si, i, arr);
                si += 1;
            }
        }
        swap(si, r, arr);
        return si;
    }


    private void testPartitionArr() {
       int[] arr = {6, 9, 11, 3, 8, 5, 12, 19, 21, 2,5, 10,15,22};
        //int[] arr = {6, 9, 11, 3, 8, 5, 19, 21};
        partitionArr(7, 12, arr);
        System.out.printf("Flag:[%s]\n", arrToString(arr));
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for(int i = n-1; i >= 0; i--) {
            for(int j = 0; j < i; j++) {
                if(arr[j] > arr[j+1]) {
                    swap(j, j+1, arr);
                }
            }
        }
    }

    private void partitionArr(int l, int h, int[] arr) {
        for(int i = 0, j = 0, k = arr.length-1; j <= k; ) {
            if(arr[j] < l) {
                swap(i, j, arr);
                i += 1;
                j += 1;
            } else if(arr[j] >= l && arr[j] <= h) {
                j += 1;
            } else {
                swap(j, k, arr);
                k -= 1;
            }
        }
    }

    private void testDutchFlag() {
        int[] arr = {2,1,0,2,2,1,1,1,0,2,1,0,2,0,0,1,2};
        solveDutchFlag(arr);
        System.out.printf("Flag:[%s]\n", arrToString(arr));
    }

    private void solveDutchFlag(int[] arr) {
        for(int i = 0, j = 0, k = arr.length-1; j < k; ){
            if(arr[j] == 0) {
                swap(i, j, arr);
                i += 1;
                j += 1;
            } else if(arr[j] == 1) {
                j += 1;
            } else {
                swap(j, k, arr);
                k -= 1;
            }
        }
    }

    private void testNegativeToFront() {
        int[] arr = {2, -9, 10, 12, 5, -2, 10, -42};
        moveNegativeToFront(arr);
        System.out.printf("arr[%s]\n", arrToString(arr));
    }

    private void moveNegativeToFront(int[] arr) {
        for(int i = 0, si = 0; i < arr.length; i++) {
            if(arr[i] < 0) {
                swap(si, i, arr);
                si += 1;
            }
        }
    }

    private void testMoveEvenFront() {
        int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12};
        moveEvenToFront(arr);
        System.out.printf("arr[%s]\n", arrToString(arr));
    }

    private void moveEvenToFront(int[] arr) {
        for(int i = 0, si = 0; i < arr.length;i++) {
            if(arr[i]%2 == 2) {
                swap(si, i, arr);
                si += 1;
            }
        }
    }

    private void swap(int i, int j , int[] arr) {
        int tmp  = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private String arrToString(int[] arr) {
        return Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(", "));
    }
}
