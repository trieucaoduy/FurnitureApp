package com.example.furniture.Model;

public class Category {
    private String NAME;
    private String IMAGE;

    public Category() {
    }

    public Category(String name, String image) {
        this.NAME = name;
        this.IMAGE = image;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String name) {
        this.NAME = name;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String image) {
        this.IMAGE = image;
    }
}
