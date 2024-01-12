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
@Document(indexName = "#{@environment.getProperty('servers.elasticsearch.prefix','')}human-task")
public class HumanTaskInfos {
    @Id
    private String id;
    private String appName;
    private String processName;
    private String correlationId;
    private String eventType;
    private List<String> input;
    private Map<String ,String> outputs;
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
    @Builder.Default
    private Boolean active= true;
    //private String outputEvent;
    //private String sourceTopic;
}
