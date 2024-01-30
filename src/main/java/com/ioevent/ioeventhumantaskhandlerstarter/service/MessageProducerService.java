package com.ioevent.ioeventhumantaskhandlerstarter.service;

import java.util.Map;

public interface MessageProducerService {
    String sendMessage(String id, Object payload, Map<String, String> customHeaders);
}
