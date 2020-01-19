package com.chengxu.demo.aop.impl;

import com.chengxu.demo.aop.Minstrel;
import com.chengxu.demo.ioc.Knight;
import com.chengxu.demo.ioc.Quest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BraveKnightTest {

    @Test
    public void embarkOnQuest() {
        Quest mockQuest = mock(Quest.class);
        Minstrel mockMinstrel = mock(Minstrel.class);
        Knight knight = new BraveKnight(mockQuest, mockMinstrel);
        knight.embarkOnQuest();
        verify(mockQuest, times(1)).embark();
        verify(mockMinstrel, times(1)).singBeforeQuest();
        verify(mockMinstrel, times(1)).singAfterQuest();
    }
}