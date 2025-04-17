package com.confluent.multithreading;


public class SafeListner {
    private EventListener eventListener;

    private SafeListner() {
        this.eventListener = new EventListener() {

            @Override
            public void onEvent(Event e) {

            }
        };
    }

    public static  SafeListner newInstance(EventSource src) {
        SafeListner listner = new SafeListner();
        src.registerListner(listner.eventListener);
        return listner;
    }

    private interface EventListener {
        void onEvent(Event e);
    }

    private class Event {
    }
    private interface EventSource {
        void registerListner(EventListener listner);
    }
}
