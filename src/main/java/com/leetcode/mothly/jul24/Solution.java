package com.leetcode.mothly.jul24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        //sol.testAlienDictionary();
        sol.testMinDiff();
    }

    private void testMinDiff() {
        int[] nums = {1,5,0,10,14};
        int diff = minDifference(nums);
        System.out.printf("Diff: %d\n", diff);
    }

    public int minDifference(int[] nums) {
        int len = nums.length;
        if(len <= 4) {
            return 0;
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(),
                maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int num: nums) {
            minHeap.offer(num);
            if(minHeap.size() > 4) {
                minHeap.poll();
            }
            maxHeap.offer(num);
            if(maxHeap.size() > 4) {
                maxHeap.poll();
            }
        }
        int[] tmp = new int[4];
        for(int idx = 0; idx < 4; idx++) {
            tmp[idx] = minHeap.poll();
        }
        int minDiff = Integer.MAX_VALUE;
        for(int idx = 3; idx >= 0; idx--) {
            minDiff = Math.min(minDiff, tmp[idx] - maxHeap.poll());
        }
        return minDiff;
    }

    private void testAlienDictionary() {
        String[] words = {"ac","ab","zc","zb"};
        String seq = alienOrder(words);
        System.out.printf("Order: %s\n", seq);
    }
    public String alienOrder(String[] words) {
        int len = words.length;
        Map<Character, Set<Character>> adjMap = new HashMap<>();
        Map<Character, Integer> ib = new HashMap<>();
        for(String word: words) {
            for(char ch: word.toCharArray()) {
                adjMap.computeIfAbsent(ch, c -> new HashSet<>());
                ib.put(ch, 0);
            }
        }
        for(int idx = 0; idx < len-1; idx++) {
            String precedessor = words[idx], successor = words[idx+1];
            if(precedessor.length() > successor.length()
                    && precedessor.startsWith(successor)) {
                return "";
            }
            for(int i = 0; i < Math.min(precedessor.length(), successor.length()); i++) {
                if(successor.charAt(i) != precedessor.charAt(i)) {
                    /*adjMap.get(precedessor.charAt(i)).add(successor.charAt(i));
                    ib.merge(successor.charAt(i), 1, Integer::sum);*/
                    if(adjMap.get(precedessor.charAt(i)).add(successor.charAt(i))) {
                        ib.merge(successor.charAt(i), 1, Integer::sum);
                    }
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        Deque<Character> queue = new ArrayDeque<>();
        for(var t: ib.entrySet()) {
            if(t.getValue() == 0) {
                queue.offer(t.getKey());
            }
        }
        while(!queue.isEmpty()) {
            Character curr = queue.poll();
            sb.append(curr);
            Set<Character> children = adjMap.getOrDefault(curr, Collections.emptySet());
            for(char child: children) {
                ib.merge(child, -1, Integer::sum);
                if(ib.get(child) == 0) {
                    queue.offer(child);
                }
            }
        }
        if(sb.length() != ib.size()) {
            return "";
        }
        return sb.toString();
    }
    public String alienOrder1(String[] words) {

        // Step 0: Create data structures and find all unique letters.
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                counts.put(c, 0);
                adjList.put(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
                    break;
                }
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : counts.keySet()) {
            if (counts.get(c).equals(0)) {
                queue.add(c);
            }
        }
        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                counts.put(next, counts.get(next) - 1);
                if (counts.get(next).equals(0)) {
                    queue.add(next);
                }
            }
        }

        if (sb.length() < counts.size()) {
            return "";
        }
        return sb.toString();
    }
}
