package com.test;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestProg {
    public static void main(String[] args) {
        TestProg tp = new TestProg();
        //tp.testFindcity();
        //tp.testMaxSubArr();
        //tp.testPrefixSum();
        //tp.testMaxValPath();
        //tp.testMaxRect();
        //tp.testCountSmaller();
        //tp.testKtSmallest();
        //tp.testCompareStrings();
        //tp.testPerimeter();
        //tp.testPermutations();
        //System.out.printf("%d \n", (-1+4)%4);
        //tp.testSpiralMetrix();
        //tp.testSpecialCharacter();
        //tp.testShortestPath();
        tp.testStringConcatenation();
    }

    private void testStringConcatenation() {
        String s = "redpinbinpinpinred";
        String[] words = {"red", "pin"};
        List<Integer> res = findSubstring(s, words);
        res.forEach(System.out::println);
    }

    public List<Integer> findSubstring(String s, String[] words) {
        int len = words.length, wordLen = words[0].length();
        List<Integer> res = new ArrayList<>();
        Map<String, Integer> targetArr = new HashMap<>();
        for(String word: words) {
            targetArr.merge(word, 1, Integer::sum);
        }
        for(int i = 0; i < wordLen; i++) {
            List<Integer> currIndices = slidingWindow(i, s, targetArr, len, wordLen);
            res.addAll(currIndices);
        }
        return res;
    }

    private List<Integer> slidingWindow(int idx, String s, Map<String, Integer> targetMap, int len, int wordLen) {
        Map<String, Integer> srcMap = new HashMap<>();
        int wordFound = 0;
        boolean excessWord = false;
        List<Integer> res = new ArrayList<>();
        for(int l = idx, r = idx; r + wordLen <= s.length(); r += wordLen) {
            String currWord = s.substring(r, r+wordLen);
            if(!targetMap.containsKey(currWord)) {
                //Reset every thing
                srcMap.clear();
                wordFound = 0;
                excessWord = false;
                l = r + wordLen;
            } else {
                //Try Shrinking
                while(r-l == len*wordLen || excessWord) {
                    String leftMostWord = s.substring(l, l+wordLen);
                    srcMap.merge(leftMostWord, -1, Integer::sum);
                    l += wordLen;
                    //Verify if crrent removed word is excessWord
                    if(srcMap.get(leftMostWord) <  targetMap.get(leftMostWord)) {
                        //we lost a valid word
                        wordFound -= 1;
                    } else {
                        //Lose and excessword
                        excessWord = false;
                    }
                }
                srcMap.merge(currWord, 1, Integer::sum);
                if(srcMap.get(currWord) > targetMap.get(currWord)) {
                    excessWord = true;
                } else {
                    wordFound += 1;
                }
                if(wordFound == len && !excessWord) {
                    res.add(l);
                }
            }
        }
        return res;
    }

    private void testShortestPath() {
        int[][] edges = {{0,1,4},{0,2,1},{1,3,2},{1,4,3},{1,5,1},{2,3,1},{3,5,3},{4,5,2}};
        boolean[] res = findAnswer(6, edges);
    }

    public boolean[] findAnswer(int n, int[][] edges) {
        Set[] GRAPH = new Set[n];
        for(int i = 0; i < n; i++) {
            GRAPH[i] = new HashSet<int[]>();
        }
        for(int[] edge: edges) {
            GRAPH[edge[0]].add(new int[]{edge[1], edge[2]});
            GRAPH[edge[1]].add(new int[]{edge[0], edge[2]});
        }
        int[] SFS = minDistanceFromSource(0, n, GRAPH);
        int[] SFD = minDistanceFromSource(n-1, n, GRAPH);
        for(int i = 0; i < n; i++) {
            System.out.printf("SFS[%d] = %d\n", i, SFS[i]);
            System.out.printf("SFD[%d] = %d\n", i, SFD[i]);
        }
        boolean[] res = new boolean[edges.length];
        for(int i = 0; i < res.length; i++) {
            res[i] = SFS[edges[i][0]] + SFD[edges[i][1]] + edges[i][2] == SFS[n-1];
        }
        return res;

    }
    private int[] minDistanceFromSource(int src, int n, Set[] GRAPH) {
        int[] SFS = new int[n];
        Arrays.fill(SFS, Integer.MAX_VALUE);
        SFS[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.<int[]>comparingInt(e -> e[1]));
        pq.offer(new int[]{src, 0});
        while(!pq.isEmpty()) {
            int[] curr = pq.poll();
            if(curr[1] > SFS[curr[0]]) {
                continue;
            }
            Set<int[]> neighbors = GRAPH[curr[0]];
            for(int[] neighbor: neighbors) {
                if(SFS[neighbor[0]] > curr[1] + neighbor[1]) {
                    SFS[neighbor[0]] = curr[1] + neighbor[1];
                    pq.offer(new int[]{neighbor[0], SFS[neighbor[0]]});
                }
            }
        }
        return SFS;
    }

    private void testSpecialCharacter() {
        String word = "AbBCab";
        int count  = numberOfSpecialChars(word);
        System.out.printf("Count: %d\n", count);
    }

    public int numberOfSpecialChars(String word) {
        int[] counter = new int['z' - 'A' + 1];
        boolean[] visited = new boolean[26];
        int count = 0;
        for(char ch: word.toCharArray()) {
            if(Character.isLowerCase(ch)) {
                char toUpper = Character.toUpperCase(ch);
                if(counter[toUpper - 'A'] > 0 && !visited[ch-'a']) {
                    count += 1;
                    visited[ch-'a'] = true;
                }
            } else {
                char toLower = Character.toLowerCase(ch);
                if(counter[toLower-'a'] > 0 && !visited[toLower-'a']) {
                    count += 1;
                    visited[toLower-'a'] = true;
                }
            }
            counter[ch - 'A'] += 1;
        }
        return count;
    }

    private void testSpiralMetrix() {
        int[][] matrix = {{1, 2, 3, 4}, {12,13,14,5}, {11,16,15,6}, {10, 9, 8, 7}};
        int[] res = spiralMatrix(matrix);
        String str = Arrays.stream(res).boxed().map(String::valueOf).collect(Collectors.joining(", "));
        System.out.printf("Res: [%s]\n", str);
    }

    /**
     * left, right, upper lower
     * Each iteration, left -> right, (incr upper), up -> down (decr right), right -> left(dec low), low -> up(incr left)
     * @param matrix
     * @return
     */

    private int[] spiralMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = n-1, upper = 0, lower = m-1;
        int count = m*n;
        int idx = 0;
        int[] result = new int[count];
        while(idx < count) {
            for(int i = left; i <= right && idx < count; i++) {
                result[idx++] = matrix[upper][i];
            }
            upper += 1;
            for(int i = upper; i <= lower && idx < count; i++) {
                result[idx++] = matrix[i][right];
            }
            right -= 1;
            for(int i = right; i >= left && idx < count; i--) {
                result[idx++] = matrix[lower][i];
            }
            lower -= 1;
            for(int i = lower; i >= upper && idx < count; i--) {
                result[idx++] = matrix[i][left];
            }
            left += 1;
        }
        return result;
    }

    private void testPermutations() {
        int[][] queries = {{10,0},{10,1},{7,0},{10,9},{9,0},{10,3},{8,2},{8,0},{7,0},{10,1}};
        for(int[] query: queries) {
            List<Integer> res = absolutePermutation(query[0], query[1]);
            String str = res.stream().map(String::valueOf).collect(Collectors.joining(","));
            System.out.printf("(%d, %d) = [%s]\n", query[0], query[1], str);
        }
    }

    public  List<Integer> absolutePermutation(int n, int k) {
        if(k == 0) {
            return IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList());
        } else if(k == 1) {
            if(n%2 == 1) {
                return Arrays.asList(-1);
            }
            Integer[] res = new Integer[n];
            for(int i = 1; i <= n; i+=2) {
                res[i-1] = i+1;
                res[i] = i;
            }
            return Arrays.asList(res);
        } else if((k << 1) > n) {
            return Arrays.asList(-1);
        }
        Integer[] res = new Integer[n];
        boolean[] used = new boolean[n+1];
        for(int i = 1; i <= k; i++) {
            res[i-1] = i + k;
            used[i+k] = true;
        }
        for(int i = n; n - i < k; i--) {
            res[i-1] = i - k;
            if(used[i-k]) {
                return Arrays.asList(-1);
            }
            used[i-k] = true;
        }
        for(int i = k+1; i <= n-k; i++) {
            if(i-k > 0 && !used[i-k]) {
                res[i-1] = i - k;
                used[i-k] = true;
            } else if(i+k <= n && !used[i+k]){
                res[i-1] = i + k;
                used[i + k] = true;
            } else {
                return Arrays.asList(-1);
            }
        }
        return Arrays.asList(res);

    }
    private void testPerimeter() {
        int[][] GRID  = {{0,1,0,0},{1,1,1,0},{0,1,0,0},{1,1,0,0}};
        int perim = islandPerimeter(GRID);
        System.out.printf("Perimeter= %d\n",  perim);
    }

    private int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0) {
                    continue;
                }
                perimeter += 4;
                if(i > 0 && grid[i-1][j] == 1) {
                    perimeter -= 2;
                }
                if(j > 0 && grid[i][j-1] == 1) {
                    perimeter -= 2;
                }
            }
        }
        return perimeter;
    }


    private void testCompareStrings() {
        String left = "ab", right = "abc";
        System.out.printf("%s compare to %s = %b\n", left, right, compareString(left, right));
    }

    private boolean compareString(String left, String right) {
        int l = left.length(), r = right.length();
        if(l > r && left.startsWith(right)) {
            return left.substring(r).compareTo(right) <= 0;
        } else if(r > l && right.startsWith(left)) {
            return right.substring(l).compareTo(left) >= 0;
        }
        return left.compareTo(right) <= 0;

    }

    private void testKtSmallest() {
        int[] coins = {3,6,9};
        int  k = 3;
        long val = findKthSmallest(coins, k);
        System.out.printf("val: %d\n", val);
    }

    public long findKthSmallest(int[] coins, int k) {
        BitSet bitset = new BitSet(k+1);
        for(int coin: coins) {
            int val = coin;
            for(int i = 0; i < k; i++) {
                System.out.printf("For coin : %d, set: %d\n", coin, val);
                bitset.set(val);
                val += coin;
            }
        }
        long val = bitset.nextSetBit(0);
        while(k-- > 0) {
            val = bitset.nextSetBit((int)val+1);
            System.out.printf("%d set bit: %d\n", k, val);
        }
        return val;
    }

    private void testCountSmaller() {
        int[] nums = {5, 2, 6, 1};
        List<Integer> res = countSmaller(nums);
        res.forEach(System.out::println);
    }

    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int[][] arr = new int[n][2], aux = new int[n][2];
        for(int i = 0; i < n; i++) {
            arr[i] = new int[]{i, nums[i]};
        }
        mergeSort(arr, 0, n-1, res, aux);
        return Arrays.stream(res).boxed().collect(Collectors.toList());
    }

    private void mergeSort(int[][] arr, int l, int r, int[] res, int[][] aux) {
        if(l >= r) {
            return;
        }
        int mid = l + (r-l)/2;
        mergeSort(arr, l, mid, res, aux);
        mergeSort(arr, mid+1, r, res, aux);
        for(int i = l; i <= r; i++) {
            aux[i] = arr[i];
        }
        int i = l, j = mid+1, idx = l;
        while(i <= mid && j <= r) {
            if(aux[i][1] <= aux[j][1]) {
                res[aux[i][0]] += (j - (mid + 1));
                arr[idx++] = aux[i++];
            } else {
                arr[idx++] = aux[j++];
            }
        }
        while(j <= r) {
            arr[idx++] = aux[j++];
        }
        while(i <= mid) {
            res[aux[i][0]] += (j - (mid + 1));
            arr[idx++] = aux[i++];
        }
    }
    private void testMaxRect() {
        char[][] matrix = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
    }

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[] DP = new int[n];
        int maxArea = 0, curr = 0;
        for(int i = 0; i < n; i++) {
            DP[i] = matrix[0][i];
            if(DP[i] == 1) {
                curr = i > 0 && DP[i-1] == 1 ? curr + 1 : 1;
            }
            maxArea = Math.max(maxArea, curr);
        }
        for(int i = 1; i < m; i++) {
            for(int j = 0; j < n; j++) {
                DP[j] += matrix[i][j];
            }
            curr = maxAreaInHistogram(DP);
            maxArea = Math.max(maxArea, curr);
        }
        return maxArea;
    }
    private int maxAreaInHistogram(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int n = heights.length, maxArea = 0;
        for(int i = 0; i < n; i++) {
            while(!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int tos = stack.pop();
                int l = stack.isEmpty() ? -1 : stack.peek();
                int width = i - l -1;
                maxArea = Math.max(maxArea, width*heights[tos]);
            }
            stack.push(i);
        }
        while(!stack.isEmpty()) {
            int tos = stack.pop();
            int l = stack.isEmpty() ? -1 : stack.peek();
            int width = n - l - 1;
            maxArea = Math.max(maxArea, width*heights[tos]);
        }
        return maxArea;
    }
    private void testMaxValPath() {
        int[] values = {61,11,18,43,81};
        int[][] edges = {{0,3,45}, {0,2,16}, {0,1,36}, {3,4,38}, {2,3,29}};
        int maxTime = 88;
        int val = maximalPathQuality(values, edges, maxTime);
        System.out.printf("val: %d\n", val);
    }

    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        int n = values.length;
        Set[] GRAPH = new Set[n];
        for(int i = 0; i < n; i++) {
            //GRAPH[i] = new TreeSet<int[]>(Comparator.<int[]>comparingInt(e -> values[e[0]]).reversed());
            GRAPH[i] = new HashSet<int[]>();
        }
        for(int[] edge: edges) {
            GRAPH[edge[0]].add(new int[]{edge[1], edge[2]});
            GRAPH[edge[1]].add(new int[]{edge[0], edge[2]});
        }
        int[] maxVal = {0};
        //boolean[] visited = new boolean[n];
        int[] seen = new int[n];
        seen[0] += 1;
        dfs(0, values[0], values, maxVal, seen, GRAPH, maxTime);
        return maxVal[0];
    }
    private void dfs(int node, int currVal, int[] values, int[] maxVal, int[] seen, Set[] GRAPH, int remTime) {
        if(remTime <= 0) {
            return;
        }
        if(node == 0) {
            maxVal[0] = Math.max(maxVal[0], currVal);
        }
        Set<int[]> neighbors  = GRAPH[node];
        for(int[] neighbor: neighbors)  {
            seen[neighbor[0]] += 1;
            int val = seen[neighbor[0]] == 1 ? currVal + values[neighbor[0]] : currVal;
            dfs(neighbor[0], val, values, maxVal, seen, GRAPH, remTime-neighbor[1]);
            seen[neighbor[0]] -= 1;
        }
    }

    private void testPrefixSum() {
        int[] arr = {13, 4, 2, 9, 7, 10, 23, 7, 0, 15};
        int[][] queries = {{2,5}, {0,4}, {6,8}, {1,7}};
        prefixSum(arr, queries);
    }

    private void testMaxSubArr() {
        int[] arr = {-1,-2,-3,-4};
        int k = 2;
        long res = maximumSubarraySum(arr, k);
        System.out.printf("res: %d\n", res);
    }


