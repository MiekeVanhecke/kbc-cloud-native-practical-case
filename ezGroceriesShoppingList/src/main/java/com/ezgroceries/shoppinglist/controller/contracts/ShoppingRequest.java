package com.ezgroceries.shoppinglist.controller.contracts;

import java.util.Objects;
public class ShoppingRequest {
    private String name;

    public ShoppingRequest() {}
    public ShoppingRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingRequest that = (ShoppingRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
