package com.codesignal;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SlidingWindowRateLimiter implements RateLimiter{
    Map<String, LinkedList<Instant>> clientWindows;

    Duration timeWindow;
    int allowedCounts;

    public SlidingWindowRateLimiter(Duration timeWindow, int allowedCounts) {
        this.clientWindows = new HashMap<>();
        this.timeWindow = timeWindow;
        this.allowedCounts = allowedCounts;
    }

    @Override
    public boolean isAllowed(String clientId) {
        Instant currTime = Instant.now();
        if(!clientWindows.containsKey(clientId)) {
            synchronized (clientWindows) {
                if (!clientWindows.containsKey(clientId)) {
                    clientWindows.put(clientId, new LinkedList<>());
                    clientWindows.get(clientId).add(currTime);
                    return true;
                }
            }
        }
        LinkedList<Instant> clientWindow = clientWindows.get(clientId);
        Instant windowStart = currTime.minusMillis(timeWindow.toMinutes());
        synchronized (clientWindow){
            while (!clientWindow.isEmpty()) {
                if (clientWindow.peekLast().isBefore(windowStart)) {
                    clientWindow.removeLast();
                } else {
                    break;
                }
            }
            if (clientWindow.size() >= allowedCounts) {
                return false;
            }
            clientWindow.offerFirst(currTime);
        }
        return true;
    }
    private record Pair(int node, long dist){};
    private record Edge(int to, long weight){};

    long INF = (long)2e18;
    public int[] minCost(int n, int[] prices, int[][] roads) {

        // normal travel graph
        List<Edge>[] emptyGraph = new ArrayList[n];

        // carrying apples graph
        List<Edge>[] carryGraph = new ArrayList[n];

        for(int i = 0; i < n; i++) {
            emptyGraph[i] = new ArrayList<>();
            carryGraph[i] = new ArrayList<>();
        }

        for(int[] e : roads) {

            int u = e[0];
            int v = e[1];
            int cost = e[2];
            int taxi = e[3];

            emptyGraph[u].add(new Edge(v, cost));
            emptyGraph[v].add(new Edge(u, cost));

            carryGraph[u].add(
                    new Edge(v, 1L * cost * taxi));

            carryGraph[v].add(
                    new Edge(u, 1L * cost * taxi));
        }
        int[] ans = new int[n];

        for(int src = 0; src < n; src++) {
            long[] emptyDist = createDistArray(src, n, emptyGraph);
            long[] carryDist = createDistArray(src, n, carryGraph);
            // FIND BEST SHOP
            long best = prices[src];

            for(int shop = 0; shop < n; shop++) {

                if(emptyDist[shop] == INF ||
                        carryDist[shop] == INF)
                    continue;

                long total =
                        emptyDist[shop] +
                                carryDist[shop] +
                                prices[shop];

                best = Math.min(best, total);
            }

            ans[src] = (int)best;
        }

        return ans;
    }

    private long[] createDistArray(int src, int n, List<Edge>[] graph) {
        long[] emptyDist = new long[n];
        // EMPTY TRAVEL
        Arrays.fill(emptyDist, INF);

        PriorityQueue<Pair> pq =
                new PriorityQueue<>(
                        (a, b) -> Long.compare(a.dist, b.dist)
                );

        emptyDist[src] = 0;

        pq.offer(new Pair(0, src));

        while(!pq.isEmpty()) {

            Pair cur = pq.poll();

            long d = cur.dist;
            int u = cur.node;

            if(d > emptyDist[u]) continue;

            for(Edge edge : graph[u]) {

                int v = edge.to;
                long w = edge.weight;

                if(emptyDist[v] > d + w) {

                    emptyDist[v] = d + w;

                    pq.offer(
                            new Pair(v, emptyDist[v])
                    );
                }
            }
        }
        return emptyDist;
    }
}
