package com;

public class Application {


    public static void main(String[] args) {
        Application app = new Application();
        app.validatePallindorm();
    }

    private void validatePallindorm() {
        String str = "aababc";
        boolean res = canFormPallindrom(str);
        System.out.printf("%s is Pallindrom = %b\n", str, res);
    }


    private boolean canFormPallindrom(String str) {
        if(str == null) {
            return false;
        } else if(str.length() == 1) {
            return true;
        }
        str = str.toLowerCase();
        int[] count = new int[26];
        for(char ch: str.toCharArray()) {
            count[ch - 'a'] += 1;
        }
        // Odd length:  (1,3,5) => rest of characters should have even frequency
        // Even Length: evry character frequency should be divisible by 2
        int characterWithOddFrequency = 0;
        for(int frequency: count) {
            if(frequency%2 == 1) {
                characterWithOddFrequency += 1;
            }
        }
        return characterWithOddFrequency <= 1;
    }
}
