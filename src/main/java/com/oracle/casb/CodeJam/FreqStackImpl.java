package com.oracle.casb.CodeJam;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created By : abhijsri
 * Date  : 28/08/18
 **/
public class FreqStackImpl implements FreqStack {

    Map<Integer, Integer> frequencyMap;
    Map<Integer, Stack<Integer>> group;

    int maxFreq;

    public FreqStackImpl() {
        frequencyMap = new HashMap<>();
        group = new HashMap<>();
        maxFreq = 0;
    }

    @Override
    public void push(Integer element) {
        int frequency = frequencyMap.getOrDefault(element, 0) + 1;
        frequencyMap.put(element, frequency);
        group.computeIfAbsent(frequency, z-> new Stack()).push(element);
        if (frequency > maxFreq) {
            maxFreq = frequency;
        }
    }

    @Override
    public Integer pop() {
        int element = group.get(maxFreq).pop();
        frequencyMap.put(element, frequencyMap.get(element) - 1);
        if (group.get(maxFreq).isEmpty()) {
            maxFreq -= 1;
        }
        return Integer.valueOf(element);
    }
}
