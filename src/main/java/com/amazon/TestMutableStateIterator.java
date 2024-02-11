package com.amazon;

import java.util.Iterator;
import java.util.UUID;

public class TestMutableStateIterator {

    public static void main(String[] args) {
        MutableObject mutableObject = new MutableObject(UUID.randomUUID().toString());
        int count = 0;
        String prevToken = null;
        Iterator<MutableObject> itr = mutableObject.iterator();
        while (itr.hasNext()) {
            MutableObject currState = itr.next();
            System.out.printf("[%d]: Curr State: %s\n", count, currState.toString());
            if(++count < 5) {
                prevToken = "DUMMY_TOKEN";
            } else {
                prevToken = null;
            }
            currState.updateState(prevToken);
        }
    }
}
