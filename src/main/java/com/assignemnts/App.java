package com.assignemnts;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        App app = new App();
        //app.testAssignment();
        //app.testCommonOcean();
        app.testCandidateRanking();
    }

    private void testCandidateRanking() {
        List<Ballot> ballots = getBallots_3();
        // A - 9, C - 8, B - 4, D - 3
        List<String> res = getResult(ballots);
        for(String candidate: res) {
            System.out.printf("Candidate - %s\n", candidate);
        }
    }

    private List<Ballot> getBallots_1() {
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(new Ballot("A", "B"));
        ballots.add(new Ballot("C", "B", "A"));
        // A - 4, B - 4, C - 3
        return ballots;
    }

    private List<Ballot> getBallots_2() {
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(new Ballot("A", "B"));
        ballots.add(new Ballot("C", "B", "A"));
        ballots.add(new Ballot("D", "A", "B"));
        ballots.add(new Ballot("P", "M", "B"));
        // A - 6, B - 6, C - 3
        return ballots;
    }

    private List<Ballot> getBallots_3() {
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(new Ballot("A", "B"));
        ballots.add(new Ballot("C", "B", "A"));
        //B - 4, A - 4, C - 3
        return ballots;
    }

    @NotNull
    private List<Ballot> getBallots() {
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(new Ballot("A", "B", "C"));
        ballots.add(new Ballot("A", "C", "B"));
        ballots.add(new Ballot("A", "C", "D"));
        ballots.add(new Ballot("C", "D", "B"));
        return ballots;
    }

    private List<String> getResult(List<Ballot> ballots) {
        Map<String, CandidateVote> candidateVotes = new HashMap<>();
        // TreeMap<Integer, String> pointToCandidate = new TreeMap<>();
        int si = 0;
        for(int bi = 0; bi < ballots.size(); bi++) {
            Ballot ballot = ballots.get(bi);
            if(ballot.vote.size() > 3) {
                continue;
            }
            for(int idx = 0; idx < ballot.vote.size(); idx++) {
                //CandidateVote candidate = candidateVotes.get(ballot.vote.get(idx));
                if(!candidateVotes.containsKey(ballot.vote.get(idx))) {
                    candidateVotes.put(ballot.vote.get(idx), new CandidateVote(ballot.vote.get(idx)));
                }
                candidateVotes.get(ballot.vote.get(idx)).incrementRank(idx);
                candidateVotes.get(ballot.vote.get(idx)).incrementPoint(idx, si);
                /*if(!pointToCandidate.containsKey(candidateVotes.get(ballot.vote.get(idx)).points)) {
                    pointToCandidate.put(candidateVotes.get(ballot.vote.get(idx)).points, )
                }*/
                si += 1;
            }
        }
        List<CandidateVote> sortedCandidates = new ArrayList<>();
        for(Map.Entry<String, CandidateVote> entry: candidateVotes.entrySet()) {
            sortedCandidates.add(entry.getValue());
        }
        Collections.sort(sortedCandidates, Comparator.reverseOrder());
        return sortedCandidates.stream().map(c -> c.name).toList();
    }

    private class CandidateVote implements Comparable<CandidateVote> {
        public String name;
        public int[] rankCount;

        public int points;

        public int timestamp;

        public CandidateVote(String name) {
            this.name = name;
            this.rankCount = new int[3];
        }

        public void incrementRank(int rankIdx) {
            this.rankCount[rankIdx] += 1;
        }

        public void incrementPoint(int rankIdx, int ts) {
            // 0 - 3, 1 - 2, 2 - 1
            if(rankIdx == 0) {
                this.points += 3;
            } else if(rankIdx == 1) {
                this.points += 2;
            } else {
                this.points += 1;
            }
            this.timestamp = ts;
        }


        @Override
        public int compareTo(@NotNull App.CandidateVote other) {
            if (other.points != this.points) {
                return Integer.compare(this.points, other.points);
            }
            return Integer.compare(other.timestamp, this.timestamp);
            /*for(int i = 0; i < this.rankCount.length; i++) {
                if(this.rankCount[i] < other.rankCount[i]) {
                    return -1;
                } else if(this.rankCount[i] > other.rankCount[i]) {
                    return 1;
                }
            }
            return this.name.compareTo(other.name);*/
        }
    }


    public class Ballot {
        public List<String> vote;
        public Ballot(String cand1, String cand2, String cand3) {
            this.vote = new ArrayList<>(3);
            this.vote.add(cand1);
            this.vote.add(cand2);
            this.vote.add(cand3);
        }
        public Ballot(String cand1, String cand2) {
            this.vote = new ArrayList<>(2);
            this.vote.add(cand1);
            this.vote.add(cand2);
        }
        public Ballot(String cand1) {
            this.vote = new ArrayList<>(1);
            this.vote.add(cand1);
        }

    }

    private void testCommonOcean() {
        int[][] island = {{3,3,3,3,3,3}, {3,0,3,3,0,3}, {3,3,3,3,3,3}};
        List<List<Integer>> common = pacificAtlantic(island);
        String res = common.stream().map(e -> String.format("{%d, %d}", e.get(0), e.get(1))).collect(Collectors.joining(","));
        System.out.printf("Res: [%s]\n", res);
    }


    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Set<Integer> poCells = fetchCells(0, 0, heights);
        Set<Integer> aoCells = fetchCells(m-1, n-1, heights);
        poCells.retainAll(aoCells);
        List<List<Integer>> res = new ArrayList<>();
        for(int commonCell: poCells) {
            res.add(Arrays.asList(commonCell/n, commonCell%n));
        }
        return res;
    }
    int[] DIRS = {-1, 0, 1, 0, -1};
    private Set<Integer> fetchCells(int initRow, int initCol, int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            queue.offer(new int[]{i, initCol});
            visited[i][initCol] = true;
        }
        for(int i = 0; i < n; i++) {
            queue.offer(new int[]{initRow, i});
            visited[initRow][i] = true;
        }
        Set<Integer> result = new HashSet<>();

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            result.add(curr[0]*n + curr[1]);
            for(int i = 0; i < 4; i++) {
                int x = curr[0] + DIRS[i], y = curr[1] + DIRS[i+1];
                if(x < 0 || x  >= m
                        || y < 0 || y >= n
                        || visited[x][y]
                        || heights[curr[0]][curr[1]] > heights[x][y]) {
                    continue;
                }
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
        return result;
    }

    private void testAssignment() {
        String s = "0110011";
        //String s = "0110";
        int n1 = 1, n2 = 2;
        long count = fixedRatio(s, n1, n2);
        System.out.printf("Count: %d\n", count);
    }

    public long fixedRatio(String s, int n1, int n2) {
        Map<Long, Integer> freq =new HashMap<>();
        freq.put(0l, 1);
        long res = 0l, prefix = 0l;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '0') {
                prefix += n2;
            } else {
                prefix -= n1;
            }
            res += freq.getOrDefault(prefix, 0);
            freq.merge(prefix, 1, Integer::sum);
        }
        return res;
    }
}
