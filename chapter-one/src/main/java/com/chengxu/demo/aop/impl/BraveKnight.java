package com.chengxu.demo.aop.impl;

import com.chengxu.demo.aop.Minstrel;
import com.chengxu.demo.ioc.Knight;
import com.chengxu.demo.ioc.Quest;

public class BraveKnight implements Knight {

    private Quest quest;
    private Minstrel minstrel;

    public BraveKnight(Quest quest, Minstrel minstrel) {
        this.quest = quest;
        this.minstrel = minstrel;
    }


    @Override
    public void embarkOnQuest() {
        minstrel.singBeforeQuest();
        quest.embark();
        minstrel.singAfterQuest();
    }
}
