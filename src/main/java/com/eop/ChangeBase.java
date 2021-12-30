package com.eop;

public class ChangeBase {
    public static void main(String[] args) {
        ChangeBase cb = new ChangeBase();
        cb.testChangeBase();
    }

    private void testChangeBase() {
        String number = "c";
        String inBase8 = convertBase(number, 10, 16);
        System.out.printf("%s\n", inBase8);
    }

    private String convertBase(String number, int b1, int b2){
        boolean isNegative = number.charAt(0) == '-';
        int numAsInt = number.substring(isNegative ? 1 : 0)
                .chars()
                .reduce(0, (x, c) -> x*b1 +
                        (Character.isDigit(c) ? c - '0' : c - 'A' + 10));
        return isNegative ? "-" : ""
                + (numAsInt == 0 ? "0" : constructFromBase(numAsInt, b2));
    }

    private String constructFromBase(int num, int base){
        return num == 0 ? ""
                : constructFromBase(num/base, base)
                +  (char) (num % base > 10
                ? 'A' + num % base  - 10
                : '0' +    num%base);
    }
}
