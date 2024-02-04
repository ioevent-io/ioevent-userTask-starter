package com.ioevent.ioeventusertaskhandlerstarter.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskInfosDto {
    private String id;
    private String processName;
    private String correlationId;
    private Long startTime;
    private String payload;
    private Object rawPayload;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UserTaskInfosDto toDto(UserTaskInfos userTaskInfos) {
        Object deserializedObject = deserialize(userTaskInfos.getRawPayload());

        return new UserTaskInfosDto(
                userTaskInfos.getId(),
                userTaskInfos.getProcessName(),
                userTaskInfos.getCorrelationId(),
                userTaskInfos.getStartTime(),
                userTaskInfos.getPayload(),
                deserializedObject
        );
    }

    private static Object deserialize(byte[] rawPayload) {
        try {
            return objectMapper.readValue(rawPayload, Object.class);
        } catch (IOException e) {
            return null;
        }
    }
}