package com.ioevent.ioeventhumantaskhandlerstarter.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HumanTaskInfosDto {
    private String id;
    private String processName;
    private String correlationId;
    private Long startTime;
    private String payload;
    private Object rawPayload;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static HumanTaskInfosDto toDto(HumanTaskInfos humanTaskInfos) {
        Object deserializedObject = deserialize(humanTaskInfos.getRawPayload());

        return new HumanTaskInfosDto(
                humanTaskInfos.getId(),
                humanTaskInfos.getProcessName(),
                humanTaskInfos.getCorrelationId(),
                humanTaskInfos.getStartTime(),
                humanTaskInfos.getPayload(),
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