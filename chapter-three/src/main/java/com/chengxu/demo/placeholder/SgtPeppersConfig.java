package com.chengxu.demo.placeholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:app.properties")
public class SgtPeppersConfig {

    @Autowired
    private Environment environment;

    @Bean
    public SgtPeppers sgtPeppers() {
        return new SgtPeppers(environment.getProperty("disc.title"),environment.getProperty("disc.artist"));
    }
}
