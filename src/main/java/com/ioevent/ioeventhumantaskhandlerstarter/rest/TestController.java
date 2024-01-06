package com.ioevent.ioeventhumantaskhandlerstarter.rest;

import com.ioevent.ioeventhumantaskhandlerstarter.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TestController {
    @Autowired
    private Producer producer;

    @GetMapping("/sendMessage")
    public void sendMessage() {
        producer.sendMessage();
    }

    /*@GetMapping("/sendAnotherMessage")
    public void sendAnotherMessage() {
        producer.sendAnotherMessage();
    }*/
}