//1: 0, 2:1, 3: 3, 4:6, 5: 10, 6: 15
    public long maximumSubarraySum(int[] nums, int k) {
        //For each number, store min ps recorded till now for that number
        long max = Long.MIN_VALUE, ps = 0l;
        Map<Integer, Long> numToPs = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            if(numToPs.getOrDefault(nums[i], Long.MAX_VALUE) > ps) {
                numToPs.put(nums[i], ps);
            }
            ps += nums[i];
            if(numToPs.containsKey(nums[i] + k)) {
                max = Math.max(max, ps-  numToPs.get(nums[i] + k));
            }
            if(numToPs.containsKey(nums[i] - k)) {
                max = Math.max(max, ps - numToPs.get(nums[i] - k));
            }
        }
        return max == Long.MIN_VALUE ? 0 : max;
    }

    public void prefixSum(int[] arr, int[][] queries) {
        int[] ps = new int[arr.length];
        ps[0] = arr[0];
        for(int i = 1; i < arr.length; i++) {
            ps[i] = ps[i-1] + arr[i];
        }
        for(int[] query: queries) {
            int l = query[0], r = query[1];
            int sum = ps[r] - (l == 0 ? 0: ps[l-1]);
            System.out.printf("Sum between %d and %d = %d\n", l, r, sum);
        }
    }
    private void testFindcity() {
        int[][] edges = {{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}};
        int city = findTheCity(5, edges, 2);
        System.out.printf("City: %d\n", city);
    }

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] GRAPH = new int[n][n];
        for(int[] row: GRAPH) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        for(int i = 0; i < n; i++) {
            GRAPH[i][i] = 0;
        }
        for(int[] edge: edges) {
            GRAPH[edge[0]][edge[1]] = edge[2];
            GRAPH[edge[1]][edge[0]] = edge[2];
        }
        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(GRAPH[i][k] == Integer.MAX_VALUE
                            || GRAPH[k][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if(GRAPH[i][k] + GRAPH[k][j] < GRAPH[i][j]) {
                        GRAPH[i][j] = GRAPH[i][k] + GRAPH[k][j];
                        GRAPH[j][i] = GRAPH[i][k] + GRAPH[k][j];
                    }
                }
            }
        }
        int[] counter = new int[n];
        int count = n, res = -1;
        for(int i = 0; i < n; i++) {
            int currCount = 0;
            for(int j = 0; j < n; j++) {
                if(GRAPH[i][j] <= distanceThreshold) {
                    currCount += 1;
                    counter[i] += 1;
                }
            }
            if(currCount <= count) {
                count = currCount;
                res = i;
            }
        }
        return res;
    }
}
