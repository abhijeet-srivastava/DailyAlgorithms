package com.oracle.casb.expedia;

import java.util.Arrays;
import java.util.Iterator;

public class StringIterator implements Iterator<Character> {
    private int ptr = 0;
    private String[] characters;
    private int[] counts;

    public StringIterator(String compressed) {
        this.ptr = 0;
        counts = Arrays.stream(compressed.split("[a-z, A-Z]")).mapToInt(Integer::valueOf).toArray();
        characters = compressed.split("[0-9]");
    }

    @Override
    public boolean hasNext() {
        return ptr < characters.length;
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            return ' ';
        }
        counts[ptr] -= 1;
        char res = characters[ptr].charAt(0);
        if (counts[ptr]  == 0) {
            ptr += 1;
        }
        return Character.valueOf(res);
    }
}
