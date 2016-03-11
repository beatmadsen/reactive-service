package com.madsen.rx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

/**
 * Created by erikmadsen on 24/01/2015.
 */

@Configuration
@ComponentScan("com.madsen")
@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync
public class ReactiveApp extends SpringBootServletInitializer {

    public static void main(final String[] args) {

        final ApplicationContext ctx = SpringApplication.run(ReactiveApp.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        final String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (final String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}

