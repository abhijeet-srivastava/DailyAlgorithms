package com.leetcode.mothly.jul24;

public interface Transactional {

    void beginTxn();

    void commit();

    void rollback();
}
