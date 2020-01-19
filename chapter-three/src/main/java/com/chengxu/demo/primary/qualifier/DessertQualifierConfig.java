package com.chengxu.demo.primary.qualifier;

import com.chengxu.demo.primary.qualifier.annotation.Cold;
import com.chengxu.demo.primary.qualifier.annotation.Creamy;
import com.chengxu.demo.primary.qualifier.annotation.Fruity;
import com.chengxu.demo.primary.qualifier.impl.Cake2;
import com.chengxu.demo.primary.qualifier.impl.Cookies2;
import com.chengxu.demo.primary.qualifier.impl.IceCream2;
import com.chengxu.demo.primary.qualifier.impl.Popsicle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DessertQualifierConfig {

    @Bean
    public Dessert cake(){
        return new Cake2();
    }

    @Bean
    @Qualifier("dry")
    public Dessert cookie(){
        return new Cookies2();
    }

    @Bean
    // @Qualifier("creamy")
    // @Qualifier("cold")
    @Cold
    @Creamy
    public Dessert iceCream(){
        return new IceCream2();
    }

    @Bean
    // @Qualifier("cold")
    // @Qualifier("fruity")
    @Cold
    @Fruity
    public Dessert popsicle() {
        return new Popsicle();
    }
}
