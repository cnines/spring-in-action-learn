package com.chengxu.demo.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConditionAutoConfig.class)
public class ConditionAutoConfigTest {

    @Autowired(required = false)
    private MagicBeanAuto magicBeanAuto;

    @Test
    public void test() {
        assertNotNull(magicBeanAuto);
    }
}