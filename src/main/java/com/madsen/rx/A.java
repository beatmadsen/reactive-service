package com.madsen.rx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by erikmadsen on 24/01/2015.
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class A extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {

        return application.sources(A.class, X.class);
    }


    public static void main(String[] args) {

        final ApplicationContext context = SpringApplication.run(A.class, args);
    }
}

