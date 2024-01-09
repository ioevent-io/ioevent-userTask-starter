package com.ioevent.ioeventhumantaskhandlerstarter.rest;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.dto.HumanTaskInfosDto;
import com.ioevent.ioeventhumantaskhandlerstarter.producer.Producer;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class Controller {
    @Autowired
    private Producer producer;

    @Autowired
    private HumanTaskInfosService humanTaskInfosService;

    @GetMapping("/sendMessage")
    public void sendMessage() {
        producer.sendMessage();
    }

    /*@GetMapping("/sendAnotherMessage")
    public void sendAnotherMessage() {
        producer.sendAnotherMessage();
    }*/
    @GetMapping("/getAllWaitingHumanTasks")
    public List<HumanTaskInfos> getAllWaitingHumanTasks() {
        return humanTaskInfosService.getAll();
    }

    /*@GetMapping("/userTasks/{processName}")
    public Page<HumanTaskInfosDto> getAllWaitingHumanTasksByProcessName(String processName, @RequestParam int pageNumber,@RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return humanTaskInfosService.getByProcessName(processName,pageable).map(HumanTaskInfosDto::toDto);
    }*/
    @GetMapping("/userTasks/{processName}")
    public List<HumanTaskInfosDto> getAllWaitingHumanTasksByProcessName(@PathVariable String processName) {
        return humanTaskInfosService.getByProcessName(processName).stream().map(HumanTaskInfosDto::toDto).collect(Collectors.toList());
    }
}