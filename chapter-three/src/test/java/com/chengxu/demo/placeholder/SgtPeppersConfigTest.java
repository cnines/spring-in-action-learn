package com.chengxu.demo.placeholder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 这里是中文字体
// 还是这么难看呀
@ContextConfiguration(classes = SgtPeppersConfig.class)
public class SgtPeppersConfigTest {

    @Autowired
    private SgtPeppers sgtPeppers;

    @Test
    public void test() {
        assertNotNull(sgtPeppers);
        assertEquals("Sgt. Peppers Lonely Hearts Club Band",sgtPeppers.getTitle());
        assertEquals("The Beatles",sgtPeppers.getArtist());
    }

}