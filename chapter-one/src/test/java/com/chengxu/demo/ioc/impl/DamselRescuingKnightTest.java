package com.chengxu.demo.ioc.impl;

import com.chengxu.demo.ioc.impl.DamselRescuingKnight;
import org.junit.Test;

public class DamselRescuingKnightTest {

    @Test
    public void embarkOnQuest() {
        DamselRescuingKnight knight = new DamselRescuingKnight();
        knight.embarkOnQuest();
    }
}