package com.oracle.casb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By : abhijsri
 * Date  : 07/11/18
 **/
public class ProcessTimeLine {

    ProcessTimeLine INSTANCE;

    private Map<Integer, List<Integer>> processTimeMap;

    private Integer upperBound;

    private ProcessTimeLine(Integer[] processes) {
        processTimeMap = new ConcurrentHashMap<>();
        upperBound = getLcm(processes);
        for (int i = 0; i < processes.length; i++) {
            int pi = processes[i];
            if (processTimeMap.containsKey(pi)) {
                continue;
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(pi);
                processTimeMap.put(pi, list);
            }
            Map<Integer, List<Integer>> tempMap = new HashMap<>();
            for (Map.Entry<Integer, List<Integer>> entry : processTimeMap.entrySet()) {
                int val = pi * entry.getKey();
                List<Integer> list = null;
                if (processTimeMap.containsKey(val)) {
                    list = new ArrayList<>(processTimeMap.get(val));
                } else {
                    list = new ArrayList<>();
                }
                list.add(pi);
                list.add(entry.getKey());
                tempMap.put(val, list);
            }
            processTimeMap.putAll(tempMap);
        }
    }

    private List<Integer> getRunningProcess(int k) {
         int rem = k % upperBound;
         return processTimeMap.get(rem);
    }

    private void addProcess(int p) {
        upperBound = getLcm(new Integer[]{upperBound, p});

        Map<Integer, List<Integer>> tempMap = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : processTimeMap.entrySet()) {
            int val = p * entry.getKey();
            List<Integer> list = null;
            if (processTimeMap.containsKey(val)) {
                list = new ArrayList<>(processTimeMap.get(val));
            } else {
                list = new ArrayList<>();
            }
            list.add(p);
            list.add(entry.getKey());
            tempMap.put(val, list);
        }
        processTimeMap.putAll(tempMap);
    }


    private Integer getLcm(Integer[] processes) {
        return 0;
    }
}
