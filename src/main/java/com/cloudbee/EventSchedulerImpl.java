package com.cloudbee;

import java.util.Iterator;
import java.util.List;

public class EventSchedulerImpl implements EventScheduler {
    Iterator<String> iterator;

    public EventSchedulerImpl(List<String> list) {
        this.iterator = list.iterator();
    }

    @Override
    public boolean hasRemainingEvent() {
        return this.iterator.hasNext();
    }

    @Override
    public String scheduleEvent() {
        return this.iterator.next();
    }
}
