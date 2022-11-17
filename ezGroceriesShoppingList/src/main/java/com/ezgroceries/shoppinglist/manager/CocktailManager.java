package com.ezgroceries.shoppinglist.manager;

import com.ezgroceries.shoppinglist.dao.CocktailResource;
import com.ezgroceries.shoppinglist.dao.CocktailDBResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CocktailManager {

    private List<CocktailResource> allCocktailResources;

    public CocktailManager() {
        List<CocktailResource> cocktailResources = new ArrayList<>();

        cocktailResources.add(new CocktailResource("23b3d85a-3928-41c0-a533-6538a71e17c4",
                "Margerita",
                "Cocktail glass",
                "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                Arrays.asList("Tequila", "Triple sec","Lime juice", "Salt")));
        cocktailResources.add(new CocktailResource("d615ec78-fe93-467b-8d26-5d26d8eab073",
                "Blue Margerita",
                "Cocktail glass",
                "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                Arrays.asList("Tequila", "Blue Curacao","Lime juice", "Salt")));


        this.allCocktailResources = cocktailResources;
    }

    public List<CocktailResource> getAllCocktails() {
        return allCocktailResources;
    }

    public CocktailResource getCocktail(String cocktailId) {
        for (CocktailResource aCocktailResource : allCocktailResources) {
            if (aCocktailResource.getCocktailId().equals(cocktailId)) return aCocktailResource;
        }
        return null;
    }

    public void createCocktailList(CocktailDBResponse cocktailDBResponse) {
        List<CocktailDBResponse.DrinkResource> drinks = cocktailDBResponse.getDrinks();
        for (CocktailDBResponse.DrinkResource aDrink : drinks) {
            List<String> ingredients = aDrink.getAllIngredients();
            CocktailResource cocktailResource = new CocktailResource(aDrink.getIdDrink(), aDrink.getStrDrink(), aDrink.getStrGlass(), aDrink.getStrInstructions(), aDrink.getStrDrinkThumb(), ingredients);
            allCocktailResources.add(cocktailResource);
        }
    }
    public List<CocktailResource> searchCocktails(String cocktailString) {
        List<CocktailResource> allCocktailsMatchSearches = new ArrayList<>();
        for (CocktailResource aCocktailResource : allCocktailResources) {
            if (aCocktailResource.getName().toLowerCase().indexOf(cocktailString.toLowerCase())>-1) allCocktailsMatchSearches.add(aCocktailResource);
        }
        return allCocktailsMatchSearches;
    }
}
