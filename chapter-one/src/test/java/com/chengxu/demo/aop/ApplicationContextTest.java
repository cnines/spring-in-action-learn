package com.chengxu.demo.aop;

import com.chengxu.demo.aop.config.KnightConfig;
import com.chengxu.demo.ioc.Knight;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextTest {

    @Test
    public void testClassPathXmlApplicationContextGetBean() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/BraveKnight-aop.xml");

        Knight knight = context.getBean(Knight.class);

        knight.embarkOnQuest();

        context.close();

    }

    @Test
    public void testAnnotationConfigApplicationContextGetBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KnightConfig.class);

        Knight knight = context.getBean(Knight.class);

        knight.embarkOnQuest();

        context.close();

    }
}