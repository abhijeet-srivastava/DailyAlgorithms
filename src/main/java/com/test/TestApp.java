package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestApp {


    /**
     *
     * Common Prime Factors
     * Given a list of input integers, return a list of common prime numbers to all values provided.
     *
     * Example
     * Input
     * Output
     * A
     * [8385, 5289]
     * [3, 43]
     * B
     * [6, 35, 10, 15]
     * [ ]
     * C
     * [2386685, 2966845]
     * [5, 7, 37]
     * D
     * [2310, 67830, 52890]
     * [2, 3, 5]
     * E
     * [6670, 253115, 24541, 20999, 2400533]
     * [23]
     * F
     * [39270, 6510, 28980, 16590, 68250]
     * [2, 3, 5, 7]
     * @param args
     */

    /**
     * 1. For each of the number in list - Find its prime factorizations
     * 2. Find common elements amongs those prime factos list
     *
     * 6 - 2, 3
     * 35 - 5, 7
     * 10 - 2, 5
     * 15 - 3, 5
     */
    public static void main(String[] args) {
        TestApp ta = new TestApp();
        //ta.validatePrimeGen();
        System.out.printf("Kth : %s\n", ta.smallestPalindrome("xxnfnxx", 3));
        //System.out.printf("Kth : %s\n", ta.smallestPalindrome("baab", 1));
        //ta.validateCommonPrimes();
    }
    //!3/!2 = 3
    // nxx - Total 6
    // {1,2}{3,4}{5,6}
    //ab - Total = 2
    //abc -> acb -> bac -> bca -> cab -> cba; fact = 2
    // 4 ->

    public String smallestPalindrome(String s, int k) {
        int len = s.length();
        if(len == 1) {
            return k == 1 ? s:"";
        }
        int half = (len>> 1);
        String halfStr = s.substring(0, half);
        char[] arr = halfStr.toCharArray();
        Arrays.sort(arr);
        int fact = 1;
        List<Character> numbers = new ArrayList<>(half);
        for(char ch: arr) {
            numbers.add(ch);
        }
        for (int i = 2; i < half; i++) {
            fact = fact * i;
        }
        if(k > fact*half) {
            return "";
        }
        k -= 1;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int index = k/fact;
            sb.append(numbers.get(index));
            numbers.remove(index);
            if (numbers.size() == 0) {
                break;
            }

            k = k % fact;
            fact = fact / numbers.size();
        }
        System.out.printf("Half: %s\n", sb.toString());
        StringBuilder res = new StringBuilder();
        res.append(sb.toString());
        if(len%2 == 1) {
            res.append(s.charAt(half));
        }
        sb.reverse();
        res.append(sb.toString());
        return res.toString();
    }

    private void validateCommonPrimes() {
        int[] numbers = {6670, 253115, 24541, 20999, 2400533};
        List<Integer> commonPrimes = commonPrimeFactors(numbers);
        System.out.printf("Res: [%s]\n", commonPrimes.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void validatePrimeGen() {
        List<Integer> primes = generatePrimes(2400533);
        System.out.printf("Primes: [%s]\n", primes.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        Set<Integer> primeFactors = getPrimeFactors(2400533, primes);
        System.out.printf("Prime Factors {%s}\n", primeFactors.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    private List<Integer> commonPrimeFactors(int[] numbers) {
        int len = numbers.length;
        int max = Arrays.stream(numbers).max().getAsInt();
        List<Integer> allPrimes = generatePrimes(max);
        Set<Integer> primeFactorPrev = getPrimeFactors(numbers[0], allPrimes);
        for(int idx = 1; idx < len; idx++) {
            Set<Integer> primeFactor = getPrimeFactors(numbers[idx], allPrimes);
            primeFactorPrev.retainAll(primeFactor);
            if(primeFactorPrev.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
        }
        return primeFactorPrev.stream().toList();
    }

    private List<Integer> generatePrimes(int max) {
        boolean[] primes = new boolean[max+1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        for(long i = 2; i <= max; i++) {
            if(!primes[(int)i]) {
                continue;
            }
            for(long j = i*i; j <= max; j += i) {
                primes[(int)j] = false;
            }
        }
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i <= max; i++) {
            if(primes[i]) {
                result.add(i);
            }
        }
        return result;
    }

    private Set<Integer> getPrimeFactors(int number, List<Integer> allPrimes) {
        Set<Integer> result = new HashSet<>();
        for(int idx = 0; allPrimes.get(idx)*allPrimes.get(idx) <= number; idx++) {
            if(number%allPrimes.get(idx) == 0) {
                result.add(allPrimes.get(idx));
            }
        }
        return result;
    }
}
