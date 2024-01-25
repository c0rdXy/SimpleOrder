package com.example.simpleorder.entity;

public class Menu {

    private String name; // 菜名
    private int price; // 价格

    public Menu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}

