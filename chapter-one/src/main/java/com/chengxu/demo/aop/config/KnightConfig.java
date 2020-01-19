package com.chengxu.demo.aop.config;

import com.chengxu.demo.aop.Minstrel;
import com.chengxu.demo.aop.impl.BraveKnight;
import com.chengxu.demo.ioc.Knight;
import com.chengxu.demo.ioc.Quest;
import com.chengxu.demo.ioc.impl.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class KnightConfig {
    @Bean
    public Minstrel minstrel() {
        return new Minstrel(System.out);
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }

    @Bean
    public Knight knight() {
        return new BraveKnight(quest(), minstrel());
    }
}

