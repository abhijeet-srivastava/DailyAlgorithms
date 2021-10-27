package main.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class NumberOfIslands {
    private final int[][] directions = {{-1,0}, {0,-1}, {0,1}, {1, 0}};

    public static void main(String[] args) {
        NumberOfIslands ni = new NumberOfIslands();
        ni.testUnionFind();
    }

    private void testUnionFind() {
        char[][] grid = {
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
            };
        int count = numIslands(grid);
    }

    public int numIslandsDfs(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        int columns = grid[0].length;
        Set<Integer> visited = new HashSet<>();
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                if(grid[row][col] == '1'
                        && !visited.contains(row*columns + col)) {
                    count += 1;
                    dfs(grid, row, col, visited);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int row, int col, Set<Integer> visited) {
        int rows = grid.length;
        int columns = grid[0].length;
        visited.add(row*columns +col);
        for(int[] direction : directions) {
            int nRow = row + direction[0];
            int nCol = col + direction[1];
            if(nRow < 0 || nRow >= rows
                    || nCol < 0 || nCol >= columns
                    || grid[nRow][nCol] == '0'
                    || visited.contains(nRow*columns + nCol)) {
                continue;
            }
            dfs(grid, nRow, nCol, visited);
        }
    }

    private int numOfIslandsBfs(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == '0') {
                    continue;
                }
                count += 1;
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i*cols + j);
                while (!queue.isEmpty()) {
                    int currentCell = queue.poll();
                    int currRow = currentCell/cols;
                    int currCol = currentCell%cols;
                    for(int[] child : directions) {
                        int childRow = currRow + child[0];
                        int childCol = currCol + child[1];
                        if(childRow >= rows || childCol >= cols || grid[childRow][childCol] == '0') {
                            continue;
                        }
                        queue.offer(childRow*cols + childCol);
                        grid[childRow][childCol] = '0';
                    }
                }
            }
        }
        return count;
    }

    class UnionFind {
        int count;
        int[] parent;
        int[] rank;

        public UnionFind(char[][] grid) {
            this.count = 0;
            int rows = grid.length;
            int cols = grid[0].length;
            this.parent = new int[rows*cols];
            this.rank = new int[rows*cols];
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    if(grid[i][j] == '1') {
                        this.parent[i*cols +j] = (i*cols + j);
                        this.count += 1;
                    }
                    this.rank[i*cols +j] = 0;
                }
            }
        }
        public int find(int i) {
            if(this.parent[i] != i) {
                this.parent[i] = find(parent[i]);
            }
            return this.parent[i];
        }
        public void union(int x, int y) {
            int rootx = find(x);
            int rooty = find(y);
            if (rootx != rooty) {
                if(rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx;
                } else if(rank[rootx] < rank[rooty]) {
                    parent[rooty] = rootx;
                } else {
                    parent[rooty] = rootx;
                    rank[rootx] += 1;
                }
                this.count -= 1;
            }
        }

        public int getCount() {
            return count;
        }
    }
    private int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(grid[row][col] == '0') {
                    continue;
                }
                grid[row][col] = '0';
                for(int[] direction : directions) {
                    int nRow = row + direction[0];
                    int nCol = col + direction[1];
                    if(nRow < 0 || nRow >= rows || nCol < 0 || nCol >= cols || grid[nRow][nCol] == '0') {
                        continue;
                    }
                    uf.union(row*cols + col, nRow*cols + nCol);
                }

            }
        }
        return uf.getCount();
    }

}
