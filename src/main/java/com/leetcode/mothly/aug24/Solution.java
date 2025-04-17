package com.leetcode.mothly.aug24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        //sol.solveMinSwaps();
        //sol.solveRegionsBySlashes();
        //sol.solveFractions();
        //sol.testSubIslands();
        //sol.testLongestSubString();
        sol.testSumPrefix();
    }
    private List[] findIntersections(List<int[]> intervals) {
        List[] intersections = {new ArrayList<int[]>(), new ArrayList<int[]>()};
        Collections.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        return intersections;
    }

    private void testSumPrefix() {
        String[] words= {"abc", "ab", "bc", "b"};
        int[] weight = sumPrefixScores(words);
        System.out.printf("Weights: [%s]\n", Arrays.stream(weight).boxed().collect(Collectors.toList()));
    }

    public int[] sumPrefixScores(String[] words) {
        Trie trie = new Trie();
        for(String word: words) {
            trie.add(word);
        }
        int[] res = new int[words.length];
        for(int idx = 0; idx < words.length;idx++) {
            res[idx] = trie.weight(words[idx]);
        }
        return res;
    }
    private class Trie {
        private TrieNode root;
        public Trie() {
            this.root = new TrieNode('/');
        }
        public void add(String str) {
            TrieNode curr = this.root;
            //System.out.printf("Adding: %s\n", str);
            for(char ch: str.toCharArray()) {
                int idx = ch - 'a';
                if(curr.children[idx] == null) {
                    curr.children[idx] = new TrieNode(ch);
                }
                curr.children[idx].count += 1;
                curr = curr.children[idx];
                //System.out.printf("Char: %c, weight: %d\n", ch, curr.children[idx].count);
            }
        }
        public int weight(String str) {
            System.out.printf("Word: %s\n", str);
            TrieNode curr = this.root;
            int weight = 0;
            for(char ch: str.toCharArray()) {
                int idx = ch - 'a';
                System.out.printf("Char: %c, weight: %d\n", ch, curr.children[idx].count);
                weight += curr.children[idx].count;
                curr = curr.children[idx];
            }
            return weight;
        }
    }
    private class TrieNode {
        char ch;
        int count;
        TrieNode[] children;
        public TrieNode(char ch) {
            this.ch = ch;
            this.count = 0;
            this.children = new TrieNode[26];
        }
    }

    Map<Character, BiFunction<Integer, Integer, Integer>> functionMap = Map.of(
            '+', Integer::sum,
            '-', (a, b) -> a-b,
            '*', (a,b) -> a*b
    );
    public List<Integer> diffWaysToCompute(String expression) {
        int len = expression.length();
        return diffWaysToCompute(0, len-1, expression);
    }

    private List<Integer> diffWaysToCompute(int l, int r, String expression) {
        int len = r-l+1;
        if(len == 1 || len ==2) {
            return Arrays.asList(Integer.valueOf(expression.substring(l, r+1)));
        }
        List<Integer> res = new ArrayList<>();
        for(int idx = l+1; idx < r; idx++) {
            char ch = expression.charAt(idx);
            if(!functionMap.containsKey(ch)) {
                continue;
            }
            List<Integer> left = diffWaysToCompute(l, idx-1, expression);
            List<Integer> right = diffWaysToCompute(idx+1, r, expression);
            for(int lVal: left) {
                for(int rVal: right) {
                    res.add(functionMap.get(ch).apply(lVal, rVal));
                }
            }
        }
        return res;
    }

    private void testLongestSubString() {
        String str = "eleetminicoworoep";
        int len = findTheLongestSubstring(str);
        System.out.printf("%s - %d\n", str, len);
    }

    public int findTheLongestSubstring(String s) {
        int maxLen = 0;
        int currMask = 0;
        Map<Integer, Integer> maskIndices = new HashMap<>();
        maskIndices.put(0, -1);
        for(int idx = 0; idx < s.length();idx++) {
            char curr = s.charAt(idx);
            int indexMask = findMaskIndex(curr);
            currMask ^= indexMask;
            System.out.printf("%c has mask %d and currMask: %d\n", curr, indexMask, currMask);
            maskIndices.putIfAbsent(currMask, idx);
            maxLen = Math.max(maxLen, idx-maskIndices.get(currMask));
        }
        return maxLen;
    }
    private int findMaskIndex(char ch) {
        if(!isVowel(ch)) {
            return 0;
        }
        return  1 << ("aeiou".indexOf(ch));
        //return 1 << ("aeiou".indexOf(ch) + 1) >> 1;
    }
    private boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    private void testSubIslands() {
        int[][] grid1 = {{1,1,1,1,0,0},{1,1,0,1,0,0},{1,0,0,1,1,1},{1,1,1,0,0,1},{1,1,1,1,1,0},{1,0,1,0,1,0},{0,1,1,1,0,1},{1,0,0,0,1,1},{1,0,0,0,1,0},{1,1,1,1,1,0}};
        int[][] grid2 = {{1,1,1,1,0,1},{0,0,1,0,1,0},{1,1,1,1,1,1},{0,1,1,1,1,1},{1,1,1,0,1,0},{0,1,1,1,1,1},{1,1,0,1,1,1},{1,0,0,1,0,1},{1,1,1,1,1,1},{1,0,0,1,0,0}};
        int cnt = countSubIslands(grid1, grid2);
        System.out.printf("count: %d\n", cnt);
    }

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        int[][] grid = new int[m][n];
        for(int r = 0; r < m; r++) {
            for(int c = 0; c < n; c++) {
                if(grid1[r][c] == 1 && grid2[r][c] == 1) {
                    grid[r][c] = 3;
                } else if(grid1[r][c] == 1) {
                    grid[r][c] = 2;
                } else if(grid2[r][c] == 1) {
                    grid[r][c] = 1;
                }
            }
        }
        int count = 0;
        for(int r = 0; r < m; r++) {
            for(int c = 0; c < n; c++) {
                if(grid[r][c] < 3) {
                    continue;
                }
                if(dfs1(r, c, grid)) {
                    System.out.printf("Sub island starting at: (%d, %d)\n", r, c);
                    count += 1;
                }
            }
        }
        return count;
    }
    int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private boolean dfs1(int r, int c, int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if(r < 0 || r >= m || c < 0 || c >= n || grid[r][c] == 0) {
            return true;
        } else if(grid[r][c] == 2) {
            return true;
        } else if(grid[r][c] == 1) {
            return false;
        }
        //grid[r][c] = 3;
        for(int[] dir: dirs) {
            if(!dfs1(r+dir[0], c+dir[1], grid)) {
                return false;
            }
        }
        return true;
    }

    private void solveFractions() {
        String res = fractionAddition("7/3+5/2-3/10");
        System.out.printf("Res: %s\n", res);
    }

    public String fractionAddition(String expression) {
        int len = expression.length();
        if(len == 0) {
            return expression;
        }
        List<Fraction> fractions = new ArrayList<>();
        int idx = 0;
        boolean isPosetive = true;
        int gcd = 0;
        if(expression.charAt(0) == '-') {
            isPosetive = false;
            idx += 1;
        } else if(expression.charAt(0) == '+') {
            idx += 1;
        }
        for(; idx < len; ) {
            char curr = expression.charAt(idx++);
            int num = curr - '0';
            char next = expression.charAt(idx);
            if(next == '0') {
                num = 10;
                idx += 2;
            } else {
                idx += 1;
            }
            curr = expression.charAt(idx);
            if(curr == '-') {
                isPosetive = isPosetive ? false : true;
                curr = expression.charAt(++idx);
            }
            int den = curr - '0';
            if(idx < len-1 && expression.charAt(idx+1) == '0') {
                den = 10;
                idx += 1;
            }
            Fraction currFraction = new Fraction(num, den, isPosetive);
            if(fractions.size() == 0) {
                gcd = den;
            } else {
                gcd = gcd(gcd, den);
            }
            fractions.add(currFraction);
            System.out.printf("Fraction = %s\n", currFraction.toString());
            if(idx < len-1 && (expression.charAt(idx+1) == '+' || expression.charAt(idx+1) == '-')) {
                isPosetive = expression.charAt(idx+1) == '+';
                idx += 2;
            } else {
                idx += 1;
            }
        }
        //System.out.printf("gcd: %d\n", gcd);
        int sum = 0;
        for(Fraction frac: fractions) {
            int value = frac.num*(gcd/frac.den);
            if(frac.isPosetive) {
                sum += value;
            } else {
                sum -= value;
            }
        }
        //System.out.printf("sum: %d\n", sum);
        if(sum == 0) {
            return "0/1";
        }
        int lcf = lcf(Math.abs(sum), gcd);
        Fraction res = new Fraction(Math.abs(sum)/lcf, gcd/lcf, sum > 0);
        return res.toString();
    }
    //lcf(6, 4) => lcf (4, 6) => lcf(2, 4) => lcf(0, 2)
    private int gcd(int a, int b) {
        int lcf = lcf(a, b);
        return (a*b)/lcf;
    }
    private int lcf(int a, int b) {
        if(a == 0) {
            return b;
        }
        return lcf(b%a, a);
    }
    class Fraction {
        boolean isPosetive;
        int num;
        int den;
        public Fraction(int num, int den, boolean isPosetive) {
            this.num = Math.abs(num);
            this.den = Math.abs(den);
            this.isPosetive = isPosetive;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(!isPosetive) {
                sb.append('-');
            }
            sb.append(this.num);
            sb.append('/');
            sb.append(this.den);
            return sb.toString();
        }
    }
    public int regionsBySlashes1(String[] grid) {
        int n = grid.length;
        DSU dsu = new DSU(4*n*n);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i > 0) {
                    dsu.join(g(i-1, j, 2, n), g(i, j, 0, n));
                }
                if(j > 0) {
                    dsu.join(g(i, j-1, 1, n), g(i, j, 3, n));
                }
                char curr = grid[i].charAt(j);
                if(curr == '/') {
                    dsu.join(g(i, j, 0, n), g(i, j, 3, n));
                    dsu.join(g(i, j, 1, n), g(i, j, 2, n));
                } else if(curr == '\\') {
                    dsu.join(g(i, j, 0, n), g(i, j, 1, n));
                    dsu.join(g(i, j, 2, n), g(i, j, 3, n));
                } else {
                    dsu.join(g(i, j, 0, n), g(i, j, 1, n));
                    dsu.join(g(i, j, 1, n), g(i, j, 2, n));
                    dsu.join(g(i, j, 2, n), g(i, j, 3, n));
                    dsu.join(g(i, j, 3, n), g(i, j, 0, n));
                }
            }
        }
        return dsu.count;

    }
    private int g(int r, int c, int sec, int n) {
        return (r*n + c)*4 + sec;
    }
    private class DSU {
        int count;
        int[] parent;
        int[] rank;
        public DSU(int n) {
            this.count = n;
            this.parent = new int[n];
            this.rank = new int[n];
            for(int i = 0; i < n; i++) {
                this.parent[i] = i;
                this.rank[i] = 1;
            }
        }
        private int find(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return parent[x];
        }
        private void join(int x, int y) {
            int px = find(x), py = find(y);
            if(px == py) {
                return;
            }
            if(rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if(rank[px] == rank[py]) {
                    rank[px] += 1;
                }
            }
            this.count -= 1;
        }
    }

    private void solveRegionsBySlashes() {
        String[] grid = {" /","/ "};
        int count = regionsBySlashes1(grid);
        System.out.printf("Region Count: %d\n", count);
    }

    public int regionsBySlashes(String[] grid) {
        int m = grid.length;
        int[][] tmp = new int[3*m][3*m];
        for(int row = 0; row < m; row++) {
            for(int col = 0; col < m; col++) {
                char val = grid[row].charAt(col);
                int rIdx = row*3, colIdx = col*3;
                if(val == '/') {
                    tmp[rIdx][colIdx+2] = 1;
                    tmp[rIdx+1][colIdx+1] = 1;
                    tmp[rIdx+2][colIdx] = 1;
                } else if(val == '\\') {
                    tmp[rIdx][colIdx] = 1;
                    tmp[rIdx+1][colIdx+1] = 1;
                    tmp[rIdx+2][colIdx+2] = 1;
                }
            }
        }
        return countRegions(tmp);
    }
    private int countRegions(int[][] tmp) {
        int m = tmp.length;
        int count = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < 2; j++) {
                if(tmp[i][j] == 0) {
                    System.out.printf("Regin start at: (%d, %d)\n", i, j);
                    count += 1;
                    dfs(i, j, tmp);
                }
            }
        }
        return count;
    }
    //int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private void dfs(int i, int j, int[][] tmp) {
        int m = tmp.length;
        if(i < 0 || i >= m || j < 0 || j >= m || tmp[i][j] != 0) {
            return;
        }
        tmp[i][j] = 2;
        for(int[] dir: dirs) {
            dfs(i+dir[0], j + dir[1], tmp);
        }
    }
    private void solveMinSwaps() {
        int[] nums = {1,1,0,0,1};
        int count = minSwaps(nums);
        System.out.printf("Count: %d\n", count);
    }

    public int minSwaps(int[] nums) {
        int len = nums.length;
        int count = Arrays.stream(nums).sum();
        int[] tmp = new int[len << 1];
        System.arraycopy(nums, 0, tmp, 0, len);
        System.arraycopy(nums, 0, tmp, len, len);
        int minWindow = len;
        for(int l = 0, r = 0, currCount = 0; r < 2*len; r++) {
            currCount += tmp[r];
            while(currCount >= count) {
                currCount -= tmp[l];
                minWindow = Math.min(minWindow, r-l+1);
                l += 1;
            }
        }
        return minWindow - count;
    }

    public String kthDistinct(String[] arr, int k) {
        Map<String, int[]> counter = new HashMap<>();
        for(int idx = 0; idx < arr.length;idx++) {
            if(counter.containsKey(arr[idx])) {
                counter.get(arr[idx])[0] += 1;
            } else {
                counter.put(arr[idx], new int[]{1, idx});
            }
        }
       Optional<Map.Entry<String, int[]>> kthEntry = counter.entrySet()
                .stream().filter(e -> e.getValue()[0] > 1)
                .sorted((e1, e2) -> Integer.compare(e1.getValue()[1], e2.getValue()[1]))
                .skip(k-1)
                .findFirst();
        return kthEntry.isPresent() ? kthEntry.get().getKey() : "" ;
    }

    private String findString(Object e) {
        return "";
    }
}
