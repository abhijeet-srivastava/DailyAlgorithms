package com.acceldata.messageQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class ConsumerTopic {

    Topic topic;
    Consumer consumer;

    Deque<Message> messageQueue;

    public ConsumerTopic(Consumer consumer, Topic topic) {
        this.topic = topic;
        this.consumer = consumer;
        this.messageQueue = new ArrayDeque<>();
    }

    public void publishToConsumerQueue(Message message) {
        this.messageQueue.offerLast(message);
    }

    public Message consumeFromTopic() throws MessageQueueEmptyException {
        if(this.messageQueue.isEmpty()) {
            throw  new MessageQueueEmptyException("Trying to consume from empty queue");
        }
        return this.messageQueue.removeFirst();
    }
}
