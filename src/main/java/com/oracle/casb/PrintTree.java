package com.oracle.casb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 26/04/18
 **/
public class PrintTree {
    public static void main(String[] args) {
        PrintTree pt = new PrintTree();
        pt.testPrint();
    }

    private void testPrint() {
        Map<String, Object> infoMap = new HashMap<>();
        String fileName = "PrintInput1.txt";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(e -> populateMap(infoMap, e));
            System.out.println("Map Size = " + infoMap.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateMap(Map<String,Object> infoMap, String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }
        int index = line.indexOf(",");
        String current = line, rest = null;
        if (index > 0) {
            current = line.substring(0, index);
            rest = line.substring(index+1);
        }
        Map<String, Object> childMap;
        if (infoMap.containsKey(current)) {
            childMap = (Map<String, Object>) infoMap.get(current);
        } else {
            childMap =  new HashMap<>();
            infoMap.put(current, childMap);
        }
        populateMap(childMap, rest);
    }

}
