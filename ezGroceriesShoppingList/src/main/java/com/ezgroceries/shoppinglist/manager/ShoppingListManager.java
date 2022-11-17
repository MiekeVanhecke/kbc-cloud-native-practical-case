package com.ezgroceries.shoppinglist.manager;

import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShoppingListManager {
    private List<ShoppingListResource> allShoppingListResources;


    public ShoppingListManager() {
        this.allShoppingListResources = new ArrayList<>();
    }

    public String createShoppingList(String name) {
        ShoppingListResource aShoppingListResource = new ShoppingListResource(name);
        allShoppingListResources.add(aShoppingListResource);
        return aShoppingListResource.getShoppingListId();
    }

    public ShoppingListResource getShoppingList(String shoppingListId) {
        for (ShoppingListResource aShoppingListResource : allShoppingListResources) {
            if (aShoppingListResource.getShoppingListId().equals(shoppingListId)) {
                return aShoppingListResource;
            }
        }
        return null;
    }

    public List<ShoppingListResource> getAllShoppingLists() {
        return allShoppingListResources;
    }
}
