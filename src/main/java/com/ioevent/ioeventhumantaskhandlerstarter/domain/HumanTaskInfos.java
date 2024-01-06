package com.ioevent.ioeventhumantaskhandlerstarter.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HumanTaskInfos {
    private String id;
    private String correlationId;
    private String apiKey;
    private String processName;
    private String eventType;
    private String stepName;
    private String input;
    private String outputEvent;
    private String sourceTopic;
    private Long startTime;
    private Long instanceStartTime;
}
