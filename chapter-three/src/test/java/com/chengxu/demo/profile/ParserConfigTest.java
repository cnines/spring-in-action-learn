package com.chengxu.demo.profile;

import com.chengxu.demo.profile.impl.ExcelParser;
import com.chengxu.demo.profile.impl.PPTParser;
import com.chengxu.demo.profile.impl.WordParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParserConfig.class)
@ActiveProfiles("dev")
public class ParserConfigTest {

    @Autowired(required = false)
    private WordParser wordParser;

    @Autowired(required = false)
    private ExcelParser excelParser;

    @Autowired(required = false)
    private PPTParser pptParser;

    @Test
    public void testAutowiredIsNull() {
        assertNotNull(wordParser);
        assertNotNull(excelParser);
        assertNotNull(pptParser);
    }

}