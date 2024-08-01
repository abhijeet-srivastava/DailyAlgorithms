package com.leetcode.contest393;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class LeetCodeContest {

    public static void main(String[] args) {
        LeetCodeContest lcc = new LeetCodeContest();

        //lcc.testFinfKthSmallest();
        //lcc.testJobMinDiff();
        //lcc.testMessageParts();
        //lcc.testSimilars();
        //lcc.testKthSmallestFract();
        lcc.testKthSmallest();
    }



    private void testKthSmallest() {
        //int[] quality = {3,1,10,10,1}, wqge = {4,8,2,2,7};
        int[] quality = {10,20,5}, wage = {70,50,30};
        double minPay = mincostToHireWorkers(quality, wage, 2);
        System.out.printf("MinPay: %f\n", minPay);
    }

    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        //1. p1/q1 = p2/q2 (same for all indixes) pi = qi* ratio
        //2. pi >= wi
        //(1,2) => qi*ratio >= wi ==> ratio >= wi/qi ==> ratio >= ratio(i) ==> ratio is highest ratio of all index
        //For each ratio(ri), consider all ratio lower then it(0..j), and take least k pays(=ri*qj)
        //Keeping ri fixed at each index, its equal to sum of leas k qualities
        int n = quality.length;
        double[][] workers = new double[n][2];
        for(int i = 0; i < n; i++) {
            workers[i] = new double[]{findRatio(wage[i], quality[i]), (double)quality[i]};
        }
        Arrays.sort(workers, (a,b) -> Double.compare(a[0],b[0]));
        //Least k quantities
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> Integer.compare(b, a));
        double leastCost = Double.MAX_VALUE;
        double leastKQualitySum = 0.0d;
        for(int i = 0; i < n; i++) {
            double currRatio = workers[i][0];
            int currQuality = (int)workers[i][1];
            pq.offer(currQuality);
            leastKQualitySum += currQuality;
            if(i >= k) {
                leastKQualitySum -= pq.poll();
            }
            if(i >= k-1) {
                leastCost = Math.min(leastCost, leastKQualitySum*currRatio);
            }
        }
        return leastCost;
    }
    private double findRatio(int wage, int quality) {
        return Double.valueOf(wage)/Double.valueOf(quality);
    }

    private void testKthSmallestFract() {
        int[] arr = {1,2,3,5};
        int k = 3;
        int[] res = kthSmallestPrimeFraction(arr, k);
        System.out.printf("res: (%d, %d)\n", res[0], res[1]);
    }

    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        //PriorityQueue<double[]> pq = new PriorityQueue<>((a,b) -> Double.valueOf(b[0]).compareTo(Double.valueOf(a[0])));

        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));
        int n = arr.length, hi = n-1;
        for(int i = 0; i < n;i++) {
            pq.offer(
                    new double[]{
                            -1.0d*fraction(arr[i], arr[hi]),
                            i,
                            hi
                    }
            );
        }
        while(k-- > 1) {
            double[] curr = pq.poll();
            int num = (int)curr[1];
            int den = (int)curr[2];
            if(num < den-1) {
                pq.offer(
                        new double[] {
                                -1.0d*fraction(arr[num], arr[den-1]),
                                num,
                                den-1
                        }
                );
            }
        }
        double[] curr = pq.poll();
        int num = (int)curr[1], den = (int)curr[2];
        return new int[]{arr[num], arr[den]};
    }
    private double fraction(int i, int j) {
        return Double.valueOf(i)/Double.valueOf(j);
    }

    private void testSimilars() {
        String[] s1 = {"I","love","leetcode"};
        String[] s2 = {"I","love","onepiece"};

        List<List<String>> similarPairs = Arrays.asList(Arrays.asList("manga","onepiece"),
                Arrays.asList("platform","anime"),
                Arrays.asList("leetcode","platform"),
                Arrays.asList("anime","manga"));
        boolean res = areSentencesSimilarTwo(s1, s2, similarPairs);
    }

    public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        if(sentence1.length != sentence2.length) {
            return false;
        }
        Set<String> similars = similarPairs.stream().flatMap(e -> e.stream()).collect(Collectors.toSet());
        DSU dsu = new DSU(similars);
        for(List<String> pair: similarPairs) {
            dsu.join(pair.get(0), pair.get(1));
        }
        for(int i = 0; i < sentence1.length; i++) {
            String px = dsu.find(sentence1[i]), py = dsu.find(sentence2[i]);
            if(!px.equals(py)) {
                return false;
            }
        }
        return true;
    }
    private class DSU {
        Map<String, String> parents;
        Map<String, Integer> ranks;
        public DSU(Set<String> strs) {
            this.parents = new HashMap<>();
            this.ranks = new HashMap<>();
            for(String str: strs) {
                this.parents.put(str, str);
                this.ranks.put(str, 1);
            }
        }
        public String find(String x) {
            while(!x.equals(this.parents.getOrDefault(x, x))) {
                //parent[x] = parent[parent[x]]
                this.parents.put(x, this.parents.get(this.parents.get(x)));
                x = this.parents.get(x);
            }
            return this.parents.getOrDefault(x, x);
        }
        public void join(String x, String y) {
            String px = find(x), py = find(y);
            if(px.equals(py)) {
                return;
            }
            if(ranks.get(px) < ranks.get(py)) {
                this.parents.put(px, py);
            } else {
                this.parents.put(py, px);
                if(ranks.get(px) == ranks.get(py)) {
                    ranks.merge(px, 1, Integer::sum);
                }
            }
        }
    }

    private void testMessageParts() {
        //String[] res = splitMessage("this is really a very awesome message", 9);
        String[] res = splitMessage("boxpn", 5);
        for(String line: res) {
            System.out.printf("%s\n", line);
        }
    }

    public String[] splitMessage(String message, int limit) {
        int b = 1, totalLenA = 1;
        int msgLen = message.length();
        int bLen = len(b);
        while(totalLenA + msgLen + (3 + bLen)*b > limit*b) {
            if(3 + 2*bLen >= limit) {
                return new String[0];
            }
            b += 1;
            bLen = len(b);
            totalLenA += bLen;
        }
        String[] res = new String[b];
        int l = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < b; i++) {
            int currSegLen = limit - (3 + bLen + len(i+1));
            int r = Math.min(msgLen, l + currSegLen);
            sb.append(message.substring(l, r));
            sb.append(String.format("<%d/%d>", i+1, b));
            res[i] = sb.toString();
            l = r;
            sb.setLength(0);
        }
        return res;
    }

    private int len(int b) {
        return String.valueOf(b).length();
    }

    private void testJobMinDiff() {
        int[] jd = {7,1,7,1,7,1};
        int minDiff = minDifficulty(jd, 3);
        System.out.printf("Min difficulty: %d\n", minDiff);
    }


    public int minDifficulty(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if(n < d) {
            return -1;
        } else if(n == d) {
            //1 job each day
            return Arrays.stream(jobDifficulty).sum();
        }
        return minDifficulty(0, jobDifficulty, d);
    }
    private int minDifficulty(int ji, int[] jd, int remDays) {
        int n = jd.length;
        if(ji == n || remDays == 0) {
            return Arrays.stream(jd).sum();
        } else if(ji == n-1 && remDays == 1) {
            return jd[ji];
        } else if(remDays == 1) {
            return maxBeyondCurrIndex(ji, jd);
        }
        int minDiff = Integer.MAX_VALUE;
        int currDayMax = jd[ji];
        for(int i = ji; i <= n-remDays; i++) {
            currDayMax = Math.max(currDayMax, jd[i]);
            minDiff = Math.min(minDiff, currDayMax + minDifficulty(i+1, jd, remDays-1));
        }
        System.out.printf("(%d, %d) = %d\n", ji, remDays, minDiff);
        return minDiff;
    }

    private int maxBeyondCurrIndex(int idx, int[] jd) {
        int max = jd[idx];
        for(int i = idx + 1; i < jd.length; i++) {
            max = Math.max(max, jd[i]);
        }
        return max;
    }
    private void testFinfKthSmallest() {
        int[] nums = {3, 6, 9};
        long res = findKthSmallest(nums, 3);


    }

    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        List<Integer>[] comb = new ArrayList[n+1];
        for(int i = 0; i <= n; i++) {
            comb[i] = new ArrayList<>();
        }
        for(int m = 1; m <= (1 << n); m++) {
            int cnt  = 0, v =  1;
            for(int i = 0; i < n; i++) {
                if((m & (1<<i)) != 0) {
                    cnt += 1;
                    int g = BigInteger.valueOf(v).gcd(BigInteger.valueOf(coins[i])).intValue();
                    v *= coins[i]/g;
                }
            }
            comb[cnt].add(v);
        }
        return -1;
    }
}
