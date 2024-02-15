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

package com.ioevent.ioeventusertaskhandlerstarter.rest;

import com.ioevent.ioeventusertaskhandlerstarter.dto.UserTaskInfosDto;
import com.ioevent.ioeventusertaskhandlerstarter.service.UserTaskInfosService;
import com.ioevent.ioeventusertaskhandlerstarter.service.MessageProducerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ioevent/user-task-infos")
@Slf4j
public class Controller {
    @Autowired
    private UserTaskInfosService userTaskInfosService;

    @Autowired
    private MessageProducerService messageProducerService;

    @Operation(summary = "Get all waiting user tasks for all workflows ")
    @GetMapping("/waiting-tasks")
    public List<UserTaskInfosDto> getAllWaitingUserTasks() {
        return userTaskInfosService.getAll().stream().map(UserTaskInfosDto::toDto).toList();
    }

    @Operation(summary = "Get all waiting user tasks for a specific workflow by giving the workflow name")
    @GetMapping("/waiting-tasks/{processName}")
    public List<UserTaskInfosDto> getAllWaitingUserTasksByProcessName(@PathVariable String processName) {
        return userTaskInfosService.getByProcessNameAndActiveTrue(processName).stream().map(UserTaskInfosDto::toDto).toList();
    }

    @Operation(summary = "Get a waiting user task by giving the id")
    @GetMapping("/waiting-task")
    public UserTaskInfosDto getUserTaskById(@RequestParam String id) {
        return UserTaskInfosDto.toDto(Objects.requireNonNull(userTaskInfosService.getById(id).orElse(null)));
    }

    @Operation(summary = "Finishing a waiting user task by giving the id and the payload as a required parameters " +
            "and the customHeaders and the outputString as optional parameters")
    @PostMapping("/complete-task")
    public String resumeUserTask(@RequestParam String id,@RequestBody Object payload,@RequestPart(value = "customHeaders",required = false) Map<String,String> customHeaders) {
        if(payload == null){
            return "The payload is required";
        }
        return messageProducerService.sendMessage(id, payload,customHeaders);
    }

    @Operation(summary = "Deactivating all waiting user tasks (of all types)")
    @PostMapping("/purgeAll")
    public void purgeAll() {
        userTaskInfosService.purgeAll();
    }

}