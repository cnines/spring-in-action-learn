package com.chengxu.demo.bean.autowiring;

import com.chengxu.demo.bean.autowiring.impl.CDPlayer;
import com.chengxu.demo.bean.autowiring.impl.SgtPeppers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CDPlayerConfig {

    @Bean
    public CompactDisc sgtPeppers() {
        return new SgtPeppers();
    }

    @Bean
    public MediaPlayer cdPlayer() {
        return new CDPlayer(sgtPeppers());
    }
}
