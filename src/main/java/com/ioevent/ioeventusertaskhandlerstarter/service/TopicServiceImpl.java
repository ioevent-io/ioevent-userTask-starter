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

package com.ioevent.ioeventusertaskhandlerstarter.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TopicServiceImpl implements TopicService{
    @Autowired
    private AdminClient client;

    @Override
    public void createUserTaskTopics(String appName, String prefix, int partition, String replication) {
        String prefixTopic = prefix.isEmpty() ? "" : prefix+"-";
        String cleanAppName = Utils.cleanAppName(appName);
        NewTopic userTaskRequest = new NewTopic(prefixTopic+cleanAppName+"_"+"ioevent-user-task", partition, Short.parseShort(replication));
        NewTopic userTaskResponse = new NewTopic(prefixTopic+cleanAppName+"_"+"ioevent-user-task-Response", partition, Short.parseShort(replication));
        client.createTopics(Arrays.asList(userTaskRequest, userTaskResponse));
    }
}
