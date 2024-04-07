package com.acceldata.messageQueue;

import com.oracle.casb.DateTime;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Message {

    UUID uid;
    String message;
    Long createdTime;

    public Message(String message) {
        this.uid = UUID.randomUUID();
        this.message = message;
        this.createdTime = System.currentTimeMillis();
    }

    public UUID getUid() {
        return uid;
    }

    public String getMessage() {
        return message;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uid=" + uid +
                ", message='" + message + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
