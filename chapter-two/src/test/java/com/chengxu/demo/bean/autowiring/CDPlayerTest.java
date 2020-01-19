package com.chengxu.demo.bean.autowiring;

import com.chengxu.demo.bean.autowiring.impl.SgtPeppers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// 使用 SpringJUnit4ClassRunner 在测试开始的时候自动创建 Spring 的应用上下文
@RunWith(SpringJUnit4ClassRunner.class)
// 要在 CDPlayerConfig 中加载配置
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

    @Autowired
    private CompactDisc disc;

    // @Rule
    // public final SystemOutRule log = new SystemOutRule().enableLog();
    //
    // @Autowired
    // private MediaPlayer player;

    @Test
    public void testCDShouldNotBeNull() {
        assertNotNull(disc);
    }

    // @Test
    // public void testPlay() {
    //     player.play();
    //     assertEquals("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles\n", log.getLog());
    // }
}