package com.ezgroceries.shoppinglist.manager;

import com.ezgroceries.shoppinglist.dao.ShoppingList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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

    public List<ShoppingList> getAllShoppingLists() {
        return allShoppingLists;
    }
}
