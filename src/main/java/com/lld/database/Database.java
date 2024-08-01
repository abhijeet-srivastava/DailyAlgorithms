package com.lld.database;

import java.util.HashMap;
import java.util.Map;

public class Database implements KeyValuePair {
    private Map<String, String> db;
    private Map<String, String> txnDb;

    private final String TOMBSTONE = "TOMBSTONE_VALUE";

    private boolean inTxn;

    public Database() {
        this.db = new HashMap<>();
        this.inTxn = false;
    }

    @Override
    public String get(String key) {
        if(this.inTxn) {
            String txnVal = this.txnDb.get(key);
            if(TOMBSTONE.equals(txnVal)) {
                throw new RuntimeException("Given key not found");
            } else if(txnVal != null) {
                return txnVal;
            }
        }
        if(!this.db.containsKey(key)) {
            throw new RuntimeException("Given key not found");
        }
        return this.db.get(key);
    }

    @Override
    public void put(String key, String value) {
        if(this.inTxn) {
            this.txnDb.put(key, value);
        } else {
            this.db.put(key, value);
        }
    }

    @Override
    public void delete(String key) {
        if(this.inTxn) {
            String txnVal = this.txnDb.get(key);
            if(txnVal == null || TOMBSTONE.equals(txnVal)) {
                throw new RuntimeException("Key does not exist");
            } else {
                this.txnDb.put(key, TOMBSTONE);
            }
        } else {
            if(!this.db.containsKey(key)) {
                throw new RuntimeException("Key not found");
            }
            this.db.remove(key);
        }
    }

    public void beginTransaction() {
        this.inTxn = true;
        if(this.txnDb == null) {
            this.txnDb = new HashMap<>();
        }
        this.txnDb.clear();
    }
    public void commit() {
        if(!this.inTxn) {
            throw new RuntimeException("Invalid Trasaction state");
        }
        for(var entry: this.txnDb.entrySet()) {
            if(TOMBSTONE.equals(entry.getValue())) {
                this.db.remove(entry.getKey());
            } else {
                this.db.put(entry.getKey(), entry.getValue());
            }
        }
        this.inTxn = false;
        this.txnDb.clear();
    }
    public void rollback() {
        this.inTxn = false;
        this.txnDb.clear();
    }

}
