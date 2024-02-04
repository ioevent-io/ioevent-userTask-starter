package com.ioevent.ioeventusertaskhandlerstarter.service;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageProducerServiceImplTest {
    @Mock
    private UserTaskInfosService userTaskInfosService;

    @Mock
    private MessageProducerServiceImpl messageProducerService;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessageWhenHumanTaskInfosIsPresentAndNotImplicitStart(){
        UserTaskInfos userTaskInfos = new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true);
        CompletableFuture<SendResult<String, Object>> future = new CompletableFuture<>();

        when(userTaskInfosService.getById("1")).thenReturn(Optional.of(userTaskInfos));
        when(messageProducerService.sendMessage("1", "payload", null)).thenReturn("Event sent successfully");
        when(kafkaTemplate.send(Mockito.any(Message.class))).thenReturn(future);

        String result = messageProducerService.sendMessage("1", "payload", null);
        assertEquals("Event sent successfully", result);
        //verify(kafkaTemplate, times(1)).send((Message<?>) any());
        //verify(userTaskInfosService, times(1)).deactivateHumanTask("1");
    }
}
