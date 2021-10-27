package com.oracle.casb.common;

import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 18/06/18
 **/
public class ArrayUtils {
    public static void printArray(int[] result) {
        Stream.of(result).forEach(
                e -> System.out.print(e + ", ")
        );
        System.out.println("\n");
    }
}
