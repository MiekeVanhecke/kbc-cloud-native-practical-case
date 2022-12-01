package com.ezgroceries.shoppinglist.dao.entities;

import com.ezgroceries.shoppinglist.dao.entities.util.StringListConverter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "COCKTAIL")
public class CocktailEntity {
    @Id
    private UUID id;
    @Column(name = "ID_DRINK")
    private String idDrink;
    private String name;
    @Convert(converter = StringListConverter.class)
    private List<String> ingredients= new ArrayList<>();
    private String glass;
    private String instructions;
    private String image;
    @OneToMany(mappedBy = "cocktail")
    Set<CocktailShoppingListEntity> cocktailShoppingListEntities;

    public CocktailEntity() {
    }

    public CocktailEntity(String idDrink, String name, String glass, String instructions, String image) {
        this.id = UUID.randomUUID();
        this.idDrink = idDrink;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String id_drink) {
        this.idDrink = id_drink;
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

    public Set<CocktailShoppingListEntity> getCocktailShoppingListEntities() {
        return cocktailShoppingListEntities;
    }

    public void setCocktailShoppingListEntities(Set<CocktailShoppingListEntity> cocktailShoppingListEntities) {
        this.cocktailShoppingListEntities = cocktailShoppingListEntities;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}