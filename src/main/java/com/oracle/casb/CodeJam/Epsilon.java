package com.oracle.casb.CodeJam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By : abhijsri
 * Date  : 09/09/18
 **/
public class Epsilon {

    public static void main(String[] args) {
        Epsilon es = new Epsilon();
        //es.testArrayRotate();
        es.testSpell();
    }

    private void testSpell() {
        String[] strArr = {"discount", "weak", "goalkeeper"};
        for (String str : strArr) {
            System.out.println(testSpell(str));
        }
    }

    private void testArrayRotate() {
        int numStatement = 5;
        int[] array = {2,1,3};
        int[][] testCase = {{3,2}, {1,1}, {3,2}, {2,2}, {3,2}};
        int count = 0;
        int size = array.length;
        for (int j = 0; j < numStatement; j++) {
            int a = testCase[j][0];
            int b = testCase[j][1];
            if (a == 1) {
                count -= b;
            } else if (a == 2) {
                count += b;
            } else if (a == 3){
                array = rotate(array, count);
                System.out.printf("%d\n", array[b%size]);
                count = 0;
            }
        }

    }

    private int[] rotate(int[] array, int count) {
        if (count == 0) {
            return array;
        }
        int size = array.length;
        int val = Math.abs(count);
        int rotate = val % size;
        if (count < 0) {
            rotate *= -1;
        }
        int[] newArr = new int[size];
        for (int i = 0 ; i < size; i++) {
            int index = i + rotate;
            if (index < 0) {
                index += size;
            }
            newArr[index%size] = array[i];
        }
        return newArr;
    }

    private String testSpell(String spell) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 1);
        map.put('e', 2);
        map.put('i', 3);
        map.put('o', 4);
        map.put('u', 5);
        boolean start = true;
        boolean asc = false;
        boolean desc = false;
        int latest = -1;
        int index = -1;
        for (int i = 0; i < spell.length(); i++) {
            char ch = spell.charAt(i);
            if (map.containsKey(ch)) {
                if (start) {
                    start = false;
                    latest = map.get(ch);
                    index = i;
                }else {
                   int curr =  map.get(ch);
                   if (!asc && !desc) {
                       if (curr > latest) {
                           asc = true;
                       } else if (curr < latest) {
                           desc = true;
                       }
                       latest = curr;
                   } else {
                       if (asc && curr < latest) {
                           return "Bad";
                       } else if (asc && curr >= latest) {
                           latest = curr;
                       } else if (desc && curr <= latest) {
                           latest = curr;
                       } else if (desc && curr > latest) {
                           return "Bad";
                       }
                   }
                }
            }
        }
        if (asc) {
            return "Good";
        } else if (desc) {
            return "Worst";
        }
        return "";
    }


    private int countNum (String s) {
        char[] arr = s.toCharArray();
        int count = 0;
        for (int i = 0; i <s.length(); i++) {
            for (int j = i; j< s.length() ; j++) {
                if (isVowel(arr[i])) {
                    count += 1;
                }
                if (isVowel(arr[j])) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'A' ||  c == 'e' || c == 'E' || c == 'i' || c== 'I' || c == 'o' || c == 'O' || c == 'u' || c == 'U';
    }
}
