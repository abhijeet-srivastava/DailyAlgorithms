package com.google;

import java.util.*;
import java.util.stream.Collectors;

public class WordPredictor {
    String DELIMS = "[,\\s\\-:\\?]";

    Map<String, List<String>> predictor;
    public WordPredictor(String[] input) {
        Map<String, Map<String, Integer>> dictionary = new HashMap<>();
        for (String sentence : input) {
            String[] words =  sentence.toLowerCase().split(DELIMS);
            for(int i = 0; i < words.length-1; i++) {
                dictionary.computeIfAbsent(words[i], e -> new HashMap<>()).put(words[i+1],
                        dictionary.get(words[i]).getOrDefault(words[i+1], 0) + 1);
            }
        }
        this.predictor = new HashMap<>();
        for(String key : dictionary.keySet()) {
            Map<String, Integer> freqMap = dictionary.get(key);
            int maxFrequency = freqMap.values().stream().max(Comparator.comparingInt(Integer::intValue)).get();
            List<String>  maxFreqWords = freqMap.entrySet().stream().filter(e  -> e.getValue() == maxFrequency).map(Map.Entry::getKey).collect(Collectors.toList());
            this.predictor.put(key, maxFreqWords);
            System.out.printf("%s - [%s]\n", key, maxFreqWords.stream().collect(Collectors.joining(", ")));
        }
    }

    private List<String> predict(String word) throws Exception {
        if(!predictor.containsKey(word)) {
            throw new Exception("Not found");
        }
        return this.predictor.get(word);
    }

    public static void main(String[] args) {
        String[] input = {"Hi,X How-how are:any you?", "How do you do", "I am good in it", "I am a player", "I will win"};
        WordPredictor wp = new WordPredictor(input);
    }


}
