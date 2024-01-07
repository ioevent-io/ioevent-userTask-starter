package com.ioevent.ioeventhumantaskhandlerstarter.listner;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.domain.IOEventHeaders;
import com.ioevent.ioeventhumantaskhandlerstarter.repository.HumanTaskInfosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
public class EventListner {
    private final HumanTaskInfosRepository humanTaskInfosRepository;

    public EventListner(HumanTaskInfosRepository humanTaskInfosRepository) {
        this.humanTaskInfosRepository = humanTaskInfosRepository;
    }

    @KafkaListener(groupId = "testgroup_id",id = "idTest",topics = "Samples-ioevent-human-task", containerFactory = "kafkaListenerContainerFactory")
    public void onEvent(@Payload List<Message<byte[]>> messages){
        List<HumanTaskInfos> humanTaskInfosList = new ArrayList<>();
        messages.forEach(message -> {
            log.info("Message headers received: {}", message.getHeaders());
            log.info("Message payload received: {}", message.getPayload());
            log.info("Message payload string received: {}", new String(message.getPayload()));
            log.info("Message header received: {}", message.getHeaders().get("customHeader"));

            HumanTaskInfos humanTaskInfos = HumanTaskInfos.builder().id(UUID.randomUUID().toString())
                    .processName(message.getHeaders().get(IOEventHeaders.PROCESS_NAME.toString()).toString())
                    .correlationId(message.getHeaders().get(IOEventHeaders.CORRELATION_ID.toString()).toString())
                    .eventType(message.getHeaders().get(IOEventHeaders.EVENT_TYPE.toString()).toString())
                    .stepName(message.getHeaders().get(IOEventHeaders.STEP_NAME.toString()).toString())
                    .apiKey(message.getHeaders().get(IOEventHeaders.API_KEY.toString()).toString())
                    .startTime((Long) message.getHeaders().get(IOEventHeaders.START_TIME.toString()))
                    .instanceStartTime(((Long) message.getHeaders().get(IOEventHeaders.START_INSTANCE_TIME.toString())))
                    .isImplicitStart((Boolean) message.getHeaders().get(IOEventHeaders.IMPLICIT_START.toString()))
                    .isImplicitEnd((Boolean) message.getHeaders().get(IOEventHeaders.IMPLICIT_END.toString()))
                    .outputs((Map<String, String>) message.getHeaders().get("OUTPUTS"))
                    .appName(message.getHeaders().get("APPNAME").toString())
                    .input((List<String>) message.getHeaders().get(IOEventHeaders.INPUT.toString()))
                    .payload(new String(message.getPayload()))
                    .rawPayload(message.getPayload())
                    .build();
            log.info("human task received : {}", humanTaskInfos);
            humanTaskInfosList.add(humanTaskInfos);
        });

     humanTaskInfosRepository.saveAll(humanTaskInfosList);
    }

    /*@KafkaListener(groupId = "testgroup_id1",id = "idTest1",topics = "Samples-ioevent-human-task", containerFactory = "kafkaListenerContainerFactory2")
    public void onEvent2(@Payload List<HumanTaskInfos> messages){
        messages.forEach(message -> log.info("Message received with json deserializer: {}", message.toString()));
    }*/
}
