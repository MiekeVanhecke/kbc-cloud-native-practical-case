package com.ezgroceries.shoppinglist.dao.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SHOPPING_LIST")
public class ShoppingListEntity {
    @Id
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "shoppingList")
    private Set<CocktailShoppingListEntity> cocktailShoppingListEntities;

    public ShoppingListEntity() {
    }

    public ShoppingListEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CocktailShoppingListEntity> getCocktailShoppingListEntities() {
        return cocktailShoppingListEntities;
    }

    public void setCocktailShoppingListEntities(Set<CocktailShoppingListEntity> cocktailShoppingListEntities) {
        this.cocktailShoppingListEntities = cocktailShoppingListEntities;
    }
}
