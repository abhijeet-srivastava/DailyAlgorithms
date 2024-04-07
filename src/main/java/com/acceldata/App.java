package com.acceldata;

import com.acceldata.messageQueue.Consumer;
import com.acceldata.messageQueue.Publisher;
import com.acceldata.messageQueue.Topic;

public class App {
    /**
     * Create a in Memory Message Queue
     * Publisher
     * Consumer
     * Topic
     * Message
     *
     * Consumers are subscribing to Topics
     * Publishers are publishing messages to Topic
     *  Once published message is available to all the Consumer
     *  Messages consumed in order in which they are received
     * Entities -
     *
     * Publisher
     * Topic - Name:
     *
     * Subscriber
     *
     * Message
     *
     * Publisher - Map<String, Topic>  publish(String topic, message) {} => Published to the topic
     *  registerTopic(String topicName)
     *
     * Topic => Map<String, ConsumerTopic>, publishToConsumers() {}
     *
     * ConsumerTopic {
     *      Topic
     *      Consumer
     *     Queue<Message>
     *     publishToConsumerQueue(Message)
     *     consumeFromTopic()
     * }
     *
     * Consumer :
     * Map<String, ConsumerTopic>
     *
     * Subscribe to Topic
     * subscribe(Topic)
     *
     * consumeFromTopic(Topic )
     *
     *
     *
     */

    public static void main(String[] args) {
        App app = new App();
        app.testMessageQueue();
    }

    private void testMessageQueue() {
        Publisher publisher1 = new Publisher("Publisher_1");

        Topic topic1 = new Topic("TOPIC_1");
        Topic topic2 = new Topic("TOPIC_2");
        Topic topic3 = new Topic("TOPIC_3");

        publisher1.registerTopic(topic1);
        publisher1.registerTopic(topic2);
        publisher1.registerTopic(topic3);

        Consumer consumer1 = new Consumer("Consumer_1");
        Consumer consumer2 = new Consumer("Consumer_2");
        Consumer consumer3 = new Consumer("Consumer_3");
        Consumer consumer4 = new Consumer("Consumer_4");

        consumer1.subscribe(topic1);
        consumer1.subscribe(topic2);

        consumer2.subscribe(topic1);
        consumer2.subscribe(topic2);
        //consumer2.subscribe(topic3);

        consumer3.subscribe(topic3);

        publisher1.publishToTopic("TOPIC_1", "Message_1");
        publisher1.publishToTopic("TOPIC_2", "Message_3");
        /*publisher1.publishToTopic("TOPIC_1", "Message_2");
        publisher1.publishToTopic("TOPIC_2", "Message_3");
        publisher1.publishToTopic("TOPIC_3", "Message_4");
        publisher1.publishToTopic("TOPIC_2", "Message_5");*/

        consumer1.consumeMessages();
        /*consumer2.consumeMessages();
        consumer3.consumeMessages();
        consumer4.consumeMessages();*/

    }

}
