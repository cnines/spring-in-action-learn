package com.chengxu.demo.placeholder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SgtPeppersUsePlaceHolderConfig.class)
public class SgtPeppersUsePlaceHolderConfigTest {

    @Autowired
    private SgtPeppers sgtPeppers;

    @Test
    public void test() {
        assertNotNull(sgtPeppers);
        assertEquals("Sgt. Peppers Lonely Hearts Club Band",sgtPeppers.getTitle());
        assertEquals("The Beatles",sgtPeppers.getArtist());
    }

}