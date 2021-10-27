package com.oracle.casb.common;

public interface HeapType<T extends Comparable<T>> {

    void push(T t);
}
