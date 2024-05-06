package com.tree;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppTesting {

    public static void main(String[] args) {
        AppTesting at = new AppTesting();
        //at.testSortArray();
        //at.testParallelStream();
        //System.out.printf("%d\n",'z' - 'A');
        //at.testCountWays();
       // at.testMinWindow();
    }


    private void testMinWindow() {
        String s = "ADOBECODEBANC", t= "ABC";
        String res = minWindow(s, t);
        System.out.printf("res: %s\n", res);
    }

    public String minWindow(String s, String t) {
        int[] counter = new int['z' - 'A' + 1];
        for(char ch : t.toCharArray()) {
            counter[ch-'A'] += 1;
        }
        String min = "";
        int minLength = s.length() + 1;
        int tf = t.length();
        for(int l = 0, r = 0; r < s.length(); r++) {
            char we = s.charAt(r);
            if(counter[we - 'A'] > 0) {
                tf -= 1;
            }
            counter[we - 'A'] -= 1;
            while(tf == 0) {
                if(r-l+1 < minLength) {
                    minLength = r-l+1;
                    min = s.substring(l, r+1);
                }
                char ws = s.charAt(l);
                counter[ws - 'A'] += 1;
                if(counter[ws - 'A'] > 0) {
                    tf += 1;
                }
                l += 1;
            }
        }
        return min;
    }

    private void testCountWays() {
        String[] words = {"acca","bbbb","caca"};
        String target = "aba";
        int count = numWays(words, target);
        System.out.printf("count: %d\n", count);
    }

    public int numWays(String[] words, String target) {
        int MOD = 1_000_000_007;
        int m = words.length, n = words[0].length(), t = target.length();
        int[][] charCountAtIdx = new int[26][n];
        for(String str: words) {
            for(int i = 0; i < str.length(); i++) {
                charCountAtIdx[str.charAt(i)-'a'][i] += 1;
            }
        }
        long[][] DP = new long[t+1][n+1];
        DP[0][0] = 1;
        for(int i = 0; i <= t; i++) {
            for(int j = 1; j <= n; j++) {
                if(i > 0){
                    DP[i][j] = (DP[i-1][j-1]*charCountAtIdx[target.charAt(i-1)-'a'][j-1])%MOD;
                }
                DP[i][j] = (DP[i][j] + DP[i][j-1])%MOD;
            }
        }
        long[][] dp = new long[t + 1][n + 1];
        dp[0][0] = 1;

        for (int length = 0; length <= t; length++) {
            for (int col = 0; col < n; col++) {
                if (length < t) {
                    dp[length + 1][col + 1] += charCountAtIdx[target.charAt(length) - 'a'][col] * dp[length][col];
                    dp[length + 1][col + 1] %= MOD;
                }
                dp[length][col + 1] += dp[length][col];
                dp[length][col + 1] %= MOD;
            }
        }

        return (int)DP[t][n];
    }

    public enum JAVA_VERSION {
        JavaSE6,JavaSE7,JavaSE8,JavaSE9,JavaSE10,JavaSE11,JavaSE12,JavaSE13, JavaSE14, JavaSE17, JavaSE21
    }
    private void testParallelStream() {
        boolean isParallel = IntStream.rangeClosed(1, 1000).boxed().parallel().map(x -> Math.sqrt(x)).sequential().sorted().isParallel();
        System.out.printf("IsParallel: %b\n", isParallel);
        JAVA_VERSION version=JAVA_VERSION.JavaSE21;

        var support = switch (version) {
            case JavaSE6, JavaSE7, JavaSE8 : yield "Neither LTS nor Non-LTS";
            case JavaSE9,JavaSE10,JavaSE12,JavaSE13 : yield "Non-LTS";
            case JavaSE11: yield "LTS";
            default : String ver=version.toString();
                yield ver;
        };

        System.out.printf("version: %s", support);
    }

    private void testSortArray() {
        int[] arr = {29, 10, 14, 37, 13};
        sortArray(arr);
        String afteSort = IntStream.of(arr).boxed().map(String::valueOf).collect(Collectors.joining(", "));
        System.out.printf("Sorted: [%s]\n", afteSort);
    }

    private void sortArray(int[] arr) {
        for(int i = arr.length-1; i >=0; i--) {
            for(int j = 1; j <= i; j++) {
                if(arr[j-1] > arr[j]) {
                    swap(j, j-1, arr);
                }
            }
        }
    }

    private void swap(int i, int j, int[] arr) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
