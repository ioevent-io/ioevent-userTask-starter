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