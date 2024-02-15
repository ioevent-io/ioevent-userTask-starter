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

package com.ioevent.ioeventusertaskhandlerstarter.postprocessor;

import com.ioevent.ioeventusertaskhandlerstarter.service.TopicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicBeanPostProcessor implements BeanPostProcessor {
    @Value("${ioevent.application_name:}")
    private String applicationName;

    @Value("${ioevent.prefix:}")
    private String prefix;

    @Value("${spring.kafka.streams.replication-factor:1}")
    private String replicationFactor;

    @Value("${ioevent.partition:1}")
    private int partition;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof TopicService) {
            ((TopicService) bean).createUserTaskTopics(applicationName, prefix, partition, replicationFactor);
        }

        return bean;
    }

}
