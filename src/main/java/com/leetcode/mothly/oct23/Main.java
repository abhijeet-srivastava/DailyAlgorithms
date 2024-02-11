package com.leetcode.mothly.oct23;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        //main.testReverse();
        main.testfindMaximumNumber();
    }

    private void testfindMaximumNumber() {
        long num = findMaximumNumber(57, 4);
        System.out.printf("num: %d\n", num);
    }

    public long findMaximumNumber(long k, int x) {
        long low = 1L, high = (long)Math.pow(10,15);//1_000_000_000_000_000;
        long res = low;
        while(low <= high) {
            long mid = low + (high-low)/2;
            long count = calculatePrice(mid, x);
            System.out.printf("Price of:%d = %d\n",mid, count);
            if(count <= k) {
                res = mid;
                low = mid+1;
            } else {
                high = mid-1;
            }
        }
        return res;
    }
    private long calculatePrice(long num, int x) {
        int msb = countMsb(num);
        num += 1;
        long price = 0l;
        for(int i = msb; i > 0; i--) {
            if(i%x == 0) {
                price +=  (num / powerOf2(i)) * (powerOf2(i - 1)) + Math.max(0L, (num % powerOf2(i)) - powerOf2(i - 1));
            }
        }
        return price;
    }
    private long powerOf2(int i) {
        return 1L << i;
    }

    private int countMsb(long num) {
        int msb = 0;
        while(num > 0) {
            num >>= 1;
            msb += 1;
        }
        return msb;
    }

    private void testReverse() {
        String str = "Let's take LeetCode contest";
        System.out.printf("Reverse: %s\n", reverseWords(str));
    }

    public String reverseWords(String s) {
        int n = s.length(), l = 0, r = 0;
        char[] arr = s.toCharArray();
        while(r < n) {
            while(l < n && Character.isWhitespace(arr[l])) {
                l += 1;
            }
            r = l;
            while(r < n && !Character.isWhitespace(arr[r])) {
                r += 1;
            }
            reverse(arr, l, r-1);
            l = r;
        }
        return new String(arr);
    }
    private void reverse(char[] arr, int l, int r) {
        while(l < r) {
            char tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l += 1;
            r -= 1;
        }
    }
}
