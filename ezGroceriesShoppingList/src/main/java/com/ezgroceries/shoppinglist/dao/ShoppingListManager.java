package com.ezgroceries.shoppinglist.dao;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListManager {
    private List<ShoppingList> allShoppingLists;


    public ShoppingListManager() {
        this.allShoppingLists = new ArrayList<>();
    }

    public String createShoppingList(String name) {
        ShoppingList aShoppingList = new ShoppingList(name);
        allShoppingLists.add(aShoppingList);
        return aShoppingList.getShoppingListId();
    }

    public ShoppingList getShoppingList(String shoppingListId) {
        for (ShoppingList aShoppingList:allShoppingLists) {
            if (aShoppingList.getShoppingListId().equals(shoppingListId)) {
                return aShoppingList;
            }
        }
        return null;
    }
}
