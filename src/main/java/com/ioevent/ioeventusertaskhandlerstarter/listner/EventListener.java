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

package com.ioevent.ioeventusertaskhandlerstarter.listner;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import com.ioevent.ioeventusertaskhandlerstarter.domain.IOEventHeaders;
import com.ioevent.ioeventusertaskhandlerstarter.service.UserTaskInfosService;
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
public class EventListener {

    private final UserTaskInfosService userTaskInfosService;

    public EventListener(UserTaskInfosService userTaskInfosService) {
        this.userTaskInfosService = userTaskInfosService;
    }
    @KafkaListener(groupId = "#{getGroupId}",id = "idTest",topics = "#{getApplicationName}", containerFactory = "kafkaListenerContainerFactory")
    public void onEvent(@Payload List<Message<byte[]>> messages){
        List<UserTaskInfos> userTaskInfosList = new ArrayList<>();
        messages.forEach(message -> {
            UserTaskInfos userTaskInfos = UserTaskInfos.builder().id(UUID.randomUUID().toString())
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
            log.info("user task received : {}", userTaskInfos);
            userTaskInfosList.add(userTaskInfos);
        });
        userTaskInfosService.saveAll(userTaskInfosList);

    }

}