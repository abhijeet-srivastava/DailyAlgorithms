package com.oracle.casb.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 23/07/18
 **/
public class StandardInputReader {

    public static List<String> readLines() throws IOException {
        List<String> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line = br.readLine();
            while (line != null) {
                input.add(line);
                line = br.readLine();
            }

        }
        return input;
    }

    public static Integer[] readInIntarray(String str) {
        return Stream.of(str.split("\\s")).filter(e -> !e.isEmpty()).map(Integer::valueOf).toArray(Integer[]::new);
    }
}
