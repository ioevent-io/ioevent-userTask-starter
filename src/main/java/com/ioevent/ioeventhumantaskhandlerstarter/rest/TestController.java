package com.ioevent.ioeventhumantaskhandlerstarter.rest;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.producer.Producer;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TestController {
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
}
