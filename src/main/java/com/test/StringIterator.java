package com.test;

public class StringIterator {
    String compressedString;
    int index;
    int currCount;
    char currChar;

    public StringIterator(String compressedString) {
        this.compressedString = compressedString;
        this.currCount = 0;
        this.index = 0;
    }

    public char next() {
        if(currCount > 0) {
            currCount -= 1;
            return currChar;
        }
        this.currChar = compressedString.charAt(index);
        this.index += 1;
        int counter = 0;
        while(this.index < compressedString.length()
                && Character.isDigit(compressedString.charAt(index))) {
            counter = counter*10 + (compressedString.charAt(index) - '0');
            this.index += 1;
        }
        currCount = counter-1;
        return currChar;
    }

    public boolean hasNext() {
        return currCount > 0 ||  this.index < compressedString.length();
    }
}
