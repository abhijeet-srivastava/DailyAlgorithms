package com.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogSystem {
    TreeMap<String, Set<Integer>> logMessages;

    Map<String, Integer> gran_index = Map.of(
            "Year", 0, "Month", 1, "Day", 2, "Hour", 3, "Minute", 4, "Second", 5
    );
    String[][] period_start = {{"2000", "2000"}, {"01", "12"}, {"01", "31"}, {"00", "23"}, {"00", "59"}, {"00", "59"}};
    public LogSystem() {
        logMessages = new TreeMap<>();
    }

    public void put(int id, String timestamp) {
        logMessages.computeIfAbsent(timestamp, e -> new HashSet<>()).add(id);
    }

    public List<Integer> retrieve(String start, String end, String granularity) {
        int idx = gran_index.get(granularity);
        start = trimTimeStampForGran(start, granularity, 0);
        end = trimTimeStampForGran(end, granularity, 1);
        List<Integer> result = new ArrayList<>();
        for(var t: logMessages.entrySet()) {
            if(t.getKey().compareTo(start) >= 0
                    && t.getKey().compareTo(end) <= 0) {
                result.addAll(t.getValue());
            }
        }
        return result;
    }
    private String trimTimeStampForGran(String timestamp, String gran, int boundry) {
        String[] tsArr = timestamp.split(":");
        int index = gran_index.get(gran);
        for(int i = index+1; i < tsArr.length; i++) {
            tsArr[i] = period_start[i][boundry];
        }
        return Stream.of(tsArr).collect(Collectors.joining(":"));
    }
}
