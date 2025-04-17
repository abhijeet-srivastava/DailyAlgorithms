package com.assignemnts;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class NumberContainers {
    Map<Integer, Integer> indexToNumber;
    Map<Integer, TreeSet<Integer>> numberToIndices;
    public NumberContainers() {
        this.indexToNumber = new HashMap<>();
        this.numberToIndices = new HashMap<>();
    }

    public void change(int index, int number) {
        if(indexToNumber.containsKey(index)) {
            int oldNumber = indexToNumber.get(index);
            System.out.printf("Removing  %d from index: %d\n", oldNumber, index);
            // Current index need to be removed from oldNumber indices
            numberToIndices.get(oldNumber).remove(index);
        }
        System.out.printf("Adding index- %d for number : %d\n", index, number);
        // indexToNumber.put(index, number);
        numberToIndices.computeIfAbsent(number, e -> new TreeSet<>()).add(index);
        System.out.printf("[%d] = %d\n", index, number);
        indexToNumber.put(index, number);
    }

    public int find(int number) {
        if(numberToIndices.containsKey(number)
                && !numberToIndices.get(number).isEmpty()) {
            numberToIndices.get(number).first();
        }
        return -1;
    }

    public static void main(String[] args) {
        NumberContainers nc = new NumberContainers();
        nc.change(2, 10);
        nc.change(1, 10);
        nc.change(3, 10);
        nc.change(5, 10);
        int li = nc.find(10);
        System.out.printf("Li for %d = %d\n", 10, li);
    }
}
