package com.ezgroceries.shoppinglist.controller.contracts;

import java.util.Objects;

public class CocktailRequest {
    private String cocktailId;

    public CocktailRequest() {}
    public CocktailRequest(String cocktailId) {
        this.cocktailId = cocktailId;
    }

    public String getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(String cocktailId) {
        this.cocktailId = cocktailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CocktailRequest that = (CocktailRequest) o;
        return Objects.equals(cocktailId, that.cocktailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cocktailId);
    }
}
