package com.walmart;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrappingWater {

    private class Cell implements Comparable<Cell> {
        int row;
        int col;
        int height;
        public Cell(int r, int c, int h) {
            this.row = r;
            this.col = c;
            this.height = h;
        }
        @Override
        public int compareTo(@NotNull Cell other) {
            return this.height - other.height;
        }
    }
    public static void main(String[] args) {
        TrappingWater tw = new TrappingWater();

        //tw.testByStack();
        tw.solveBoard();

    }

    private void solveBoard() {
        char[][] board = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        solve(board);
    }

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m ; i++) {
            for(int j = 0; j < n; j++) {
                if(board[i][j] == 'O') {
                    dfs(i, j, board);
                }
            }
        }
        for(int i = 0; i < m ; i++) {
            for(int j = 0; j < n; j++) {
                if(board[i][j] == 'v') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private boolean dfs(int i, int j, char[][] board) {
        if(i == 0 || i == board.length-1 || j == 0|| j == board[0].length) {
            board[i][j] = 'v';
            return true;
        }
        board[i][j] = 'p';
        int[][] dirs = {{-1,0}, {0,-1},{0,1},{1,0}};
        boolean touchesBoundry =false;
        for(int[] dir: dirs) {
            int row = i + dir[0];
            int col = j + dir[1];
            if(row < 0 || row >= board.length
                    || col < 0 || col >= board[0].length
                    || board[row][col] != 'O') {
                continue;
            }  else if(board[row][col] == 'O'){
                //board[i][j] = 'p';
                touchesBoundry = dfs(row, col, board);
            }

        }
        board[i][j]  = touchesBoundry ? 'v' : 'X';
        return touchesBoundry;
    }


    private void testByStack() {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int water = trap(height);
        System.out.printf("Water: %d\n", water);
    }

    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        for(int i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int left = stack.pop();
                if(stack.isEmpty()) {
                    break;
                }
                int distance = i - stack.peek() - 1;
                int bounded = Math.min(height[i], height[stack.peek()]) - height[left];
                result += (distance * bounded);
            }
            stack.push(i);
        }
        return result;
    }

    public int trap2Pointer(int[] height) {
        int result = 0;
        int left = 0, right = height.length-1;
        int leftMax = 0, rightMax = 0;
        while(left < right) {
            if(height[left] < height[right]) {
                if(height[left] > leftMax) {
                    leftMax = height[left];
                } else {
                    result += (leftMax - height[left]);
                }
                left += 1;
            } else {
                if(height[right] > rightMax) {
                    rightMax = height[right];
                } else {
                    result += (rightMax - height[right]);
                }
                right -= 1;
            }
        }
        return result;
    }
    public int trapDP(int[] height) {
        int n = height.length;
        int[] left = new int[n+1];
        int[] right = new int[n+1];
        int fromLeft = 0;
        int fromRight = 0;
        for(int i = 0; i < n ; i++) {
            fromLeft = Math.max(height[i], fromLeft);
            left[i] = fromLeft;
            fromRight = Math.max(height[n-i-1], fromRight);
            right[n-i-1] = fromRight;
        }
        int total = 0;
        for(int i = 0; i < n;i++) {

            //System.out.printf("Index : %d, left: %d, right ; %d\n", i, left[i], right[i]);
            int h = Math.min(left[i], right[i]) - height[i];
            total += h;
        }
        return total;
    }
    String S;
    public int search(int L, int a, long modulus, int n, int[] nums) {
        // Compute the hash of string S[:L]
        long h = 0;
        for (int i = 0; i < L; ++i) {
            h = (h * a + nums[i]) % modulus;
        }

        // Store the already seen hash values for substrings of length L.
        HashMap<Long, List<Integer>> seen = new HashMap<Long, List<Integer>>();

        // Initialize the hashmap with the substring starting at index 0.
        seen.putIfAbsent(h, new ArrayList<Integer>());
        seen.get(h).add(0);

        // Const value to be used often : a**L % modulus
        long aL = 1;
        for (int i = 1; i <= L; ++i) {
            aL = (aL * a) % modulus;
        }

        for (int start = 1; start < n - L + 1; ++start) {
            // Compute rolling hash in O(1) time
            h = (h * a - nums[start - 1] * aL % modulus + modulus) % modulus;
            h = (h + nums[start + L - 1]) % modulus;
            List<Integer> hits = seen.get(h);
            if (hits != null) {
                // Check if the current substring matches any of
                // the previous substrings with hash h.
                String cur = S.substring(start, start + L);
                for (Integer i : hits) {
                    String candidate = S.substring(i, i + L);
                    if (candidate.equals(cur)) {
                        return i;
                    }
                }
            }
            // Add the current substring's hashvalue and starting index to seen.
            seen.putIfAbsent(h, new ArrayList<Integer>());
            seen.get(h).add(start);
        }
        return -1;
    }

    public String longestDupSubstring(String s) {
        S = s;
        int n = S.length();

        // Convert string to array of integers to implement constant time slice
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = (int)S.charAt(i) - (int)'a';
        }

        // Base value for the rolling hash function
        int a = 26;

        // modulus value for the rolling hash function to avoid overflow
        int modulus = 1_000_000_007;

        // Binary search, L = repeating string length
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (search(mid, a, modulus, n, nums) != -1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        int start = search(left - 1, a, modulus, n, nums);
        return S.substring(start, start + left - 1);
    }


    private int trappingWater3d(int[][] heightMap) {
        int result = 0;
        if(heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return result;
        }
        int m = heightMap.length;
        int n = heightMap[0].length;
        PriorityQueue<Cell> queue = new PriorityQueue<>();
        boolean[][] visited = new boolean[heightMap.length][heightMap[0].length];
        for(int i = 0; i < m; i++) {
            queue.offer(new Cell(i, 0, heightMap[i][0]));
            visited[i][0] = true;
            queue.offer(new Cell(i, n-1, heightMap[i][n-1]));
            visited[i][n-1] = true;
        }
        for(int j = 1; j < n-1; j++) {
            queue.offer(new Cell(0, j, heightMap[0][j]));
            visited[0][j] = true;
            queue.offer(new Cell(m-1, j, heightMap[m-1][j]));
            visited[m-1][j] = true;
        }
        int[][] DIRS = {{-1, 0} , {0, 1}, {1, 0}, {0, -1}};
        while(!queue.isEmpty()) {
            Cell cell = queue.poll();
            for(int[] dir : DIRS) {
                int nRow = cell.row + dir[0];
                int nCol = cell.col + dir[1];
                if(nRow < 0 || nRow >= m || nCol < 0 || nCol >= n || visited[nRow][nCol]) {
                    continue;
                }
                result += Math.max(0, cell.height - heightMap[nRow][nCol]);
                queue.offer(new Cell(nRow, nCol, Math.max(cell.height, heightMap[nRow][nCol])));
                visited[nRow][nCol] = true;
            }
        }
        return result;
    }
    public boolean canReorderDoubled(int[] arr) {
        Integer[] array = IntStream.of(arr).mapToObj(Integer::valueOf).toArray(Integer[]::new);
        Arrays.sort(array, Comparator.comparingInt(Math::abs));
        return true;
    }
}
