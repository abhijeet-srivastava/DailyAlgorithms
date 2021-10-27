package com.oracle.casb.leetcode;

/**
 * Created By : abhijsri
 * Date  : 13/12/18
 **/
public class LeetCodeSample {

    public static void main(String[] args) {
        LeetCodeSample lcs = new LeetCodeSample();
        lcs.testSplitRunId();
        //lcs.testAlienDictionary();
    }

    private void testSplitRunId() {
        Long helixRunId = Long.valueOf(201812041002l);
        String asStr = helixRunId.toString();
        Integer runId = Integer.valueOf(asStr.substring(0, asStr.length() - 2));
        Integer eventHour = Integer.valueOf(asStr.length() - 2);
        System.out.println("run id - " + asStr.substring(0, asStr.length() - 2));
        System.out.println("eventHour - " + asStr.substring(asStr.length() - 2));
    }

    private void testAlienDictionary() {
        String[] words = {"apple", "app"};
        String order = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(isAlienSorted(words, order));
    }

    public boolean isAlienSorted(String[] words, String order) {
        int[] index = new int[order.length()];
        for(int i = 0; i < order.length(); i++) {
            index[order.charAt(i) - 'a'] = i;
        }
        boolean result = true;
        for (int i = 0; i < words.length - 1;i++) {
            if (!isInOrder(words[i], words[i+1], index)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean isInOrder(String word, String word1, int[] index) {
        int len = word.length();
        int len1 = word1.length();
        boolean result = true;
        int i = 0;
        for (; i < word.length() && i < word1.length(); i++) {
            if (index[word.charAt(i) - 'a'] > index[word1.charAt(i) - 'a']) {
                result = false;
                break;
            }
        }
        if (result) {
            result = !(i < word.length());
        }
        return result;
    }
}
