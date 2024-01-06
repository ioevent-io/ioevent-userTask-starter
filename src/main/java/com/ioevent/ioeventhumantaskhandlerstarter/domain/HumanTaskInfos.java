package com.ioevent.ioeventhumantaskhandlerstarter.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Document(indexName = "#{@environment.getProperty('servers.elasticsearch.prefix','')}humanTask")
public class HumanTaskInfos {
    @Id
    private String id;
    private String processName;
    private String correlationId;
    private String eventType;
    private List<String> input;
    Map<String ,String> outputs;
    private String stepName;
    private String apiKey;
    private Long startTime;
    private Long instanceStartTime;
    @Builder.Default
    private Boolean isImplicitStart = false;
    @Builder.Default
    private Boolean isImplicitEnd = false;
    private String payload;
    private byte[] rawPayload;
    //private String outputEvent;
    //private String sourceTopic;
}
