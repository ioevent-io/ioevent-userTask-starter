package com.ioevent.ioeventhumantaskhandlerstarter.configuration;

import com.ioevent.ioeventhumantaskhandlerstarter.listner.EventListner;
import com.ioevent.ioeventhumantaskhandlerstarter.repository.HumanTaskInfosRepository;
import com.ioevent.ioeventhumantaskhandlerstarter.rest.Controller;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosService;
import com.ioevent.ioeventhumantaskhandlerstarter.service.HumanTaskInfosServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

@Configuration
@EnableKafka
@Service
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = "com.ioevent.ioeventhumantaskhandlerstarter.repository")
@Import({KafkaConfig.class, ElasticConfig.class, EventListner.class})
public class IOEventHumanTaskConfig {
    @Value("${ioevent.application_name:}")
    private String applicationName;
    @Value("${ioevent.group_id:ioevent:}")
    private String groupId;

    private final HumanTaskInfosRepository humanTaskInfosRepository;
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

    @Bean
    public HumanTaskInfosService humanTaskInfosService() {
        return new HumanTaskInfosServiceImpl(humanTaskInfosRepository);
    }

    @Bean
    public Controller controller() {
        return new Controller();
    }


}
