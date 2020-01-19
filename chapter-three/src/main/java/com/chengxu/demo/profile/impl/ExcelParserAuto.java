package com.chengxu.demo.profile.impl;

import com.chengxu.demo.profile.Parser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ExcelParserAuto implements Parser {
    @Override
    public void parse() {
        System.out.println("ExcelParserAuto.parse......");
    }
}
