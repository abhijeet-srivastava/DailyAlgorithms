package com.leetcode.mothly.feb24;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class LeetCodeFeb {
    public static void main(String[] args) {
        LeetCodeFeb lcf = new LeetCodeFeb();
        //lcf.testCanTraverseAllPairs();
        //lcf.testLargestSquareSize();
        //lcf.testDecode();
        lcf.testCombCount();
    }

    private void testCombCount() {
        int count = numberOfCombinations("327156");
        System.out.printf("Count: %d\n", count);
    }

    int MOD = 1_000_000_007;
    public int numberOfCombinations(String num) {
        if(num == null || num.length() == 0 || num.charAt(0) == '0') {
            return 0;
        }
        return numberOfCombinations(0, 0, num);
    }

    private int numberOfCombinations(int index, int prevVal, String num) {
        if(index == num.length()) {
            return 1;
        }
        int i = index+1;
        int val = Integer.parseInt(num.substring(index, i));
        while(i < num.length() && val < prevVal) {
            i += 1;
            val = Integer.parseInt(num.substring(index, i));
        }
        if(i == num.length()) {
            return val > prevVal ? 1 : 0;
        }
        int res = 0;
        for(; i < num.length(); i++) {
            val = Integer.parseInt(num.substring(index, i));
            res  = (res+numberOfCombinations(i,val, num))%MOD;
        }
        return res;
    }


    private void testDecode() {
        int count = numDecodings2("*1");
        System.out.printf("Count: %d\n", count);
    }

    public int numDecodings2(String s) {
        int MOD = 1_000_000_007;
        int n = s.length();
        if(n == 0 || s.charAt(0) == '0') {
            return 0;
        }
        int d1 = 1, d2 = s.charAt(0) == '*' ? 9:1;
        for(int i = 2; i <= n; i++) {
            int di = 0;
            char curr = s.charAt(i-1);
            if(curr >= '1' && curr <= '9') {
                di = d2;
            } else if(curr == '*') {
                di = (d2*9)%MOD;
            }
            char last = s.charAt(i-2);
            if((last == '1' && (curr >= '0' && curr <= '9'))
                    ||  (last == '2' && (curr >= '0' && curr <= '6'))) {
                di = (di+d1)%MOD;
            } else if(last == '1' && curr == '*') {
                di = (di + (d1*9)%MOD)%MOD;
            } else if(last == '2' && curr == '*') {
                di = (di + (d1*6)%MOD)%MOD;
            } else if(last == '*' && curr >= '1' && curr <= '9') {
                di = (di + (d1*15)%MOD)%MOD;
            }else if(last == '*' && curr == '*') {
                di = (di+ (d1*15)%MOD)%MOD;
            }
            d1 = d2;
            d2 = di;
        }
        return d2;
    }

    public int numDecodings(String s) {
        if(s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        int n = s.length();
        int[] DP = new int[n+1];
        DP[0] = 1;
        DP[1] = 1;
        for(int i = 2; i <= n; i++) {
            int di = 0;
            if(s.charAt(i-1) != 0) {
                di = DP[i-1];
            }
            if(s.charAt(i-2) != '0'
                    && Integer.parseInt(s.substring(i-2, i)) <= 26) {
                di += DP[i-2];
            }
            DP[i] = di;
        }
        return DP[n];
    }

    private void testLargestSquareSize() {
        int[][] bl = {{4,6},{6,2},{3,3}};
        int[][] tr = {{10,8},{9,4},{7,5}};
        long maxSize = largestSquareArea(bl, tr);
    }

    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;
        Rectangle[] rectangles = new Rectangle[n];
        for(int i = 0; i < n; i++) {
            rectangles[i] = new Rectangle(bottomLeft[i], topRight[i]);
        }
        //Arrays.sort(rectangles);
        long maxSize = 0;
        for(int i = 0; i < n-1; i++) {
            for(int j = i+1; j < n; j++) {
                if(!rectangles[i].intersects(rectangles[j])) {
                    continue;
                }
                Rectangle intersection = rectangles[i].intersection(rectangles[j]);
                int largestSqr = intersection.longestSquareSize();
                if(maxSize < largestSqr) {
                    maxSize = largestSqr;
                }
            }
        }
        /*Rectangle prev = rectangles[0];
        for(int i = 1; i < n; i++) {
            Rectangle curr = rectangles[i];
            if(curr.intersects(prev)) {
                Rectangle intersection = curr.intersection(prev);
                int largestSqr = intersection.longestSquareSize();
                if(maxSize < largestSqr) {
                    maxSize = largestSqr;
                }
            }
            prev = curr;
        }*/
        return maxSize*maxSize;
    }
    public class Rectangle {
        int xs;
        int xe;
        int ys;
        int ye;
        public Rectangle(int[] bl, int[] tr) {
            this.xs = bl[0];
            this.xe = tr[0];
            this.ys = bl[1];
            this.ye = tr[1];
        }
        public Rectangle(int xsi, int xei, int ysi, int yei) {
            this.xs = xsi;
            this.xe = xei;
            this.ys = ysi;
            this.ye = yei;
        }
        public boolean intersects(Rectangle other) {
            return Math.max(this.xs, other.xs) < Math.min(this.xe, other.xe)
                    && Math.max(this.ys, other.ys) < Math.min(this.ye, other.ye);
        }
        public Rectangle intersection(Rectangle other) {
            if(!this.intersects(other)) {
                return null;
            }
            int xsi = Math.max(this.xs, other.xs);
            int xei = Math.min(this.xe, other.xe);
            int ysi = Math.max(this.ys, other.ys);
            int yei = Math.min(this.ye, other.ye);
            return new Rectangle(xsi, xei, ysi, yei);
        }
        public int longestSquareSize() {
            return Math.min(xe-xs, ye-ys);
        }
    }

    private void testCanTraverseAllPairs() {
        //int[] nums = {84,42,70,34,30,30,70,15,22,44,84,84,42,70,78,90,60,98,70,60,15,90,10,44,90,35};
        //int[] nums = {2,3,6};
        //int[] nums = {4,3,12,8};
        //int[] nums = {10007,20014};
        int[] nums = {51,46,4,3,48,9,49,7,54};
        //int[] nums = {3, 9, 5};
        boolean canTraverse = canTraverseAllPairs(nums);
        System.out.printf("Can Travers: %b\n", canTraverse);
    }

    
    
    public boolean canTraverseAllPairs(int[] nums) {
        if(nums.length == 1) {
            return true;
        }
        DSU dsu = new DSU(nums.length);
        BitSet primes = createPrimeSet(5000);
        Map<Integer, Set<Integer>> numberFactors = new HashMap<>();
        Map<Integer, Set<Integer>> factorToIndices = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(num ==1) {
                return false;
            }
            Set<Integer> factors
                    = numberFactors.computeIfAbsent(num,
                    e -> primeFactors(num, primes));
            for(int fact: factors) {
                Set<Integer> indices
                        = factorToIndices.get(fact);
                if(indices == null) {
                    indices = new HashSet<>();
                    indices.add(i);
                    factorToIndices.put(fact,  indices);
                } else {
                    int j = indices.iterator().next();
                    dsu.join(i, j);
                    indices.add(i);
                }
            }
        }
        return dsu.count == 1;
    }
    private Set<Integer> findFactors(int num, BitSet primes) {
        Set<Integer> factors = new HashSet<>();
        for (int i = primes.nextSetBit(2); i >= 0; i = primes.nextSetBit(i+1)) {
            if(i >= num) {
                break;
            }
            if(num%i == 0){
                factors.add(i);
                factors.add(num/i);
            }
        }
        if(factors.isEmpty()) {
            factors.add(num);
        }
        return factors;
    }
    private Set<Integer> primeFactors(int number, BitSet primes) {
        Set<Integer> factors = new HashSet<>();
        int i = primes.nextSetBit(1);
        while(i >= 0 && i*i <= number) {
            if(number%i > 0) {
                i = primes.nextSetBit(i+1);
                continue;
            }
            factors.add(i);
            while(number%i == 0) {
                number /= i;
            }
            i = primes.nextSetBit(i+1);
        }
        if(number > 1) {
            factors.add(number);
        }
        /*if(factors.isEmpty()) {
            factors.add(number);
        }*/
        return factors;
    }
    private BitSet createPrimeSet(int limit) {
        BitSet primes = new BitSet(limit+1);
        primes.set(2, limit);
        for(int i = 2; i*i < limit; i++) {
            if(!primes.get(i)) {
                continue;
            }
            for(int p = i << 1; p < limit; p += i) {
                primes.clear(p);
            }
        }
        return primes;
    }
    private boolean[]  generatePrimes(int n) {
        boolean[] primes = new boolean[n];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        for(int i = 2; i*i < n; i++) {
            if(!primes[i]) {
                continue;
            }
            for(int p = i*i; p < n; p += i){
                primes[p] = false;
            }
        }
        return primes;
    }
    class DSU {
        int[] parent;
        int[] rank;
        int count;

        private DSU(int n) {
            this.count = n;
            this.parent = new int[this.count];
            this.rank = new int[this.count];
            for(int i = 0; i < n; i++) {
                this.parent[i] = i;
                this.rank[i] = 1;
            }
        }
        private int find(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return parent[x];
        }
        private void join(int x, int y) {
            int px = find(x), py = find(y);
            if(px == py) {
                return;
            }
            if(rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if(rank[px] == rank[py]) {
                    rank[px] += 1;
                }
            }
            this.count -= 1;
        }

    }
    private int gcd(int a, int b) {
        if(a == b) {
            return a;
        }
        if(a == 0)  {
            return b;
        }
        return gcd(b%a, a);
    }
}
