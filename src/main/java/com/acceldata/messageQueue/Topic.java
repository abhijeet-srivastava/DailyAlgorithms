package com.acceldata.messageQueue;

import java.util.HashMap;
import java.util.Map;

public class Topic {

    String topicName;

    Map<String, ConsumerTopic> consumerTopicMap;

    public Topic(String topicName) {
        this.topicName = topicName;
        this.consumerTopicMap = new HashMap<>();
    }

    public void publishToConsumers(Message message) {
        for(var t: consumerTopicMap.entrySet()) {
            t.getValue().publishToConsumerQueue(message);
            System.out.printf("Publish message %s to Consumer :%s\n", message.toString(), t.getValue().consumer.consumerName);
        }
    }

    public void grantSubscription(ConsumerTopic consumerTopic) {
        this.consumerTopicMap.put(consumerTopic.consumer.consumerName, consumerTopic);
    }
}
