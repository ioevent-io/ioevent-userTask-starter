package com.ioevent.ioeventhumantaskhandlerstarter.configuration;

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