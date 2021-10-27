package com.oracle.casb.CodeJam;

import java.util.Stack;

/**
 * Created By : abhijsri
 * Date  : 10/09/18
 **/
public class StockSpanner {
    private Stack<Integer>  prices, weight;

    public StockSpanner() {
        prices = new Stack<>();
        weight = new Stack<>();
    }

    public int next(int price) {
        int w = 1;
        while (!prices.isEmpty() && prices.peek() < price) {
            prices.pop();
            w += weight.pop();
        }
        prices.push(price);
        weight.push(w);
        return w;
    }
}
