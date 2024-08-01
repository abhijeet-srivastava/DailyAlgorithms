package com.cloudbee;

public interface InterleavedEventScheduler {
    boolean hasRemainingEvent();

    String scheduleEvent();
}
