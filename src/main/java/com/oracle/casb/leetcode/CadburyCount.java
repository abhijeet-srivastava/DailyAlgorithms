package com.oracle.casb.leetcode;

import com.oracle.casb.common.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CadburyCount {


    public static void main(String[] args) {
        CadburyCount cc = new CadburyCount();
       // cc.testSount();
        //cc.testCourse();
        //cc.testStone();
        //cc.removeDuplicates("bbaacaaab");
        cc.lastStoneMinWeight(new int[] {31,26,33,21,40});
    }

    public boolean isInterLeaving(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        boolean[][] dp = new boolean[s1.length()+1][s2.length()+1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                int k = i + j -1;
                if(i == 0 && j == 0) {
                    dp[i][j] = true;
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] &&  (s2.charAt(j-1) == s3.charAt(k));
                } else if (j == 0) {
                    dp[i][j] = dp[i-1][j] && (s1.charAt(i-1) == s3.charAt(k));
                } else {
                    dp[i][j] = (dp[i-1][j] && (s1.charAt(i-1) == s3.charAt(k)))
                            || (dp[i][j - 1] &&  (s2.charAt(j-1) == s3.charAt(k)));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    private void testStone() {
        int[] stones = {2,7,4,1,8,1};
        int rem = lastStoneWeight(stones);
        System.out.println("Final stone "+ rem);
    }

    private void testCourse() {
        int[] order = findOrder(2, new int[][]{{1,0}});
        System.out.println(Arrays.stream(order).boxed().map(String::valueOf).collect(Collectors.joining(",", "[", "]")));
    }


    public int hasPath(int[][] maze, int[] start, int[] destination) {
        Queue<int[]> queue = new LinkedList<>();
        //boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[][] distance = new int[maze.length][maze[0].length];

        Arrays.stream(distance).forEach(
                arr -> Arrays.fill(arr, Integer.MAX_VALUE)
        );

        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1, 0}};
        queue.add(start);
        //visited[start[0]][start[1]] = true;
        distance[start[0]][start[1]] = 0;
        boolean res = false;
        while (!queue.isEmpty()) {
            int[] current = queue.remove();
            /*if (visited[current[0]][current[1]]) {
                continue;
            }
            if (current[0] == destination[0] && current[1] == destination[1]) {
                res =true;
                break;
            }*/
            for (int[] direction : directions) {
                int x = current[0] + direction[0];
                int y = current[1] + direction[1];
                int count = 0;
                while(x >= 0 && y >= 0
                && x < maze.length && y < maze[0].length
                && maze[x][y] == 0) {
                    x = current[0] + direction[0];
                    y = current[1] + direction[1];
                    count += 1;
                }
                x -= direction[0];
                y -= direction[1];
                /*if (!visited[x][y]) {
                    queue.add(new int[]{x, y});
                    visited[x][y] = true;
                }*/
                if((distance[current[0]][current[1]] + count) < distance[x][y]) {
                    distance[x][y] = distance[current[0]][current[1]] + count;
                    queue.add(new int[]{x, y});
                }
            }
        }
        return distance[destination[0]][destination[1]] == Integer.MAX_VALUE
                ? -1 : distance[destination[0]][destination[1]];
    }
    public int networkDelayTimeDijkastraHeap(int[][] times, int N, int K) {
        Map<Integer, List<Integer[]>> graph = new HashMap();
        Arrays.stream(times).forEach(
                edge -> graph.computeIfAbsent(Integer.valueOf(edge[0]),
                        k -> new ArrayList<>()).add(new Integer[]{edge[1], edge[2]})
        );
        PriorityQueue<int[]> heap = new PriorityQueue<>(
                (info1, info2) -> info1[1] - info2[1]);
        heap.offer(new int[]{K, 0});

        Map<Integer, Integer> dist = new HashMap();

        while (!heap.isEmpty()) {
            int[] info = heap.poll();
            int d = info[1], current = info[0];
            if (dist.containsKey(current)) continue;
            dist.put(current, d);
            if(!graph.containsKey(current)) {
                continue;
            }
            graph.get(current).stream().forEach(
                    neighbourInfo -> {
                        if (!dist.containsKey(neighbourInfo[0]))
                            heap.offer(new int[]{neighbourInfo[0], d+neighbourInfo[1]});
                    }
            );
        }

        return (dist.size() != N) ? -1
                : dist.values().<Integer>stream().max((a, b) -> a.compareTo(b)).get();
    }

    public int networkDelayTimeDijkastra(int[][] times, int N, int K) {
        Map<Integer, Integer> dist = getInitialDistMap(N);
        Map<Integer, List<Integer[]>> graph = getIntegerListMap(times);

        dist.put(K, 0);
        boolean[] visited = new boolean[N+1];
        while (true) {
            /**
             * Grab node with lowest Distance from source
             */
            int currentNode = dist.entrySet().stream()
                    .filter(entry -> !visited[entry.getKey()])
                    .min(Map.Entry.comparingByValue()).get().getKey();
            /**
             * If No remaining node is reachable(i.e. minDistanceNode, current is at Inifinite distance): Stop
             */
            if(dist.get(currentNode) == Integer.MAX_VALUE) {
                break;
            }
            visited[currentNode] = Boolean.TRUE;
            if (!graph.containsKey(currentNode)) {
                continue;
            }
            graph.get(currentNode).stream().forEach(
                   neighbourInfo
                           -> dist.put(neighbourInfo[0], Math.min(neighbourInfo[1], dist.get(currentNode) + neighbourInfo[1]))
            );
        }
        return getMaxDistance(dist);
    }

    private Map<Integer, List<Integer[]>> getIntegerListMap(int[][] times) {
        Map<Integer, List<Integer[]>> graph = new HashMap<>();
        for (int[] edge : times) {
            graph.computeIfAbsent(Integer.valueOf(edge[0]),
                    k -> new ArrayList<>()).add(new Integer[]{edge[1], edge[2]});
        }
        return graph;
    }

    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, Integer> dist = getInitialDistMap(N);
        Map<Integer, List<Integer[]>> graph = getIntegerListMap(times);
        for(Map.Entry<Integer, List<Integer[]>> entry : graph.entrySet()) {
            Collections.sort(entry.getValue(), (a, b) -> a[1] - b[1]);
        }
        dfs(graph, K, 0, dist);
        return getMaxDistance(dist);

    }

    private int getMaxDistance(Map<Integer, Integer> dist) {
        boolean nonConnected = dist.values()
                .stream()
                .anyMatch(Integer.valueOf(Integer.MAX_VALUE)::equals);
        return nonConnected ? -1 :
                dist.values().stream().max((a, b) -> a.compareTo(b)).get();
    }

    private void dfs(Map<Integer, List<Integer[]>> graph, int source, int elapsedTime, Map<Integer, Integer> dist) {
        /**
         * If time elapsed till now is already greater
         * then time taken by some previous signal to reach here, do not process this signal
         */
        if (elapsedTime >= dist.get(source)) {
            return;
        }
        // Update elapsed time to reach current source
        dist.put(source, elapsedTime);
        // If current source do not have any connected neighbours, we can't DFS further
        if (!graph.containsKey(source)) {
            return;
        }
        /**
         * From current source to connected edges, create signal; reach time of all neighbours
         */
        for(Integer[] neighbour : graph.get(source)) {
            dfs(graph, neighbour[0], elapsedTime+neighbour[1], dist);
        }
    }

    private Map<Integer, Integer> getInitialDistMap(int N) {
        return IntStream.rangeClosed(1, N)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), x -> Integer.MAX_VALUE));
    }

    public boolean is_Interleave(String s1, int i, String s2, int j, String s3, int k, int[][] memo) {
        if (i == s1.length()) {
            return s2.substring(j).equals(s3.substring(k));
        }
        if (j == s2.length()) {
            return s1.substring(i).equals(s3.substring(k));
        }
        if (memo[i][j] >= 0) {
            return memo[i][j] == 1 ? true : false;
        }
        boolean ans = false;
        if (s3.charAt(k) == s1.charAt(i) && is_Interleave(s1, i + 1, s2, j, s3, k + 1, memo)
                || s3.charAt(k) == s2.charAt(j) && is_Interleave(s1, i, s2, j + 1, s3, k + 1, memo)) {
            ans = true;
        }
        memo[i][j] = ans ? 1 : 0;
        return ans;
    }
    public boolean isInterleave(String s1, String s2, String s3) {
        int memo[][] = new int[s1.length()][s2.length()];
        Arrays.stream(memo).forEach(
                array -> Arrays.fill(array, -1)
        );
        /*for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                memo[i][j] = -1;
            }
        }*/
        return is_Interleave(s1, 0, s2, 0, s3, 0, memo);
    }

    private Map<Character, Integer> createCharMap(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.merge(Character.valueOf(ch), 1, (oldValue, newValue) -> oldValue + newValue);
        }
        return map;
    }


    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
        Set<Integer> set2 = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        set1.retainAll(set2);
        int i = 0;
        int[] res = new int[set1.size()];
        for (int num : set1) {
            res[i++] = num;
        }

        return res;
    }

    public int longestStrChain(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Arrays.stream(words).forEach(
                word -> map.put(word, 1)
        );
        int max = 0;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                String prev = word.substring(0, i) + word.substring(i+1);
                if (map.containsKey(prev)) {
                    map.put(word, Math.max(map.get(word), map.get(prev) + 1));
                }
            }
            max = Math.max(max, map.get(word));
        }
        return max;
    }
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> weights = new PriorityQueue<>((a,b) -> b-a);
        Arrays.stream(stones).forEach(
                stone -> weights.add(stone)
        );
        int res = 0;
        while (!weights.isEmpty()) {
            int weight1 = weights.poll();
            if(weights.isEmpty()) {
                res = weight1;
                break;
            }
            int weight2 = weights.poll();
            if(weight1 > weight2) {
                weights.add(weight1 - weight2);
            }
        }
        return res;
    }

    public String removeDuplicates(String S) {
        char[] array = S.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < array.length; i++) {
            if (!stack.isEmpty() && stack.peek() == array[i]) {
                stack.pop();
            } else {
                stack.push(array[i]);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    public String removeDuplicatesAll(String S) {
        char[] array = S.toCharArray();
        Stack<Character> stack = new Stack<>();
        Character prev = Character.valueOf(array[0]);
        for (int i = 0; i < array.length; i++) {
            boolean isDupe  = false;
            while (i < array.length - 1 && array[i] == array[i+1]) {
                isDupe = true;
                i += 1;
            }
            if (isDupe) {
                //i -= 1;
                continue;
            }
            if (!stack.isEmpty() && array[i] == stack.peek()) {
                stack.pop();
            } else {
                stack.push(array[i]);
            }

        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    public String removeDuplicates1(String S) {
        CharListNode head = creatCharList(S.toCharArray());
        while(true) {
            boolean dupe = false;
            CharListNode current = head;
            CharListNode prev = null;
            while (current != null) {
                if (current.count > 1) {
                    if (prev == null) {
                        head = current.next;
                        //current = current.next;
                    } else {
                        prev.next = current.next;
                    }
                }
                prev = current;
                current = current.next;
            }
            prev = null;
            current = head;
            while (current != null) {
                if (prev != null && prev.ch == current.ch) {
                    merge(prev, current);
                    current = prev;
                    dupe = true;
                }
                prev = current;
                current = current.next;
            }
            if(!dupe) {
                break;
            }
        }
        return head == null ? "" : head.toString();
    }

    private void merge(CharListNode prev, CharListNode current) {
        prev.count += current.count;
        prev.next = current.next;
    }

    private CharListNode creatCharList(char[] array) {
        CharListNode head = null;
        CharListNode current = head;
        char prev = array[0];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == prev) {
                count += 1;
            } else {
                CharListNode node = new CharListNode(prev, count);
                prev = array[i];
                //i += 1;
                count = 1;
                if (head == null) {
                    head = node;
                    current = node;
                } else {
                    current.next = node;
                    current = current.next;
                }
            }
        }
        if (current != null)
            current.next = new CharListNode(prev, count);
        return head;
    }

    private class CharListNode {
        char ch;
        int count;
        CharListNode next;

        public CharListNode(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            CharListNode current = this;
            while (current != null) {
                sb.append(current.ch);
                current = current.next;
            }
            return sb.toString();
        }
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] order = new int[numCourses];
        Map<Integer, Set<Integer>> dependencies
                = getDependencies(prerequisites, numCourses);
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < numCourses; i++) {
            int val = getNextIndependent(dependencies, visited);
            if (val == -1) {
                return new int[]{};
            }
            visited.add(val);
            order[i] = val;
            removeDependent(dependencies, val);
        }
        return order;
    }
    private int[] multiplyPolyNomial(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                res[i+j] += (a[i]*b[j]);
            }
        }
        return res;
    }

    private int maxSum(int[] arr) {
        return 0;
    }
    private ListNode multiplyPolyNomial(ListNode a, ListNode b) {
        ListNode res = new ListNode(0);
        int i = 0;
        for (ListNode first = a; first != null; first = first.getNext()) {
            int j = 0;
            ListNode current = res;
            for (ListNode second = b; second != null; second = second.getNext()) {
                current.setValue(current.getValue() + first.getValue() * second.getValue());
                if (second.getNext() != null && current.getNext() == null) {
                    current.setNext(new ListNode(0));
                }
                current = current.getNext();
            }
        }
        return res;
    }
    private int getNextIndependent(Map<Integer, Set<Integer>> dependencies, Set<Integer> visited) {
        int course = -1;
        for (Map.Entry<Integer, Set<Integer>> entry : dependencies.entrySet()) {
            if(!visited.contains(entry.getKey()) && entry.getValue().isEmpty()) {
                course = entry.getKey();
                break;
            }
        }
        return course;
    }
    private void removeDependent(Map<Integer, Set<Integer>> dependencies, int course) {
        for (Map.Entry<Integer, Set<Integer>> entry : dependencies.entrySet()) {
            entry.getValue().remove(course);
        }
    }
    private Map<Integer, Set<Integer>> getDependencies(int[][] prerequisites, int numCourses) {
        Map<Integer, Set<Integer>>  dependencies = new HashMap<>();
        for (int[] dep : prerequisites) {
            if (dependencies.containsKey(dep[0])) {
                dependencies.get(dep[0]).add(dep[1]);
            } else {
                Set<Integer> dependency = new HashSet<>();
                dependency.add(dep[1]);
                dependencies.put(dep[0], dependency);
            }
        }
        for (int i = 0; i < numCourses; i++) {
            if (!dependencies.containsKey(i)) {
                dependencies.put(i, Collections.EMPTY_SET);
            }
        }
        return dependencies;
    }
    private void testSount() {
        System.out.println(getCount(6,4));
    }

    private int getCount(int len, int bredth) {
        int res = 1;
        if (len != bredth) {
            int max = Math.max(len, bredth);
            int min = Math.min(len, bredth);
            res += getCount(min, max - min);
        }
        System.out.printf("For len %d bredth %d, Count %d\n", len, bredth, res);
        return res;
    }

    public void solveSudoku(int[][] board) {
        //Map<String, Set<Integer>> possiblitiesMap = createPossiblitiesMap(board);

    }
    private boolean solveSudoku(int[][] board, int row , int col) {
        if (row == 9 && col == 9) {
            return true;
        }
        for (int i = row ;i < 9; i++) {
            for(int j = col; j < 9; j++) {
                if (board[i][j] != 0) {
                    continue;
                }
                Set<Integer> possibilities = getValidForCell(board, i, j);
                if (possibilities.size() == 0) {
                    return false;
                }
                for (int value : possibilities) {
                    board[i][j] = value;
                    if(!solveSudoku(board, i, j)) {
                        board[i][j] = 0;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Map<String, Set<Integer>> createPossiblitiesMap(int[][] board) {
        Map<String, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String key = i + "_" + j;
                map.put(key, getValidForCell(board, i, j));
            }
        }
        return map;
    }

    private Set<Integer> getValidForCell(int[][] board, int row, int column) {
        Set<Integer> possibilities = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());
        Set<Integer> exclusions = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            exclusions.add(board[row][i]);
            exclusions.add(board[i][column]);
        }
        exclusions.addAll(getGridValues(board, row, column));
        possibilities.removeAll(exclusions);
        return possibilities;
    }

    private Collection<? extends Integer> getGridValues(int[][] board, int row, int column) {
        Set<Integer> res = new HashSet<>();
        int rStart = 0;
        int rEnd = 0;
        int cStart = 0;
        int cEnd = 0;
        int gridRow = row/3;
        int gridCol = column/3;
        if (gridRow == 0) {
            rStart = 0;
            rEnd = 2;
        } else if (gridRow == 1) {
            rStart = 3;
            rEnd = 5;
        } else {
            rStart = 6;
            rEnd = 8;
        }

        if (gridCol == 0) {
            cStart = 0;
            cEnd = 2;
        } else if (gridCol == 1) {
            cStart = 3;
            cEnd = 5;
        } else {
            cStart = 6;
            cEnd = 8;
        }

        for (int i = rStart; i <= rEnd; i++) {
            for (int j = cStart; j <= cEnd; j++) {
                res.add(board[i][j]);
            }
        }
        return res;
    }

    public boolean isValidSudoku(int[][] board) {
        if (board == null || board.length == 0) {
            return true;
        }
        int rows = board.length;
        int columns = board[0].length;
        if (rows != columns) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            if (!isValidRow(board, i)
            || !isValidColumn(board, i)
            || !isValidGrid(board,i)) {
                return false;
            }

        }
        return true;
    }

    private boolean isValidColumn(int[][] board, int column) {
        boolean[] vals = new boolean[9];
        boolean res = true;
        for (int row = 0; row < 9; row++) {
            if (!isValid(row, column, board, vals)) {
                res = false;
                break;
            }
        }
        return res;
    }

    private boolean isValidRow(int[][] board, int row) {
        boolean[] vals = new boolean[9];
        boolean res = true;
        for (int column = 0; column < 9; column++) {
            if (!isValid(row, column, board, vals)) {
                res = false;
                break;
            }
        }
        return res;
    }
    private boolean isValidGrid(int[][] board, int grid) {
        int rStart = 0;
        int rEnd = 0;
        int cStart = 0;
        int cEnd = 0;
        if (grid < 3) {
            rStart = 0;
            rEnd = 2;
        } else if (grid < 6) {
            rStart = 3;
            rEnd = 5;
        } else {
            rStart = 6;
            rEnd = 8;
        }
        int col = grid % 3;
        if (col == 0) {
            cStart = 0;
            cEnd = 2;
        } else if (col == 1) {
            cStart = 3;
            cEnd =   5;
        } else {
            cStart = 6;
            cEnd =   8;
        }
        boolean res = true;
        boolean[] vals = new boolean[9];
        for (int row = rStart; row <= rEnd; row++) {
            for (int column = cStart; column <= cEnd; column++) {
                if (!isValid(row, column, board, vals)) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    private boolean isValid(int row, int column, int[][] board, boolean[] vals) {
        if (!Character.isDigit(board[row][column])) {
            return true;
        }
        //int value = Character.getNumericValue(board[row][column]);
        int value = board[row][column];
        if (value > 9) {
            return false;
        }
        if (vals[value - 1]) {
            return false;
        } else {
            vals[value - 1] = true;
            return true;
        }
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];

        for (int i = 0; i < n; i++) {              //This graph might be a disconnected graph. So check each unvisited node.
            if (colors[i] == 0 && !validColor(graph, colors, 1, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean validColor(int[][] graph, int[] colors, int color, int node) {
        if (colors[node] != 0) {
            return colors[node] == color;
        }
        colors[node] = color;
        for (int next : graph[node]) {
            if (!validColor(graph, colors, -color, next)) {
                return false;
            }
        }
        return true;
    }

    public int lastStoneWeightII(int[] stones) {
        int S = 0, S2 = 0;
        for (int s : stones) S += s;
        int n = stones.length;
        //dp[i][j]: Max sumValue getting stones 1....j and
        boolean[][] dp = new boolean[S + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            dp[0][i] = true;
        }
        for (int s = 1; s <= S / 2; s++) {
            for (int i = 1; i <= n; i++) {
                if (dp[s][i - 1] || (s >= stones[i - 1] && dp[s - stones[i - 1]][i - 1])) {
                    dp[s][i] = true;
                    S2 = Math.max(S2, s);
                }
            }
        }
        return S - 2 * S2;
    }

    public int lastStoneMinWeight(int[] stones) {
        int totalWeight = 0;
        int partWeight = 0;
        int n = stones.length;
        for (int stone : stones) {
            totalWeight += stone;
        }
        //knapSack[i][j] : Use items 1....i, is it possible to get max total weight-j
        boolean[][] knapSack = new boolean[n + 1][totalWeight + 1];
        //Its always possibel to get max total weight = 0, by not choosing any of elements
        for (int i = 0; i <= n; i++) {
            knapSack[i][0] = true;
        }
        //Using stones 1....i, try to reach as possible near totalWeight/2
        for (int i = 1; i < n; i++) {
            for (int weight = 1; weight <= totalWeight/2; weight++ ) {
                //Is it possible to get weight = weight, by choosing elements from 1....i
                //Don't consider current element
                if (knapSack[i-1][weight]) {
                    knapSack[i][weight] = true;
                } else if (weight >= stones[i - 1] //Consider current stone only if its weight is less then intended weight
                   &&  knapSack[i-1][weight - stones[i - 1]] // Without current stone i, we are able to achieve weight  weight - stones[i]
                ) {
                    knapSack[i][weight] = true;
                    //Update max partWeight possible
                    partWeight = Math.max(partWeight, weight);
                }
            }
        }

        return totalWeight - 2*partWeight;
    }

    public boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();

        // base cases
        if (p.equals(s) || p.equals("*")) return true;
        if (p.isEmpty() || s.isEmpty()) return false;

        // init all matrix except [0][0] element as False
        //d[][j]: Pattern len 1....i  and String len 1...j=> Match possible or not
        boolean[][] d = new boolean[pLen + 1][sLen + 1];
        d[0][0] = true;

        // DP compute
        for(int pIdx = 1; pIdx < pLen + 1; pIdx++) {
            // the current character in the pattern is '*'
            if (p.charAt(pIdx - 1) == '*') {
                int sIdx = 1;
                // d[p_idx - 1][s_idx - 1] is a string-pattern match
                // on the previous step, i.e. one character before.
                // Find the first idx in string with the previous math.
                while ((!d[pIdx - 1][sIdx - 1]) && (sIdx < sLen + 1)) sIdx++;
                // If (string) matches (pattern),
                // when (string) matches (pattern)* as well
                d[pIdx][sIdx - 1] = d[pIdx - 1][sIdx - 1];
                // If (string) matches (pattern),
                // when (string)(whatever_characters) matches (pattern)* as well
                while (sIdx < sLen + 1) d[pIdx][sIdx++] = true;
            }
            // the current character in the pattern is '?'
            else if (p.charAt(pIdx - 1) == '?') {
                for(int sIdx = 1; sIdx < sLen + 1; sIdx++)
                    d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1];
            }
            // the current character in the pattern is not '*' or '?'
            else {
                for(int sIdx = 1; sIdx < sLen + 1; sIdx++) {
                    // Match is possible if there is a previous match
                    // and current characters are the same
                    d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1] &&
                            (p.charAt(pIdx - 1) == s.charAt(sIdx - 1));
                }
            }
        }
        return d[pLen][sLen];
    }
}
