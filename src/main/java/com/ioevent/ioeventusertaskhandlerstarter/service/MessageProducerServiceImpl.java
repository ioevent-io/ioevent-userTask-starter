/*
 * Copyright © 2024 CodeOnce Software (https://www.codeonce.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ioevent.ioeventusertaskhandlerstarter.service;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import com.ioevent.ioeventusertaskhandlerstarter.domain.IOEventHeaders;
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
    private UserTaskInfosService userTaskInfosService;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${ioevent.application_name:}")
    private String applicationName;

    @Value("${ioevent.prefix:}")
    private String prefix;

    @Override
    public String sendMessage(final String id,final Object payload,final Map<String, String> customHeaders) {
        Optional<UserTaskInfos> userTaskInfos = userTaskInfosService.getById(id);
        UserTaskInfos userTaskInfosToSend = new UserTaskInfos();
        if(userTaskInfos.isPresent()){
            userTaskInfosToSend = userTaskInfos.get();
        }

        UserTaskInfos finalUserTaskInfosToSend = userTaskInfosToSend;
        String outputEvent = finalUserTaskInfosToSend.getOutputEvent()+"-user";
        String prefixTopic = prefix.isEmpty() ? "" : prefix+"-";
        kafkaTemplate.send(buildMessage(finalUserTaskInfosToSend, payload, prefixTopic+applicationName+"_"+"ioevent-user-task-Response", outputEvent, customHeaders));
        userTaskInfosService.deactivateUserTask(id);
        return "Event sent successfully";
    }




    private Message<Object> buildMessage(final UserTaskInfos userTaskInfos, final Object payload, final String topic, final String key, final Map<String, String> customHeaders) {
        return MessageBuilder
                .withPayload(payload)
                .copyHeaders(customHeaders)
                .setHeader(KafkaHeaders.KEY, userTaskInfos.getCorrelationId())
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(IOEventHeaders.OUTPUT_EVENT.toString(), key)
                .setHeader(IOEventHeaders.STEP_NAME.toString(), userTaskInfos.getStepName())
                .setHeader(IOEventHeaders.PROCESS_NAME.toString(), userTaskInfos.getProcessName())
                .setHeader(IOEventHeaders.CORRELATION_ID.toString(), userTaskInfos.getCorrelationId())
                .setHeader(IOEventHeaders.EVENT_TYPE.toString(), userTaskInfos.getEventType())
                .setHeader(IOEventHeaders.API_KEY.toString(), userTaskInfos.getApiKey())
                .setHeader(IOEventHeaders.START_TIME.toString(), userTaskInfos.getStartTime())
                .setHeader(IOEventHeaders.START_INSTANCE_TIME.toString(), userTaskInfos.getInstanceStartTime())
                .setHeader(IOEventHeaders.IMPLICIT_START.toString(), userTaskInfos.getIsImplicitStart())
                .setHeader(IOEventHeaders.IMPLICIT_END.toString(), userTaskInfos.getIsImplicitEnd())
                .setHeader(IOEventHeaders.INPUT.toString(), userTaskInfos.getInput())
                .build();
    }
}
