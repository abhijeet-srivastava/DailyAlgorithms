package com.oracle.casb.design.singleton;

public class BillPughSingleton {

    private BillPughSingleton() {
    }
    private static class BillPughSingletonHelper {
        private static final BillPughSingleton BILL_PUGH_SINGLETON = new BillPughSingleton();
    }
    public static BillPughSingleton getInstance() {
        return BillPughSingletonHelper.BILL_PUGH_SINGLETON;
    }
}
