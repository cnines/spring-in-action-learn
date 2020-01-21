package com.chengxu.demo.placeholder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:app.properties")
public class SgtPeppersUsePlaceHolderConfig {

    @Value("${disc.title}")
    private String title;

    @Value("${disc.artist}")
    private String artist;

    @Bean
    public SgtPeppers sgtPeppers() {
        SgtPeppers sgtPeppers = new SgtPeppers();

        sgtPeppers.setTitle(title);
        sgtPeppers.setArtist(artist);

        return sgtPeppers;

    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
