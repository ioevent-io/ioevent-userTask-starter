package com.ioevent.ioeventhumantaskhandlerstarter.dto;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public static HumanTaskInfosDto toDto(HumanTaskInfos humanTaskInfos) {
        return new HumanTaskInfosDto(humanTaskInfos.getId(), humanTaskInfos.getProcessName(), humanTaskInfos.getCorrelationId(), humanTaskInfos.getStartTime(), humanTaskInfos.getPayload());
    }
}
