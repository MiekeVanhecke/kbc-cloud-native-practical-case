package com.ezgroceries.shoppinglist.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingList {


    private String shoppingListId;
    private String name;
    private List<String> ingredients;


    public ShoppingList(String name) {
        this.shoppingListId = String.valueOf(UUID.randomUUID());
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredients (List<String> ingredients){
        ingredients.addAll(ingredients);
    }
    public String getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}
