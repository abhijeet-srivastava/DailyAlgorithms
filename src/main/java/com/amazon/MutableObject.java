package com.amazon;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class MutableObject implements Iterable<MutableObject> {

    String personId;
    boolean initialCall;

    String tokenId;

    public MutableObject(String personId) {
        this.personId = personId;
        this.initialCall = true;
    }

    public MutableObject(String personId, boolean initialCall, String tokenId) {
        this.personId = personId;
        this.initialCall = initialCall;
        this.tokenId = tokenId;
    }

    @Override
    public String toString() {
        return "MutableObject{" +
                "personId='" + personId + '\'' +
                ", initialCall=" + initialCall +
                ", tokenId='" + tokenId + '\'' +
                '}';
    }

    public void updateState(String tokenId) {
        this.initialCall = false;
        this.tokenId = tokenId;
    }

    private MutableObject currentState() {
        return this;
    }

    private class MutableObjectIterator implements Iterator<MutableObject> {

        @Override
        public boolean hasNext() {
            return initialCall || Objects.nonNull(tokenId);
        }

        @Override
        public MutableObject next() {
            if(!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return currentState();
        }
    }
    @NotNull
    @Override
    public Iterator<MutableObject> iterator() {
        return new MutableObjectIterator();
    }

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
