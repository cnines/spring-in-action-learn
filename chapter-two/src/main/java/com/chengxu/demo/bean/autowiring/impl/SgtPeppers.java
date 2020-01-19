package com.chengxu.demo.bean.autowiring.impl;

import com.chengxu.demo.bean.autowiring.CompactDisc;
import org.springframework.stereotype.Component;

import javax.inject.Named;


/**
 * {@link Component} 和 {@link Named} 在大多数场景中可以互相替换
 * 但是 {@link Named} 语义不够清晰, 所以我们一般都使用 {@link Component}
 */

// 这里使用 @Component 是自动装配, 也可以在配置类中使用 @Bean 注解进行显式装配
// @Component
// @Named("lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {
    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}
