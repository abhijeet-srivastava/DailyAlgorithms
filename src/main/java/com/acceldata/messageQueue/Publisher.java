package com.acceldata.messageQueue;

import java.util.HashMap;
import java.util.Map;

public class Publisher {

    String name;

    Map<String, Topic> topicMap;

    public Publisher(String name) {
        this.name = name;
        this.topicMap = new HashMap<>();
    }

    public  boolean registerTopic(Topic topic) {
        if(this.topicMap.containsKey(topic.topicName)) {
            return false;
        }
        this.topicMap.put(topic.topicName, topic);
        return true;
    }

    public void publishToTopic(String topicName, String messageContent) {
        Message message = new Message(messageContent);
        this.topicMap.get(topicName).publishToConsumers(message);
    }
}
