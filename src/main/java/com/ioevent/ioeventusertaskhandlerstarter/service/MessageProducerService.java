package com.ioevent.ioeventusertaskhandlerstarter.service;

import java.util.Map;

public interface MessageProducerService {
    String sendMessage(String id, Object payload, Map<String, String> customHeaders);
}
