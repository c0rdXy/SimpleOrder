package com.example.simpleorder.entity;

public class Order {

    private String name; // 菜名
    private int price; // 单价
    private int count; // 数量

    public Order(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
