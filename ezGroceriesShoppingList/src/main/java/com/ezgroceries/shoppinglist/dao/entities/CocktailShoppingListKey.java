package com.ezgroceries.shoppinglist.dao.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class CocktailShoppingListKey implements Serializable {

    @Column(name = "COCKTAIL_ID")
    UUID cocktailId;

    @Column(name = "SHOPPING_LIST_ID")
    UUID shoppingListId;

    public CocktailShoppingListKey(UUID cocktailId, UUID shoppingListId) {
        this.cocktailId = cocktailId;
        this.shoppingListId = shoppingListId;
    }

    public CocktailShoppingListKey() {
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
}
