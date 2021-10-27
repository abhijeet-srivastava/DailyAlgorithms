package com.oracle.casb;

/**
 * Created By : abhijsri
 * Date  : 05/06/18
 **/
public class StaticFieldSingleton {


    private static FieldType field;

    private static class FieldTypeHolder {
        private static final FieldType fieldType = computeFieldType();
    }

    private static FieldType computeFieldType() {
        return new FieldType();
    }

    public static FieldType getField() {
        return FieldTypeHolder.fieldType;
    }
}
