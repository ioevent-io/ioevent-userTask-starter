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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ioevent")
public class IOEventProperties {
    private String prefix = "";
    private String group_id= "ioevent";
    private String api_key = "";
    private String application_name = "";

    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getGroup_id() {
        return group_id;
    }
    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
    public String getApi_key() {
        return api_key;
    }
    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
    public String getApplication_name() {
        return application_name;
    }
    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

}