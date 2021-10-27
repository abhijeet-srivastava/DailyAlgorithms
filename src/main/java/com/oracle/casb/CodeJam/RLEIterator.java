package com.oracle.casb.CodeJam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By : abhijsri
 * Date  : 10/09/18
 **/
public class RLEIterator {

    private  int VISITED = 0;
    private  int CURR = 0;

    private List<Entry> list;
    public RLEIterator(int[] A) {
        list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < A.length - 1; i += 2) {
            if (A[i] == 0) {
                continue;
            }
            list.add(new Entry(start, start + A[i] - 1, A[i+1]));
            start += A[i];
        }
    }

    public int next(int n) {
        int ret = -1;
        int index = VISITED + n;
        while (CURR < list.size()) {
            Entry entry = list.get(CURR);
            if (entry.lies(index)) {
                VISITED += n;
                ret = entry.num;
                break;
            } else {
                VISITED += entry.size();
                n -= entry.size();
                CURR += 1;
            }
        }
        return ret;
    }

    class Entry {
        private int start;
        private int end;
        private int num;

        public Entry(int start, int end, int num) {
            this.start = start;
            this.end = end;
            this.num = num;
        }

        public boolean lies(int num) {
            return num-1 >= start && num-1 <= end;
        }

        public int size() {
            return (end - start) + 1;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public int getNum() {
            return num;
        }
    }
}
