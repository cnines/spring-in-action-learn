package com.chengxu.demo.profile;

import com.chengxu.demo.profile.impl.ExcelParser;
import com.chengxu.demo.profile.impl.PPTParser;
import com.chengxu.demo.profile.impl.WordParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ParserConfig {
    @Bean
    @Profile("dev")
    public WordParser wordParser() {
        return new WordParser();
    }

    @Bean
    @Profile("dev")
    public ExcelParser excelParser() {
        return new ExcelParser();
    }

    @Bean
    @Profile("dev")
    public PPTParser pptParser() {
        return new PPTParser();
    }
}
