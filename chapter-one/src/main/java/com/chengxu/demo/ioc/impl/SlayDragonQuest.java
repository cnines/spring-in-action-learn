package com.chengxu.demo.ioc.impl;

import com.chengxu.demo.ioc.Quest;

import java.io.PrintStream;

public class SlayDragonQuest implements Quest {

    private PrintStream stream;

    public SlayDragonQuest(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void embark() {
        stream.println("SlayDragonQuest.embark......");
    }
}
