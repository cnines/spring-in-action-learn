package com.chengxu.demo.ioc.impl;

import com.chengxu.demo.ioc.Knight;
import com.chengxu.demo.ioc.Quest;
import com.chengxu.demo.ioc.impl.BraveKnight;
import com.chengxu.demo.ioc.impl.RescueDamselQuest;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BraveKnightTest {

    @Test
    public void embarkOnQuest() {
        Quest quest = new RescueDamselQuest();

        Knight knight = new BraveKnight(quest);

        knight.embarkOnQuest();
    }


    @Test
    public void knightShouldEmbarkOnQuest() {
        Quest mockQuest = mock(Quest.class);
        Knight knight = new BraveKnight(mockQuest);
        knight.embarkOnQuest();
        verify(mockQuest, times(1)).embark();
    }
}