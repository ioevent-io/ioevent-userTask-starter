package com.ioevent.ioeventhumantaskhandlerstarter;

import com.ioevent.ioeventhumantaskhandlerstarter.configuration.IOEventProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(IOEventProperties.class)
public class IoeventHumanTaskHandlerStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoeventHumanTaskHandlerStarterApplication.class, args);
	}

}