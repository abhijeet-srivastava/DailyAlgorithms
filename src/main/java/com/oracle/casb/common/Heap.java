package com.oracle.casb.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Created By : abhijsri
 * Date  : 2018-12-27
 **/
public class  Heap<T>{
    private List<T> heap;

    private Comparator<T> comparator;
    private boolean isMax = true;

    public Heap(Comparator<T> comparator) {
        heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public Heap(Comparator<T> comparator, boolean isMax) {
        heap = new ArrayList<>();
        this.comparator = comparator;
        this.isMax = isMax;
    }


    public void push(T obj) {
        heap.add(obj);
        pushUp(heap.size()-1);
    }

    public T pop() {
        T obj = null;
        if (!heap.isEmpty()) {
            swap(0, heap.size()-1);
            obj = heap.remove(heap.size()-1);
            pushDown(0);
        }
        return obj;
    }



    public T getFirst(){
        return heap.get(0);
    }
    public T get(int index) {
        return heap.get(index);
    }

    public int size() {
        return heap.size();
    }

    public int parent(int i) {
        return (i-1)/2;
    }

    public int left(int i) {
        return 2*i+1;
    }

    public int right(int i) {
        return 2*(i+1);
    }
    private void swap(int i, int j) {
        T tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    private void pushDown(int i) {
        int left = left(i);
        int right = right(i);
        int largest = i;
        if (left < heap.size() && !isEqualAndCompared(largest, left)) {
            largest = left;
        }
        if (right < heap.size() && !isEqualAndCompared(largest, right)) {
            largest = right;
        }
        if (largest != i) {
            swap(largest, i);
            pushDown(largest);
        }
    }

    private void pushUp(int i) {
        while (i > 0 && !isEqualAndCompared(parent(i), i)) {
            swap(parent(i), i);
            i = parent(i);
        }
    }
    private boolean isEqualAndCompared(int largest, int index) {
        int value = 0;
        if (isMax) {
            value = comparator.compare(heap.get(largest), heap.get(index));
        } else {
            value = comparator.reversed().compare(heap.get(largest), heap.get(index));
        }
        return value < 0;
    }

    public void update(T dependentCourse, Function<T, Void> function) {
        int index = getIndex(dependentCourse);
        if(index >= 0 && index < heap.size())
        function.apply(dependentCourse);
        swap(0, index);
        pushDown(0);
    }

    private int getIndex(T dependentCourse) {
        int index = -1;
        for (int i = 0; i < heap.size(); i++) {
            if (comparator.compare(dependentCourse, heap.get(i)) == 0) {
                index = i;
                break;
            }
        }
        return index;
    }

    public List<T> getHeap() {
        return heap;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
