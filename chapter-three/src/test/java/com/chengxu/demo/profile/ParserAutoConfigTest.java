package com.chengxu.demo.profile;

import com.chengxu.demo.profile.impl.ExcelParserAuto;
import com.chengxu.demo.profile.impl.PPTParserAuto;
import com.chengxu.demo.profile.impl.WordParserAuto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParserAutoConfig.class)
@ActiveProfiles("dev")
public class ParserAutoConfigTest {

    @Autowired(required = false)
    private WordParserAuto wordParserAuto;

    @Autowired(required = false)
    private ExcelParserAuto excelParserAuto;

    @Autowired(required = false)
    private PPTParserAuto pptParserAuto;

    @Test
    public void testAutowiredIsNull() {
        assertNotNull(wordParserAuto);
        assertNull(excelParserAuto);
        assertNull(pptParserAuto);
    }
}