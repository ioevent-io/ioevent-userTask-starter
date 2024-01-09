package com.ioevent.ioeventhumantaskhandlerstarter.rest;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.dto.HumanTaskInfosDto;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class Controller {
    @Autowired
    private HumanTaskInfosService humanTaskInfosService;

    @GetMapping("/getAllWaitingHumanTasks")
    public List<HumanTaskInfos> getAllWaitingHumanTasks() {
        return humanTaskInfosService.getAll();
    }


    @GetMapping("/userTasks/{processName}")
    public List<HumanTaskInfosDto> getAllWaitingHumanTasksByProcessName(@PathVariable String processName) {
        return humanTaskInfosService.getByProcessName(processName).stream().map(HumanTaskInfosDto::toDto).collect(Collectors.toList());
    }
}