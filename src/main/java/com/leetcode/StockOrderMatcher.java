package com.leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class StockOrderMatcher {

    TreeMap<Integer, TreeSet<Transaction>> BUY_ORDER_SET;
    TreeMap<Integer, TreeSet<Transaction>> SELL_ORDER_SET;

    Comparator<Transaction> txnComparator = Comparator.comparingInt(a -> a.timestamp);
    int maxTxnId = 1;

    public StockOrderMatcher() {
        this.BUY_ORDER_SET = new TreeMap<>();
        this.SELL_ORDER_SET = new TreeMap<>();
    }
    private void addTransaction(int type, int price, int timestamp, int quantity) {
        Transaction txn = new Transaction(maxTxnId, type, price, timestamp, quantity);
        maxTxnId += 1;
        if(type == 0) {
            this.SELL_ORDER_SET.computeIfAbsent(price, e -> new TreeSet<>(txnComparator)).add(txn);
        } else {
            this.BUY_ORDER_SET.computeIfAbsent(price, e -> new TreeSet<>(txnComparator)).add(txn);
        }
    }

    private List<Transaction> processBuyOrders(Transaction buyOrder){
        List<Transaction> mappingTxn = new ArrayList<>();
        mappingTxn.add(buyOrder);
        var t = this.SELL_ORDER_SET.headMap(buyOrder.price);
        int remBuyOrderQty = buyOrder.quantity;
        for(var sellTxns: t.entrySet()) {
            Set<Transaction> txns = sellTxns.getValue().tailSet(buyOrder);
            Iterator<Transaction> sellTxnItr = txns.iterator();
            while (remBuyOrderQty > 0 && sellTxnItr.hasNext()) {
                Transaction sellOrder = sellTxnItr.next();
                if(remBuyOrderQty >= sellOrder.quantity) {
                    remBuyOrderQty -= sellOrder.quantity;
                    sellTxnItr.remove();
                } else {
                    sellOrder.quantity -= remBuyOrderQty;
                    remBuyOrderQty = 0;
                }
                mappingTxn.add(sellOrder);//Mark patially or Fully
            }
            if(txns.isEmpty()) {
                this.SELL_ORDER_SET.remove(sellTxns.getKey());
            }
        }
        return mappingTxn;
    }


    private class Transaction {
        Integer txnid;

        Integer type;
        Integer price;
        Integer timestamp;
        Integer quantity;

        public Transaction(Integer txnid, Integer type, Integer price, Integer timestamp, Integer quantity) {
            this.txnid = txnid;
            this.type = type;
            this.price = price;
            this.timestamp = timestamp;
            this.quantity = quantity;
        }
    }
}
