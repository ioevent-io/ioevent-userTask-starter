package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.domain.IOEventHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class MessageProducerServiceImpl implements MessageProducerService{
    @Autowired
    private HumanTaskInfosService humanTaskInfosService;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public String sendMessage(String id, Object payload, Map<String, String> customHeaders, String outputString) {
        HumanTaskInfos humanTaskInfos = null;
        if(humanTaskInfosService.getById(id).isPresent()){
            humanTaskInfos = humanTaskInfosService.getById(id).get();
        }
        for(String value: humanTaskInfos.getOutputs().keySet()){
            CompletableFuture<SendResult<String,Object>> future = new CompletableFuture<>();
            future = kafkaTemplate.send(buildMessage(humanTaskInfos, payload, humanTaskInfos.getOutputs().get(value), value,customHeaders,outputString));
            future.whenComplete(new BiConsumer<SendResult<String, Object>, Throwable>() {
                @Override
                public void accept(SendResult<String, Object> result, Throwable throwable) {
                    if(throwable != null){
                        log.error("Error while sending message to kafka", throwable);
                    }
                    else{
                        humanTaskInfosService.deactivateHumanTask(id);
                        log.info("Human Task finished successfully");
                    }
                }
            });
        }

        return "Event sent successfully";
    }

    private Message<Object> buildMessage(HumanTaskInfos humanTaskInfos, Object payload,String topic, String key, Map<String, String> customHeaders, String outputString) {
        return MessageBuilder
                .withPayload(payload)
                .copyHeaders(customHeaders)
                .setHeader(KafkaHeaders.KEY, humanTaskInfos.getCorrelationId())
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(IOEventHeaders.OUTPUT_EVENT.toString(), key)
                .setHeader(IOEventHeaders.STEP_NAME.toString(), humanTaskInfos.getStepName())
                .setHeader(IOEventHeaders.PROCESS_NAME.toString(), humanTaskInfos.getProcessName())
                .setHeader(IOEventHeaders.CORRELATION_ID.toString(), humanTaskInfos.getCorrelationId())
                .setHeader(IOEventHeaders.EVENT_TYPE.toString(), humanTaskInfos.getEventType())
                .setHeader(IOEventHeaders.API_KEY.toString(), humanTaskInfos.getApiKey())
                .setHeader(IOEventHeaders.START_TIME.toString(), humanTaskInfos.getStartTime())
                .setHeader(IOEventHeaders.START_INSTANCE_TIME.toString(), humanTaskInfos.getInstanceStartTime())
                .setHeader(IOEventHeaders.IMPLICIT_START.toString(), humanTaskInfos.getIsImplicitStart())
                .setHeader(IOEventHeaders.IMPLICIT_END.toString(), humanTaskInfos.getIsImplicitEnd())
                .setHeader(IOEventHeaders.INPUT.toString(), humanTaskInfos.getInput())
                .build();
    }
}
