package com.ioevent.ioeventhumantaskhandlerstarter.rest;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.dto.HumanTaskInfosDto;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import com.ioevent.ioeventhumantaskhandlerstarter.service.MessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ioevent/humanTaskInfos")
@Slf4j
public class Controller {
    @Autowired
    private HumanTaskInfosService humanTaskInfosService;

    @Autowired
    private MessageProducerService messageProducerService;

    @GetMapping("/allWaiting")
    public List<HumanTaskInfos> getAllWaitingHumanTasks() {
        return humanTaskInfosService.getAll();
    }

    @GetMapping("/userTasks/{processName}")
    public List<HumanTaskInfosDto> getAllWaitingHumanTasksByProcessName(@PathVariable String processName) {
        return humanTaskInfosService.getByProcessName(processName).stream().map(HumanTaskInfosDto::toDto).collect(Collectors.toList());
    }
    @GetMapping("/userTask")
    public HumanTaskInfosDto getHumanTaskById(@RequestParam String id) {
        log.info("userTaskkk");
        log.info(humanTaskInfosService.getById(id).orElse(null).getOutputs().keySet().toString());
        log.info(humanTaskInfosService.getById(id).orElse(null).getOutputs().values().toString());
        return HumanTaskInfosDto.toDto(humanTaskInfosService.getById(id).orElse(null));
    }

    @PostMapping("/achieve-userTask")
    public String resumeUserTask(@RequestParam String id,@RequestPart(value = "payload",required = false) MultipartFile payload,
                                 @RequestPart Object payloadObject,
                                 @RequestPart(value = "customHeaders",required = false) Map<String,String> customHeaders,
                                 @RequestPart(value = "outputString",required = false) String outputString) {
        byte[] payloadBytes = null;
        byte[] defaultPayloadBytes = "defaultPayload".getBytes();
        if (payload != null) {
            try {
                payloadBytes = payload.getInputStream().readAllBytes();
            } catch (Exception e) {
                log.error("Error while converting payload to bytes", e);
            }
        }
        if(customHeaders != null){
            log.info("customHeaders: " + customHeaders.toString());
        }
        if (outputString != null) {
            log.info("outputString: " + outputString);
        }
        if (payloadBytes == null) {
            payloadBytes = defaultPayloadBytes;
        }
        return messageProducerService.sendMessage(id, payloadBytes,customHeaders,outputString);
    }

    /*@PostMapping("/achieve-userTask")
    public String resumeUserTask(@RequestParam String id,@ModelAttribute UserResponse userResponse) {
        byte[] payloadBytes = null;
        if (userResponse.payload != null) {
            try {
                payloadBytes = userResponse.payload.getInputStream().readAllBytes();
            } catch (Exception e) {
                log.error("Error while converting payload to bytes", e);
            }
        }
        //log.info("customHeaders: " + userResponse.customHeaders.toString());
        log.info("outputString: " + userResponse.outputString);
        return messageProducerService.sendMessage(id, payloadBytes);
    }*/
}