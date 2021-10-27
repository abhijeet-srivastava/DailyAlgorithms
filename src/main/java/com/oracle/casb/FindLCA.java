package com.oracle.casb;

import com.google.common.collect.ImmutableList;
import com.oracle.casb.common.StandardInputReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created By : abhijsri
 * Date  : 23/07/18
 **/
public class FindLCA {
    public static void main(String[] args) throws IOException {
        //List<String> input = StandardInputReader.readLines();
        List<String> input = ImmutableList.of("1", "16", "0 8 4 12 2 10 6 14 1 9 5 13 3 11 7 15");
        int T = Integer.valueOf(input.get(0));
        int index = 0;
        while (index < input.size()-1) {
            int size = Integer.valueOf(input.get(++index));
            Integer[] array = StandardInputReader.readInIntarray(input.get(++index));
            int lca = findLca(array);
            System.out.printf("%d%c", lca, '\n');
        }
    }

    private static int findLca(Integer[] array) {
        int[] lis = new int[array.length];
        Arrays.fill(lis, 1);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]
                        && lis[i] < (1 + lis[j])) {
                    lis[i] =  1 + lis[j];
                }
            }
        }
        return Arrays.stream(lis).max().orElse(0);
    }
}
