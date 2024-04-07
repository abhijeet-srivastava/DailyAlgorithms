package com.acceldata.messageQueue;

public class MessageQueueEmptyException extends Exception {

    public MessageQueueEmptyException(String message) {
        super(message);
    }
}
