package com.chengxu.demo.spel.entity;

import com.chengxu.demo.spel.entity.InstA;

import java.util.List;

public class UseInstA {

    private InstA inst;

    private List<String> titles;

    private List<Sing> sings;

    public List<Sing> getSings() {
        return sings;
    }

    public void setSings(List<Sing> sings) {
        this.sings = sings;
    }

    public InstA getInst() {
        return inst;
    }

    public void setInst(InstA inst) {
        this.inst = inst;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
