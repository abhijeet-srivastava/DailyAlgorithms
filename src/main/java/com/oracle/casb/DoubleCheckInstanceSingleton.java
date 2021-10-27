package com.oracle.casb;

/**
 * Created By : abhijsri
 * Date  : 05/06/18
 **/
public class DoubleCheckInstanceSingleton {

    private volatile FieldType field;

    public FieldType getField() {
        FieldType result = field;
        if (result == null) {
            synchronized (field) {
                result = field;
                if (result == null) {
                    field = result = computeField();
                }
            }
        }
        return field;
    }

    private FieldType computeField() {
        return new FieldType();
    }
}
