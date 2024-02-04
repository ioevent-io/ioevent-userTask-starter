package com.ioevent.ioeventusertaskhandlerstarter.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Producer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() {
        Message<Object> message;
        message = MessageBuilder.withPayload((Object) "Hello World")
                .setHeader(KafkaHeaders.TOPIC, "ioevent-human-task")
                .setHeader(KafkaHeaders.KEY, "first message")
                .setHeader("customHeader", "customHeaderTest")
                .build();
        kafkaTemplate.send(message);
    }

}
