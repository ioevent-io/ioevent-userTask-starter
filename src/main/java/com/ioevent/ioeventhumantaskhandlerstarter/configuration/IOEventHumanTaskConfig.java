package com.ioevent.ioeventhumantaskhandlerstarter.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
@RequiredArgsConstructor
public class IOEventHumanTaskConfig {
    @Value("${ioevent.application_name:}")
    private String applicationName;
    @Value("${ioevent.group_id:ioevent:}")
    private String groupId;
    @Bean
    public IOEventProperties ioEventProperties() {
        return new IOEventProperties();
    }
    @Bean
    String getApplicationName() {
        return applicationName+"_"+"ioevent-human-task";
    }

    @Bean
    String getGroupId() {
        return groupId;
    }
}
