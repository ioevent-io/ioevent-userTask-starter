package com.ioevent.ioeventhumantaskhandlerstarter.listner;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.domain.IOEventHeaders;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
public class EventListner {

    private final HumanTaskInfosService humanTaskInfosService;

    public EventListner(HumanTaskInfosService humanTaskInfosService) {
        this.humanTaskInfosService = humanTaskInfosService;
    }
    @KafkaListener(groupId = "#{getGroupId}",id = "idTest",topics = "#{getApplicationName}", containerFactory = "kafkaListenerContainerFactory")
    public void onEvent(@Payload List<Message<byte[]>> messages){
        List<HumanTaskInfos> humanTaskInfosList = new ArrayList<>();
        messages.forEach(message -> {
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
                    .active(true)
                    .outputEvent(message.getHeaders().get(IOEventHeaders.OUTPUT_EVENT.toString()).toString())
                    .payload(new String(message.getPayload()))
                    .rawPayload(message.getPayload())
                    .build();
            log.info("human task received : {}", humanTaskInfos);
            humanTaskInfosList.add(humanTaskInfos);
        });
        humanTaskInfosService.saveAll(humanTaskInfosList);

    }

}