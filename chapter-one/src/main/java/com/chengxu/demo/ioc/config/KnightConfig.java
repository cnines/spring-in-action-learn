package com.chengxu.demo.ioc.config;

import com.chengxu.demo.ioc.Knight;
import com.chengxu.demo.ioc.Quest;
import com.chengxu.demo.ioc.impl.BraveKnight;
import com.chengxu.demo.ioc.impl.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KnightConfig {
    @Bean
    public Knight knight() {
        return new BraveKnight(quest());
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }
}
