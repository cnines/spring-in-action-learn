package com.chengxu.demo.spel.entity;

public class Sing {
    private String name;
    private String author;

    public Sing(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Sing() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
