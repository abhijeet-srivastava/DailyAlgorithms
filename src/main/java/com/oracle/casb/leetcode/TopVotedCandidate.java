package com.oracle.casb.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created By : abhijsri
 * Date  : 28/09/18
 **/
public class TopVotedCandidate {

    public static void main(String[] args) {
        TopVotedCandidate tvc = new TopVotedCandidate(new int[]{0,1,1,0,0,1,0}, new int[] {0,5,10,15,20,25,30});
        //TopVotedCandidate tvc = new TopVotedCandidate(new int[]{0,0,0,0,1}, new int[] {0,6,39,52,75});
        int[] q = new int[] {45, 49,59, 68, 42, 37, 99, 26, 78, 43};

        System.out.println(tvc.query(45));
    }
    TreeMap<Integer, Integer> timeVote = new TreeMap<>();

    public TopVotedCandidate(int[] persons, int[] times) {
        Map<Integer, Integer> personVote = new HashMap<>();
        int[] topVoted = new int[]{persons[0], 1};
        personVote.put(persons[0], 1);
        timeVote.put(times[0], topVoted[0]);
        for (int i = 1; i < persons.length ; i++) {
            int votes = personVote.containsKey(persons[i]) ? personVote.get(persons[i]) : 0;
            votes += 1;
            personVote.put(persons[i], votes);
            if (votes >= topVoted[1]) {
                topVoted[0] = persons[i];
                topVoted[1] = votes;
            }
            timeVote.put(times[i], topVoted[0]);
        }
    }

    public int query(int t) {
        return timeVote.floorEntry(t).getValue();
    }
}
