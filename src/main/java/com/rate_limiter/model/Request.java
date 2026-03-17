package com.rate_limiter.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Request {
    private long timestamp;
    private UUID clientId;
    private String sourceIpAddr;

    public Request(long timestamp, UUID clientId, String sourceIpAddr) {
        this.timestamp = timestamp;
        this.clientId = clientId;
        this.sourceIpAddr = sourceIpAddr;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
