package com.ezgroceries.shoppinglist.controller.contracts;

public class ShoppingRequest {
    private String name;

    public ShoppingRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
