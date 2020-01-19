package com.chengxu.demo.primary.qualifier;

import com.chengxu.demo.primary.qualifier.annotation.Cold;
import com.chengxu.demo.primary.qualifier.annotation.Fruity;
import com.chengxu.demo.primary.qualifier.impl.Cake2;
import com.chengxu.demo.primary.qualifier.impl.Cookies2;
import com.chengxu.demo.primary.qualifier.impl.Popsicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DessertQualifierConfig.class)
public class DessertQualifierConfigTest {

    @Autowired
    @Cold
    @Fruity
    // @Qualifier("dry")
    private Dessert dessert;

    @Test
    public void test() {
        // assertTrue(dessert instanceof Cookies2);
        assertTrue(dessert instanceof Popsicle);
    }
}