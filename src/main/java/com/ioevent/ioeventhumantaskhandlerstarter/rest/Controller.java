package com.ioevent.ioeventhumantaskhandlerstarter.rest;

import com.ioevent.ioeventhumantaskhandlerstarter.dto.HumanTaskInfosDto;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import com.ioevent.ioeventhumantaskhandlerstarter.service.MessageProducerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ioevent/human-task-infos")
@Slf4j
public class Controller {
    @Autowired
    private HumanTaskInfosService humanTaskInfosService;

    @Autowired
    private MessageProducerService messageProducerService;

    @Operation(summary = "Get all waiting human tasks for all workflows ")
    @GetMapping("/waiting-tasks")
    public List<HumanTaskInfosDto> getAllWaitingHumanTasks() {
        return humanTaskInfosService.getAll().stream().map(HumanTaskInfosDto::toDto).toList();
    }

    @Operation(summary = "Get all waiting human tasks for a specific workflow by giving the workflow name")
    @GetMapping("/waiting-tasks/{processName}")
    public List<HumanTaskInfosDto> getAllWaitingHumanTasksByProcessName(@PathVariable String processName) {
        return humanTaskInfosService.getByProcessNameAndActiveTrue(processName).stream().map(HumanTaskInfosDto::toDto).toList();
    }

    @Operation(summary = "Get a waiting human task by giving the id")
    @GetMapping("/waiting-task")
    public HumanTaskInfosDto getHumanTaskById(@RequestParam String id) {
        return HumanTaskInfosDto.toDto(Objects.requireNonNull(humanTaskInfosService.getById(id).orElse(null)));
    }

    @Operation(summary = "Finishing a waiting human task by giving the id and the payload as a required parameters " +
            "and the customHeaders and the outputString as optional parameters")
    @PostMapping("/complete-task")
    public String resumeUserTask(@RequestParam String id,@RequestBody Object payload,@RequestPart(value = "customHeaders",required = false) Map<String,String> customHeaders) {
        if(payload == null){
            return "The payload is required";
        }
        return messageProducerService.sendMessage(id, payload,customHeaders,"outputString");
    }

    /*@Operation(summary = "Finishing a waiting human task by giving the id and the payload as a required parameters " +
            "and the customHeaders and the outputString as optional parameters")
    @PostMapping("/complete-task-response")
    public String resumeUserTask1(@RequestParam String id,@RequestBody Object payload,@RequestPart(value = "customHeaders",required = false) Map<String,String> customHeaders) {
        if(payload == null){
            return "The payload is required";
        }
        return messageProducerService.sendMessage1(id, payload,customHeaders,"outputString");
    }*/
}