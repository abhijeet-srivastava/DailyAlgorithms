package com.acceldata.messageQueue;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    String consumerName;
    Map<String, ConsumerTopic> subscriptions;

    public Consumer(String consumerName) {
        this.consumerName = consumerName;
        this.subscriptions = new HashMap<>();
    }

    public  void subscribe(Topic topic) {
        if(this.subscriptions.containsKey(topic.topicName)) {
            return;
        }
        ConsumerTopic consumerTopic = new ConsumerTopic(this, topic);
        this.subscriptions.put(topic.topicName, consumerTopic);
        topic.grantSubscription(consumerTopic);
    }

    public Message consumeFromTopic(Topic topic) throws MessageQueueEmptyException {
        ConsumerTopic consumerTopic = this.subscriptions.get(topic.topicName);
        Message message = consumerTopic.consumeFromTopic();
        return message;
    }

    public void  consumeMessages() {
        for(var t: this.subscriptions.entrySet()) {
            try {
                Message message = consumeFromTopic(t.getValue().topic);
                System.out.printf("Consumed message [%s] from Topic: %s\n", message.toString(), t.getKey());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
