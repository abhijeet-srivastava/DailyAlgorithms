package com.oracle.casb.expedia.serialization.serialiation.proxy;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

//Both Data and DataProxy class should implement Serializable interface.
public class Data implements Serializable {
    private static final long serialVersionUID = 2087368867376448459L;

    private String data;

    public Data(String d){
        this.data=d;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "Data{data="+data+"}";
    }

    //serialization proxy class, DataProxy is inner private static class, so that other classes can’t access it.
    //DataProxy should have a single constructor that takes Data as argument.
    private static class DataProxy implements Serializable{

        private static final long serialVersionUID = 8333905273185436744L;

        private String dataProxy;
        private static final String PREFIX = "ABC";
        private static final String SUFFIX = "DEFG";

        public DataProxy(Data d){
            //obscuring data for security
            this.dataProxy = PREFIX + d.data + SUFFIX;
        }

        /**
         * DataProxy class should implement readResolve() method returning Data object. So when Data class is deserialized,
         * internally DataProxy is deserialized and when it’s readResolve() method is called, we get Data object.
         * @return
         * @throws InvalidObjectException
         */
        private Object readResolve() throws InvalidObjectException {
            if(dataProxy.startsWith(PREFIX) && dataProxy.endsWith(SUFFIX)){
                return new Data(dataProxy.substring(3, dataProxy.length() -4));
            }else throw new InvalidObjectException("data corrupted");
        }

    }

    //writeReplace() method returning DataProxy instance.
    // So when Data object is serialized, the returned stream is of DataProxy class
    private Object writeReplace(){
        return new DataProxy(this);
    }

    /**
     * readObject() method in Data class and throw InvalidObjectException to
     * avoid hackers attack trying to fabricate Data object stream and parse it.
     * @param ois
     * @throws InvalidObjectException
     */
    private void readObject(ObjectInputStream ois) throws InvalidObjectException{
        throw new InvalidObjectException("Proxy is not used, something fishy");
    }
}
