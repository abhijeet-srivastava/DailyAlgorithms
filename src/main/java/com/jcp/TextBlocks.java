package com.jcp;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;


public class TextBlocks {

    public static void main(String[] args) {
        TextBlocks tb = new TextBlocks();
        tb.testPattern();
    }

    public void testPattern() {
        String nameAndAddress
                = "Mark Janson;243 West Main St;Louisville;40202;USA";
        Pattern pattern = Pattern.compile("(?<name>[ a-zA-Z]+);(?<address>[ 0-9a-zA-Z]+);(?<city>[ a-zA-Z]+);(?<zip>[\\d]+);(?<country>[ a-zA-Z]+)$");
        Matcher matcher = pattern.matcher(nameAndAddress);
        if (matcher.matches()) {
            String name = matcher.group("name");
            System.out.printf("name:[%s]\n", name);
            String address = matcher.group("address");
            System.out.printf("address:[%s]\n", address);
            String city = matcher.group("city");
            System.out.printf("city:[%s]\n", city);
            String zip = matcher.group("zip");
            System.out.printf("zip:[%s]\n", zip);
            String country = matcher.group("country");
            System.out.printf("country:[%s]\n", country);
        }
    }

    public static boolean isIsomorphic(String s1, String s2) {
        if (s1 == null || s2 == null
                || s1.length() != s2.length()) {
            return false;
        }
        // step 2
        Map<Character, Character> map = new HashMap<>(); // step 3(8)
        for (int i = 0; i < s1.length(); i++) {
            char chs1 = s1.charAt(i);
            char chs2 = s2.charAt(i);
// step 4
            if (map.containsKey(chs1)) { // step 5
                if (map.get(chs1) != chs2) {
                    return false;
                }
            } else {
// step 6
                if (map.containsValue(chs2)) {
                    return false;
                }
// step 7
                map.put(chs1, chs2);
            }
        }
// step 9
        return true;
    }
}
