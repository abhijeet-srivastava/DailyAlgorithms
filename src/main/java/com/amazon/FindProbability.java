package com.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FindProbability {

    public static void main(String[] args) {
       /* FindProbability fp = new FindProbability();
        fp.testFavorableCount();*/
        String c = "Hello I am practicing Java";
        c = c.trim();
        c = c.replaceAll("am", "was");
        c =c.substring(3, 5);
        byte[] b = c.getBytes();
        for(int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        System.out.printf("%s\n", c);
    }

    private void testFavorableCount() {
        List<List<Integer>> timeForIncompleteRuns = Arrays.asList(
                Arrays.asList(2,3,2),
                Arrays.asList(3,2)
        );
        int count = backtrack(0, timeForIncompleteRuns, 5);
        System.out.printf("Count: %d\n", count);
    }

    private int backtrack(int idx,  List<List<Integer>> timeForIncompleteRuns, int remBudget) {
        //2,3,2.  3,2
        int len = timeForIncompleteRuns.size();
        if(remBudget < 0) {
            return 0;
        } else if(idx == len) {
            return 1;
        }
        int count = 0;
        List<Integer> currObsTimes = timeForIncompleteRuns.get(idx);
        for(int currObsRunTime: currObsTimes) {
            if(currObsRunTime > remBudget) {
                continue;
            }
            System.out.printf("Taking: %d,  from idx: %d remBudget: %d\n", currObsRunTime, idx, remBudget-currObsRunTime);
            count += backtrack(idx+1, timeForIncompleteRuns, remBudget-currObsRunTime);
        }
        return count;
    }
    public List<String> commonChars(String[] words) {
        char[] commonChars =  Arrays.stream(words)
                .map(w -> w.toCharArray())
                .reduce((a,b) -> reduceIntersection(a, b))
                .get();
        List<String> res = new ArrayList<>();
        for(char ch: commonChars) {
            res.add(String.valueOf(ch));
        }
        return res;
    }
    private char[] reduceIntersection(char[] w1, char[] w2) {
        int[] c1 = new int[26], c2 =  new int[26], common = new int[26];
        for(char ch: w1) {
            c1[ch - 'a'] += 1;
        }
        for(char ch: w2) {
            c2[ch - 'a'] += 1;
        }
        int count = 0;
        for(int i = 0; i < 26; i++) {
            common[i] = Math.min(c1[i], c2[i]);
            count += common[i];
        }
        int idx = 0;
        char[] res = new char[count];
        for(int i = 0; i < 26; i++) {
            for(int charCount = 0; charCount < common[i]; charCount++) {
                res[idx++] = (char)(i + 'a');
            }
        }
        return res;
    }
}
