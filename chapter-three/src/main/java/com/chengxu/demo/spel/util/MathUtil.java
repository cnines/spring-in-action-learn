package com.chengxu.demo.spel.util;

public abstract class MathUtil {
    public static Integer getBigNum(Integer a, Integer b) {
        return a >= b ? a : b;
    }

    public static Integer getNum() {
        return 2;
    }
}
