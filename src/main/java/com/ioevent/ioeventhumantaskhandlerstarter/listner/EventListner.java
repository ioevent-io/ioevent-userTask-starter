package com.ioevent.ioeventhumantaskhandlerstarter.listner;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

@Slf4j
@Configuration
public class EventListner {
    @KafkaListener(groupId = "testgroup_id",id = "idTest",topics = "humanTasks", containerFactory = "kafkaListenerContainerFactory")
    public void onEvent(@Payload List<Message<byte[]>> messages){
        messages.forEach(message -> {
            log.info("Message headers received: {}", message.getHeaders());
            log.info("Message payload received: {}", message.getPayload());
            log.info("Message payload string received: {}", new String(message.getPayload()));
            log.info("Message header received: {}", message.getHeaders().get("customHeader"));
        });
    }

    @KafkaListener(groupId = "testgroup_id1",id = "idTest1",topics = "humanTasks", containerFactory = "kafkaListenerContainerFactory2")
    public void onEvent2(@Payload List<HumanTaskInfos> messages){
        messages.forEach(message -> log.info("Message received: {}", message.toString()));
    }
}