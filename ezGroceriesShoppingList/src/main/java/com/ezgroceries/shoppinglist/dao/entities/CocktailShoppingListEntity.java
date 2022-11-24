package com.ezgroceries.shoppinglist.dao.entities;

import javax.persistence.*;

@Entity
@Table(name = "COCKTAIL_SHOPPING_LIST")
public class CocktailShoppingListEntity {

    @EmbeddedId
    CocktailShoppingListKey id;

    @ManyToOne
    @MapsId("cocktailId")
    @JoinColumn(name = "COCKTAIL_ID")
    CocktailEntity cocktail;

    @ManyToOne
    @MapsId("shoppingListId")
    @JoinColumn(name = "SHOPPING_LIST_ID")
    ShoppingListEntity shoppingList;

    public CocktailShoppingListEntity() {
    }

    public CocktailShoppingListEntity(CocktailShoppingListKey cocktailShoppingListKey, CocktailEntity cocktail, ShoppingListEntity shoppingList) {
        this.id = cocktailShoppingListKey;
        this.cocktail = cocktail;
        this.shoppingList = shoppingList;
    }

    public CocktailShoppingListKey getId() {
        return id;
    }

    public void setId(CocktailShoppingListKey id) {
        this.id = id;
    }

    public CocktailEntity getCocktail() {
        return cocktail;
    }

    public void setCocktail(CocktailEntity cocktail) {
        this.cocktail = cocktail;
    }

    public ShoppingListEntity getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingListEntity shoppingList) {
        this.shoppingList = shoppingList;
    }

}
