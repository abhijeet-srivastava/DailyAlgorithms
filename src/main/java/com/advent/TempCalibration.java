package com.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class TempCalibration {
    public static void main(String[] args) {
        TempCalibration tc = new TempCalibration();
        //tc.testTempCalibration();
        //tc.testLargest();
        tc.testMinStickers();
    }

    private void testMinStickers() {
        String[] stickers = {"with","example","science"};
        String target = "thehat";
        int min = minStickers(stickers, target);
        System.out.printf("Min count: %d\n", min);
    }

    private void testLargest() {
        int[] nums = {10,2,9,39,17};
        String val = largestNumber(nums);
        System.out.printf("num %s\n", val);
    }

    public int minStickers(String[] stickers, String target) {
        int n = target.length();
        int size = 1 << n;
        int[] DP = new int[size];
        Arrays.fill(DP, -1);
        DP[0] = 0;
        for(int state = 0; state < size; state++) {
            if(DP[state] == -1) {
                continue;
            }
            for(String sticker: stickers) {
                int stateAfterApplySticker = state;
                for(char ch: sticker.toCharArray()) {
                    for(int i = 0; i < n; i++) {
                        if(((stateAfterApplySticker >> i) & 1) == 1) {
                            continue;
                        }
                        if(target.charAt(i) == ch) {
                            stateAfterApplySticker |= (1 << i);
                            break;
                        }
                    }
                }
                if (DP[stateAfterApplySticker] == -1 || DP[stateAfterApplySticker] > 1 + DP[state]) {
                    DP[stateAfterApplySticker] = 1 + DP[state];
                }
            }
        }
        return DP[size-1];
    }
    private int createMask(String s) {
        int mask = 0;
        for(char ch: s.toCharArray()) {
            int idx = ch-'a';
            mask |= (1 << idx);
        }
        return mask;
    }
    public String largestNumber(int[] nums) {
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        Collections.sort(list, (a, b) -> compare(a, b));
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            if(i > 0 && list.get(i) == 0 && list.get(i-1) == 0) {
                continue;
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }
    private int compare(int a, int b) {
        if(a == b) {
            return 0;
        }
        if(a == 0 || b == 0) {
            return b-a;
        }
        long da = countDigits(a), db = countDigits(b);
        long num1 = a*db + b;
        long num2 = b*da + a;
        return Long.compare(num2, num1);
    }

    private long countDigits(int a) {
        long res = 1l;
        while(a > 0) {
            a /= 10;
            res *= 10l;
        }
        return res;
    }

    private void testTempCalibration() {
        Map<String, Integer> nums = Map.of(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six", 6,
                "seven", 7,
                "eight", 8,
                "nine", 9
        );
        try(Scanner scanner = new Scanner(new File("src/main/resources/adven-of-code-temp.txt"))) {
            long sum = 0l;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                int firstDigit = 0, lastDigit = 0;
                boolean found = false;
                for(int i = 0; i < line.length(); i++) {
                    if(Character.isDigit(line.charAt(i))) {
                        firstDigit = Character.digit(line.charAt(i), 10);
                        break;
                    }
                    for(var t: nums.entrySet()) {
                        if(i >= t.getKey().length()-1
                                && line.substring(i-t.getKey().length()+1, i+1).equals(t.getKey())) {
                            firstDigit = t.getValue();
                            found = true;
                            break;
                        }
                    }
                    if(found) {
                        break;
                    }
                }
                found = false;
                int len = line.length();
                for(int i = line.length()-1; i >= 0; i--) {
                    if(Character.isDigit(line.charAt(i))) {
                        lastDigit = Character.digit(line.charAt(i), 10);
                        break;
                    }
                    for(var t: nums.entrySet()) {
                        if((len-i) >= t.getKey().length()
                                && line.substring(i, i+t.getKey().length()).equals(t.getKey())) {
                            lastDigit = t.getValue();
                            found = true;
                            break;
                        }
                    }
                    if(found) {
                        break;
                    }
                }
                System.out.printf("Temp: %s first: %d, last: %d\n", line, firstDigit, lastDigit);
                sum += firstDigit*10l + lastDigit;
            }
            System.out.printf("Sum: %d\n", sum);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
