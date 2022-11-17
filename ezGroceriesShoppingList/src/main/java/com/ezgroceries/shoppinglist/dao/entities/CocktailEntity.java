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

    public CocktailEntity() {
    }

    public CocktailEntity(String idDrink, String name) {
        this.id = UUID.randomUUID();
        this.idDrink = idDrink;
        this.name = name;
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


}