package com.chengxu.demo.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/BraveKnight-ioc.xml");
        // AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KnightConfig.class);

        Knight knight = context.getBean(Knight.class);

        knight.embarkOnQuest();

        context.close();
    }
}
