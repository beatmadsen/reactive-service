package com.madsen.rx.first.config;

import com.madsen.rx.first.Cow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erikmadsen2 on 11/03/2016.
 */
@Configuration
public class FirstConfig {

    @Bean
    public Cow state() {
        return new Cow.Simple();
    }
}
