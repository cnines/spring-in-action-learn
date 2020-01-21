package com.chengxu.demo.spel;

import com.chengxu.demo.spel.entity.InstA;
import com.chengxu.demo.spel.entity.Sing;
import com.chengxu.demo.spel.entity.UseInstA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GlobalConfig {

    @Bean
    public InstA instA() {
        InstA instA = new InstA();
        instA.setName("cnines");
        instA.setAge(22);
        instA.setEmail("mddwy1997@163.com");
        return instA;
    }

    @Bean
    public UseInstA useInstA(InstA instA) {
        UseInstA useInstA = new UseInstA();
        useInstA.setInst(instA);
        useInstA.setTitles(Arrays.asList("spring", "java", "python"));

        useInstA.setSings(Arrays.asList(
                new Sing("凡人歌", "李宗盛"),
                new Sing("遇见", "孙燕姿"),
                new Sing("小半", "陈粒")
        ));
        return useInstA;
    }
}
