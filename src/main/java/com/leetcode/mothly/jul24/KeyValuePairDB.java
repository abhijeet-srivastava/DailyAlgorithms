package com.leetcode.mothly.jul24;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyValuePairDB implements KeyValuePair, Transactional{

    Map<String, String> store;
    Transaction transaction;

    public KeyValuePairDB() {
        this.store = new HashMap<>();
    }

    @Override
    public String get(String key) throws KeyNotFoundException {
        return this.store.get(key);
    }

    @Override
    public void put(String key, String value) {
        if(inTransaction()) {
            trackChanges(key);
        }
        this.store.put(key, value);
    }

    @Override
    public void delete(String key) throws KeyNotFoundException {
        if(inTransaction()) {
            trackChanges(key);
        }
        this.store.remove(key);
    }

    private boolean inTransaction() {
        return this.transaction != null;
    }

    private void trackChanges(String key) {
        if(!this.transaction.transactionalStore.containsKey(key)) {
            this.transaction.transactionalStore.put(key, this.store.getOrDefault(key, null));
        }
    }

    @Override
    public void beginTxn() {
        this.transaction = new Transaction();
    }

    @Override
    public void commit() {
        this.transaction = null;
    }

    @Override
    public void rollback() {
        for(Map.Entry<String, String> entry: this.transaction.transactionalStore.entrySet()) {
            if(entry.getValue() == null) {
                this.store.remove(entry.getKey());
            } else {
                this.store.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private class Transaction {
        Map<String, String> transactionalStore;

        public Transaction() {
            this.transactionalStore = new HashMap<>();
        }
    }
}
