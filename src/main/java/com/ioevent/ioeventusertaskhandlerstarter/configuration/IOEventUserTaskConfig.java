/*
 * Copyright © 2024 CodeOnce Software (https://www.codeonce.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ioevent.ioeventusertaskhandlerstarter.configuration;

import com.ioevent.ioeventusertaskhandlerstarter.configuration.swagger.SwaggerConfig;
import com.ioevent.ioeventusertaskhandlerstarter.listner.EventListener;
import com.ioevent.ioeventusertaskhandlerstarter.postprocessor.TopicBeanPostProcessor;
import com.ioevent.ioeventusertaskhandlerstarter.repository.UserTaskInfosRepository;
import com.ioevent.ioeventusertaskhandlerstarter.rest.Controller;
import com.ioevent.ioeventusertaskhandlerstarter.service.*;

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
@EnableElasticsearchRepositories(basePackages = "com.ioevent.ioeventusertaskhandlerstarter.repository")
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
        String prefixTopic = prefix.isEmpty() ? "" : prefix+"-";
        return prefixTopic+Utils.cleanAppName(applicationName)+"_"+"ioevent-user-task";
    }

    @Bean
    String getGroupId() {
        return groupId;
    }

    @Bean
    public UserTaskInfosService userTaskInfosService() {
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

    @Bean
    public TopicService topicService(){
        return new TopicServiceImpl();
    }

    @Bean
    public TopicBeanPostProcessor topicBeanPostProcessor(){
        return new TopicBeanPostProcessor();
    }

}
