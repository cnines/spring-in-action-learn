package com.chengxu.demo.ioc.impl;

import com.chengxu.demo.ioc.Knight;

/**
 * 营救少女的骑士
 */
public class DamselRescuingKnight implements Knight {

    // 强耦合
    private RescueDamselQuest quest;


    public DamselRescuingKnight() {
        this.quest = new RescueDamselQuest();
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }
}
