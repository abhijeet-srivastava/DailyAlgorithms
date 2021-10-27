package com.oracle.casb.CodeJam;



import com.google.common.collect.ImmutableList;

import static com.oracle.casb.common.SlidingWindowSpliterator.windowed;
import static java.util.stream.Collectors.toList;

/**
 * Created By : abhijsri
 * Date  : 10/09/18
 **/
public class TestSlidingWindow {
    public static void main(String[] args) {
        windowed(ImmutableList.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16), 4)
                .map(group -> group.collect(toList()))
                .forEach(System.out::println);
    }

}
