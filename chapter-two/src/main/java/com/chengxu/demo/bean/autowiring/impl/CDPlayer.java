package com.chengxu.demo.bean.autowiring.impl;

import com.chengxu.demo.bean.autowiring.CompactDisc;
import com.chengxu.demo.bean.autowiring.MediaPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component
public class CDPlayer implements MediaPlayer {

    // @Autowired
    private CompactDisc disc;

    public CDPlayer(CompactDisc disc) {
        this.disc = disc;
    }

    @Override
    public void play() {
        disc.play();
    }
}
