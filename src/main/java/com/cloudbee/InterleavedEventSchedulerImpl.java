package com.cloudbee;

import java.util.List;

public class InterleavedEventSchedulerImpl implements InterleavedEventScheduler {

    Integer schedulerIndex;
    List<EventScheduler> schedulers;


    public InterleavedEventSchedulerImpl(List<EventScheduler>  schedulers) {
        this.schedulerIndex = 0;
        this.schedulers = schedulers;

    }

    public boolean hasRemainingEvent() {
        int len = this.schedulers.size();
        int currIdx = schedulerIndex;
        while(!this.schedulers.get(schedulerIndex).hasRemainingEvent()) {
            schedulerIndex = (schedulerIndex + 1)%len;
            if(schedulerIndex == currIdx) {
                break;
            }
        }
        return this.schedulers.get(schedulerIndex).hasRemainingEvent();

    }

    public String scheduleEvent() {
        /*assert hasRemainingEvent() == true;
        if(!hasRemainingEvent()) {
            throw new AssertionError("All events consumed");
        }*/
        String nextEvent =  this.schedulers.get(schedulerIndex).scheduleEvent();
        int len = this.schedulers.size();
        schedulerIndex = (schedulerIndex + 1)%len;
        return nextEvent;
    }
}
