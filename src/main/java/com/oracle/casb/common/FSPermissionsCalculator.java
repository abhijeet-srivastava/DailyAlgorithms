package com.oracle.casb.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By : abhijsri
 * Date  : 04/06/18
 **/
public class FSPermissionsCalculator {

    public static void main(String[] args) {
        List<String> list  = new ArrayList<>();

        list.add("zero");
        list.add("one");
        list.add("two");

        list.add(2, "three");

        System.out.println(list.get(2));

    }
}
