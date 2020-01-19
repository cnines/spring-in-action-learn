package com.chengxu.demo.primary.qualifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DessertPrimaryConfig.class)
public class DessertPrimaryConfigTest {
    @Autowired(required = false)
    private Dessert dessert;

    @Test
    public void test() {
        Assert.assertNotNull(dessert);
    }
}