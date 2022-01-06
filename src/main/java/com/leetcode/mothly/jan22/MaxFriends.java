package com.leetcode.mothly.jan22;

import java.util.*;
import java.util.stream.IntStream;

public class MaxFriends {


    public static void main(String[] args) {
        MaxFriends mf = new MaxFriends();
        //mf.testMaxInvites();
        mf.testCourseSchedule();
    }

    private void testCourseSchedule() {
        int[][] relations = {{2,7},{2,6},{3,6},{4,6},{7,6},{2,1},{3,1},{4,1},{6,1},{7,1},{3,8},{5,8},{7,8},{1,9},{2,9},{6,9},{7,9}};
        int[] time = {9,5,9,5,8,7,7,8,4};
        int mt = minimumTime(time.length, relations, time);
        System.out.printf("Min time: %d\n", mt);
    }

    private void testMaxInvites() {
        int[] favs = {2,2,1,2};
        int max = maximumInvitations(favs);
        System.out.printf("Max Invite: %d\n", max);
    }

    public int minimumTime(int n, int[][] relations, int[] time) {
        Set<Integer>[] GRAPH = new Set[n];
        for(int i = 0; i < n; i++) {
            GRAPH[i] = new HashSet<Integer>();
        }
        int[] indegree = new int[n];
        for(int[] relation: relations) {
            GRAPH[relation[0]-1].add(relation[1]-1);
            indegree[relation[1]-1] += 1;
        }
        int[] dist = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) {
                queue.offer(i);
                dist[i] = time[i];
            }
        }
        while(!queue.isEmpty()) {
            int count = queue.size();
            while(count-- > 0) {
                int curr = queue.remove();
                for(int next : GRAPH[curr]) {
                    dist[next] = Math.max(dist[next], dist[curr] + time[next]);
                    indegree[next] -= 1;
                    if(indegree[next] <= 0) {
                        queue.offer(next);
                    }
                }
            }
        }
        return IntStream.of(dist).max().getAsInt();
    }

    public int maximumInvitations(int[] favorite) {
            int n = favorite.length;
            boolean[] visited = new boolean[n];
            //Getting Acyclic part
            int[] indegree = new int[n];
            for(int i = 0; i < n; i++){
                indegree[favorite[i]] += 1;
            }
            Queue<Integer> queue = new LinkedList<>();
            for(int i = 0; i < n; i++ ) {
                if(indegree[i] == 0) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            int[] dp = new int[n]; // dp[i] is the longest path leading to i exclusively.
            while(!queue.isEmpty()) {
                int curr = queue.poll();
                int fav = favorite[curr];
                dp[fav] = Math.max(dp[fav], dp[curr] + 1);
                indegree[fav] -= 1;
                if(indegree[fav] == 0) {
                    queue.offer(fav);
                    visited[fav] = true;
                }
            }
            // now not visited nodes are all loops. check each loop's length.
            int resultLen2 = 0;//loops of length 2 and paths leading to both nodes.
            int result = 0; //other loops
            for(int i = 0; i < n; i++) {
                if(visited[i]) {//Acyclic node, skip
                    continue;
                }
                int length = 0;
                for(int j = i; visited[j] == false; j = favorite[j]) {
                    visited[j] = true;
                    length += 1;
                }
                if(length == 2) {
                    resultLen2 += 2 + dp[i] + dp[favorite[i]];
                } else {
                    result = Math.max(result, length);
                }
            }
            return Math.max(result, resultLen2);
    }

    private int dfs(List<List<Integer>> graph, int curr) {
        int max = 0;
        for(int next : graph.get(curr)) {
            max = Math.max(max, dfs(graph, next));
        }
        return max+1;
    }
}
