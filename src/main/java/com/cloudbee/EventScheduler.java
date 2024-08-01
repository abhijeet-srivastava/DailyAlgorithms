package com.cloudbee;

public interface EventScheduler {
    boolean hasRemainingEvent();
    String scheduleEvent();
}
