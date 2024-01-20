package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.domain.IOEventHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class MessageProducerServiceImpl implements MessageProducerService{
    @Autowired
    private HumanTaskInfosService humanTaskInfosService;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${ioevent.application_name:}")
    private String applicationName;

    @Value("${ioevent.prefix:}")
    private String prefix;
    /*@Override
    public String sendMessage(String id, Object payload, Map<String, String> customHeaders, String outputString) {
        Optional<HumanTaskInfos> humanTaskInfos = humanTaskInfosService.getById(id);
        HumanTaskInfos humanTaskInfosToSend = new HumanTaskInfos();
        if(humanTaskInfos.isPresent()){
            humanTaskInfosToSend = humanTaskInfos.get();
        }

        HumanTaskInfos finalHumanTaskInfosToSend = humanTaskInfosToSend;
        kafkaTemplate.executeInTransaction(t -> {
            finalHumanTaskInfosToSend.getOutputs().keySet().forEach(
                    i -> kafkaTemplate.send(buildMessage(finalHumanTaskInfosToSend, payload, finalHumanTaskInfosToSend.getOutputs().get(i), i, customHeaders, outputString))

            );
            return "Event sent successfully";
        });

        humanTaskInfosService.deactivateHumanTask(id);

        return "Event sent successfully";
    }*/

    @Override
    public String sendMessage(String id, Object payload, Map<String, String> customHeaders, String outputString) {
        Optional<HumanTaskInfos> humanTaskInfos = humanTaskInfosService.getById(id);
        HumanTaskInfos humanTaskInfosToSend = new HumanTaskInfos();
        if(humanTaskInfos.isPresent()){
            humanTaskInfosToSend = humanTaskInfos.get();
        }

        HumanTaskInfos finalHumanTaskInfosToSend = humanTaskInfosToSend;
        kafkaTemplate.send(buildMessage(finalHumanTaskInfosToSend, payload, prefix+"-"+applicationName+"_"+"ioevent-human-task-Response", humanTaskInfosToSend.getStepName()+"-human", customHeaders, outputString));
        humanTaskInfosService.deactivateHumanTask(id);

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
