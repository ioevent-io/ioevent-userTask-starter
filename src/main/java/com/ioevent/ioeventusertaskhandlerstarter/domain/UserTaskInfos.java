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

package com.ioevent.ioeventusertaskhandlerstarter.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Document(indexName = "#{@environment.getProperty('servers.elasticsearch.prefix','')}user-task")
public class UserTaskInfos {
    @Id
    private String id;
    private String appName;
    private String processName;
    private String correlationId;
    private String eventType;
    private List<String> input;
    private String outputEvent;
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
}
