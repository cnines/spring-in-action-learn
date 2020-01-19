package com.chengxu.demo.ioc.impl;

import com.chengxu.demo.ioc.Knight;
import com.chengxu.demo.ioc.Quest;

// BraveKnight 与 Quest 是松耦合的, 不管是 DamselRescuingQuest/SlayDragonQuest等, 只要实现了 Quest 接口, 都可以进行替换

// 对依赖进行替换的常用方法是测试的时候使用 mock 实现
public class BraveKnight implements Knight {
    // 松耦合的
    private Quest quest;

    // 构造器注入 constructor injection
    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }
}
