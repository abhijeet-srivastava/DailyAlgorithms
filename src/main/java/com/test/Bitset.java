package com.test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bitset {
    //110101 => 001010
    //110001 => 001110
    int size;
    Set<Integer> setIndices;

    boolean isFlipped;

    public Bitset(int size) {
        this.size = size;
        this.setIndices = new HashSet<>();
        this.isFlipped = false;
    }

    public void fix(int idx) {
        if(this.isFlipped) {
            setIndices.remove(idx);
        } else {
            setIndices.add(idx);
        }
    }

    public void unfix(int idx) {
        if(this.isFlipped) {
            setIndices.add(idx);
        } else {
            setIndices.remove(idx);
        }
    }

    public void flip() {
        this.isFlipped ^= true;
        /*Set<Integer> allIndices = IntStream.range(0, size).boxed().collect(Collectors.toSet());
        allIndices.removeAll(setIndices);
        this.setIndices = allIndices;*/
    }

    public boolean all() {
        return this.isFlipped ? this.setIndices.isEmpty() : this.setIndices.size() == this.size;
    }

    public boolean one() {
        return this.isFlipped ? this.setIndices.size() < this.size :  !this.setIndices.isEmpty();
    }

    public int count() {
        return this.isFlipped ? this.size - this.setIndices.size() : this.setIndices.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            if(this.setIndices.contains(i)) {
                sb.append(isFlipped ? '0': '1');
            } else {
                sb.append(isFlipped ? '1': '0');
            }
        }
        return sb.toString();
    }
}
