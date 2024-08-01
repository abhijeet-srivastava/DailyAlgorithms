package com.leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Online IDE!! Happy Coding :)");
        Main main = new Main();

        //main.testSlowestPath();
        //main.testMaxProfitForIpo();
        //main.testBitSet();
        //main.testMaxSum();
        main.testMaxProfit();
    }

    private void testMaxProfit() {
        int[] difficulty = {64,88,97};
        int[] profit = {53,86,89};
        int[] worker = {98,11,6};
        int maxProfit = maxProfitAssignment(difficulty, profit, worker);
        System.out.printf("Max profit: %d\n", maxProfit);
    }
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int jobLen = difficulty.length, workerLen = worker.length;
        int[][] jobProfit = new int[jobLen][3];
        for(int i = 0; i < jobLen; i++) {
            jobProfit[i] = new int[]{difficulty[i], profit[i], 0};
        }
        Arrays.sort(jobProfit, (a, b) -> Integer.compare(a[0], b[0]));
        jobProfit[0][2] = jobProfit[0][1];
        for(int i = 1; i < jobLen; i++) {
            jobProfit[i][2] = Math.max(jobProfit[i-1][2], jobProfit[i][1]);
        }
        //Arrays.sort(worker);
        int maxProfit = 0;
        for(int work: worker) {
            int idx = bisectLeft(jobProfit, work);
            if(idx < 0) {
                //break;
                continue;
            }
            maxProfit += jobProfit[idx][2];
        }
        return maxProfit;
    }
    private int bisectLeft(int[][] jobProfit, int work) {
        int l = 0, r = jobProfit.length-1;
        int res = -1;
        while(l <= r) {
            int mid = l + (r-l)/2;
            if(jobProfit[mid][0] <= work) {
                res = mid;
                l = mid+1;
            } else {
                r = mid -1;
            }
        }
        return res;
    }

    private void testMaxSum() {
        int[] arr = {1000000000,1000000000,1000000000,1000000000,1000000000};
        long maxDamage = maximumTotalDamage(arr);
        System.out.println("Damage :"+ maxDamage);
    }

    private void testBitSet() {
        BitSet bs = new BitSet(5);
        bs.set(1);
        System.out.printf("%s\n", bs.toString());
    }

    public long maximumTotalDamage(int[] power) {
        TreeMap<Integer, Integer> damage = new TreeMap<>();
        for(int pow: power) {
            damage.merge(pow, 1, Integer::sum);
        }
        TreeMap<Integer,Long> maxSpellDamages = new TreeMap<>();
        long maxDamage = 0l;
        for(var t: damage.entrySet()) {
            long currDamage = t.getKey()*t.getValue();
            System.out.printf("Curr Spell: %d, Weight: %d, Total Damage: %d\n", t.getKey(), t.getValue(), currDamage);
            var prevDamageEntry = maxSpellDamages.lowerEntry(t.getKey() - 2);
            if(prevDamageEntry != null) {
                currDamage += prevDamageEntry.getValue();
                System.out.printf("Pre Spell: %d, Prev Spell Weight: %d, Total Damage: %d\n", prevDamageEntry.getKey(), prevDamageEntry.getValue(), prevDamageEntry.getKey()*prevDamageEntry.getValue());
            }
            maxDamage = Math.max(maxDamage, currDamage);
            maxSpellDamages.put(t.getKey(), maxDamage);
        }
        return maxDamage;
    }

    private void testMaxProfitForIpo() {
        int  k = 3, w = 0;
        int[] profits = {1,2,3}, capital = {0,1,2};
        int val = findMaximizedCapital(k, w, profits, capital);
        System.out.printf("val: %d\n", val);
        Deque<Integer> dq = new ArrayDeque<>();
        dq.pollLast();
    }
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        TreeMap<Integer, TreeSet<Integer>> capitalProfit = new TreeMap<>();
        for(int i = 0; i < profits.length; i++) {
            capitalProfit.computeIfAbsent(capital[i], e -> new TreeSet<>(Comparator.reverseOrder())).add(profits[i]);
        }
        while(k-- > 0) {
            var lower = capitalProfit.floorEntry(w);
            if(lower == null) {
                break;
            }
            Iterator<Integer> itr = lower.getValue().iterator();
            int currProfit = itr.next();
            itr.remove();
            if(lower.getValue().isEmpty()) {
                capitalProfit.remove(lower.getKey());
            }
            w += currProfit;
        }
        return w;
    }

    public void testSlowestPath() {
        int[][] adjacencyMatrix = {
                {0, 70, 100, 75, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 120, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 150, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 210, 30},
                {0, 0, 0, 0, 0, 0, 0, 80, 50, 0, 0},
                {0, 0, 0, 0, 0, 0, 70, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        SlowestPath slowestPath = slowestPath1(adjacencyMatrix);
        System.out.printf("Slowest path: [%s], value: %d\n", slowestPath.path, slowestPath.pathWeight);

    }
    String maxPath = "";
    public SlowestPath slowestPath(int[][] adjecencyMatrix) {
        int len = adjecencyMatrix.length;
        int root = -1;
        for(int col = 0; col < len; col++) {
            if(allZeroColumn(col, adjecencyMatrix)) {
                root = col;
                break;
            }
        }
        Set[] GRAPH = new Set[len];
        for(int i = 0; i < len; i++) {
            GRAPH[i] = new HashSet<int[]>();
        }
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(adjecencyMatrix[i][j] == 0) {
                    continue;
                }
                GRAPH[i].add(new int[]{j, adjecencyMatrix[i][j]});
            }
        }
        int[] max = {0};
        dfs(root, 0, "", GRAPH, max);
        return new SlowestPath(maxPath, max[0]);
    }
    private void dfs(int node, int weight, String path, Set[] GRAPH, int[] max) {
        if(GRAPH[node].isEmpty()) {
            if(max[0] < weight) {
                max[0] = weight;
                Character nodeVal = (char)(node + 'A');
                maxPath = path + String.valueOf(nodeVal);
            }
            return;
        }
        Character nodeVal = (char)(node + 'A');
        path = path + String.valueOf(nodeVal);
        Set<int[]> children = GRAPH[node];
        for(int[] child: children) {
            dfs(child[0], weight + child[1], path, GRAPH, max);
        }

    }

    public SlowestPath slowestPath1(int[][] adjecencyMatrix) {
        int len = adjecencyMatrix.length;
        int[] ib = new int[len], ob = new int[len];
        Set[] GRAPH = new Set[len];
        for(int i = 0; i < len; i++) {
            GRAPH[i] = new HashSet<int[]>();
        }
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(adjecencyMatrix[i][j] == 0) {
                    continue;
                }
                GRAPH[j].add(new int[]{i, adjecencyMatrix[i][j]});
                ib[j] += 1;
                ob[i] += 1;
            }
        }
        int root = -1;
        for(int i = 0; i < len; i++) {
            if(ib[i] == 0) {
                root = i;
                break;
            }
        }
        int[] max = {0};
        for(int i = 0; i < len; i++) {
            if(ob[i] == 0) {
                dfs1(i, 0, "", GRAPH, max, root);
            }
        }
        return new SlowestPath(maxPath, max[0]);
    }

    private void dfs1(int node, int weight, String path, Set[] GRAPH, int[] max, int root) {
        if(node == root) {
            if(max[0] < weight) {
                max[0] = weight;
                Character nodeVal = (char)(node + 'A');
                maxPath = String.valueOf(nodeVal) + path;
            }
            return;
        }
        Character nodeVal = (char)(node + 'A');
        path = String.valueOf(nodeVal) + path;
        Set<int[]> children = GRAPH[node];
        for(int[] child: children) {
            dfs(child[0], weight + child[1], path, GRAPH, max);
        }
    }

    private boolean allZeroColumn(int colIdx, int[][] adjecencyMatrix) {
        int len = adjecencyMatrix.length;
        for(int rowIdx = 0; rowIdx < len; rowIdx++) {
            if(adjecencyMatrix[rowIdx][colIdx] > 0) {
                return false;
            }
        }
        return true;
    }

    public record SlowestPath(String path, Integer pathWeight){};
}
