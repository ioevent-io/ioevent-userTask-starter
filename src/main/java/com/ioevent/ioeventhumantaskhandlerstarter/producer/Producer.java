package com.ioevent.ioeventhumantaskhandlerstarter.producer;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
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

    /*public void sendAnotherMessage() {
        Message<HumanTaskInfos> message;
        message = MessageBuilder.withPayload((new HumanTaskInfos("1","stepName")))
                .setHeader(KafkaHeaders.TOPIC, "ioevent-human-task")
                .setHeader(KafkaHeaders.KEY, "second message")
                .setHeader("customHeader", "customHeaderTest2")
                .build();
        kafkaTemplate.send(message);
    }*/
}
