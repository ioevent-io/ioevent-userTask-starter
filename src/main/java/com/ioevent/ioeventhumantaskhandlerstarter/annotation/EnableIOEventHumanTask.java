package com.ioevent.ioeventhumantaskhandlerstarter.annotation;

import com.ioevent.ioeventhumantaskhandlerstarter.configuration.IOEventHumanTaskConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(IOEventHumanTaskConfig.class)
public @interface EnableIOEventHumanTask {

}
