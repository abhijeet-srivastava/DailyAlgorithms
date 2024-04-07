package com.rate_limiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Request {
    long timestamp;
    UUID clientId;
    String sourceIpAddr;
}
