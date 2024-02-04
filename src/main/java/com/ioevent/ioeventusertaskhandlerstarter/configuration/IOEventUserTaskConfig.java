package com.ioevent.ioeventusertaskhandlerstarter.configuration;

import com.ioevent.ioeventusertaskhandlerstarter.configuration.swagger.SwaggerConfig;
import com.ioevent.ioeventusertaskhandlerstarter.listner.EventListener;
import com.ioevent.ioeventusertaskhandlerstarter.repository.UserTaskInfosRepository;
import com.ioevent.ioeventusertaskhandlerstarter.rest.Controller;
import com.ioevent.ioeventusertaskhandlerstarter.service.UserTaskInfosService;
import com.ioevent.ioeventusertaskhandlerstarter.service.UserTaskInfosServiceImpl;

import com.ioevent.ioeventusertaskhandlerstarter.service.MessageProducerService;
import com.ioevent.ioeventusertaskhandlerstarter.service.MessageProducerServiceImpl;
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
@Import({KafkaConfig.class, ElasticConfig.class, EventListener.class, SwaggerConfig.class})
public class IOEventUserTaskConfig {
    @Value("${ioevent.application_name:}")
    private String applicationName;
    @Value("${ioevent.group_id:ioevent:}")
    private String groupId;

    @Value("${ioevent.prefix:}")
    private String prefix;

    private final UserTaskInfosRepository userTaskInfosRepository;
    @Bean
    public IOEventProperties ioEventProperties() {
        return new IOEventProperties();
    }
    @Bean
    String getApplicationName() {
        return prefix+"-"+applicationName+"_"+"ioevent-human-task";
    }

    @Bean
    String getGroupId() {
        return groupId;
    }

    @Bean
    public UserTaskInfosService humanTaskInfosService() {
        return new UserTaskInfosServiceImpl(userTaskInfosRepository);
    }

    @Bean
    public Controller controller() {
        return new Controller();
    }
    @Bean
    public MessageProducerService messageProducerService(){
        return new MessageProducerServiceImpl();
    }


}
