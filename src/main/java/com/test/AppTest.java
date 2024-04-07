package com.test;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppTest {
    public static void main(String[] args) {
        AppTest at = new AppTest();
        //at.testString();
        //at.testTeamRatings();
        at.testStringTransformation();
    }

    private void testStringTransformation() {
        String s = "abcd", t = "cdab";
        StringBuilder sb = new StringBuilder(t);
        sb.append('#');
        sb.append(s);
        sb.append(s);
        int[] zArr = zFunction(sb.toString());
        System.out.printf("Concat String: %s\n", sb.toString());
    }

    private int[] zFunction(String str) {
        int n = str.length();
        int[] z = new int[n];
        int l = 0;
        for(int i = 1; i < n; i++) {
            z[i] = Math.min(z[l] + l -i, z[i-l]);
            z[i] = Math.max(0, z[i]);
            while(i + z[i] < n && str.charAt(z[i]) == str.charAt(i+z[i])) {
                z[i]++;
            }
            if(i + z[i] > l + z[i]) {
                l = i;
            }
        }
        return z;
    }

    private void testTeamRatings() {
        String[] votes = {"WXYZ","XYZW"};
        String ratings = rankTeams(votes);
        System.out.printf("Ratings: %s\n", ratings);
    }

    public String rankTeams(String[] votes) {
        if(votes.length == 1) {
            return votes[0];
        }
        int count = votes[0].length();
        Map<Character, TeamRanks> teams = new HashMap<>();
        for(char ch: votes[0].toCharArray()) {
            teams.put(ch, new TeamRanks(ch, count));
        }
        for(String vote: votes) {
            for(int i = 0; i < vote.length(); i++) {
                teams.get(vote.charAt(i)).updateRatings(i);
            }
        }
        return teams.values().stream().sorted()
                .map(a -> a.teamName).map(String::valueOf)
                .collect(Collectors.joining(""));
    }
    
    private class TeamRanks implements Comparable<TeamRanks> {
        Character teamName;
        int[] rankingCounts;
        
        private TeamRanks(char name, int n) {
            this.teamName = name;
            this.rankingCounts = new int[n];
        }
        private void updateRatings(int pos) {
            rankingCounts[pos] += 1;
        }

        @Override
        public int compareTo(TeamRanks other) {
            for(int i = 0; i < this.rankingCounts.length; i++) {
                if(this.rankingCounts[i] < other.rankingCounts[i]) {
                    return 1;
                } else if(this.rankingCounts[i] > other.rankingCounts[i]) {
                    return -1;
                }
            }
            return this.teamName.compareTo(other.teamName);
        }
    }

    private void testString() {
        char[] keys = new char[26];
        keys[3] = 'a';
        System.out.printf("State: %s\n", new String(keys));
    }
}
