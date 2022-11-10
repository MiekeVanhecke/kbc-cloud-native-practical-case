package com.ezgroceries.shoppinglist.manager;

import com.ezgroceries.shoppinglist.dao.Cocktail;
import com.ezgroceries.shoppinglist.dao.CocktailDBResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class CocktailManager {

    private List<Cocktail> allCocktails;

    public CocktailManager() {
        List<Cocktail> cocktails = new ArrayList<>();

        cocktails.add(new Cocktail("23b3d85a-3928-41c0-a533-6538a71e17c4",
                "Margerita",
                "Cocktail glass",
                "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                Arrays.asList("Tequila", "Triple sec","Lime juice", "Salt")));
        cocktails.add(new Cocktail("d615ec78-fe93-467b-8d26-5d26d8eab073",
                "Blue Margerita",
                "Cocktail glass",
                "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                Arrays.asList("Tequila", "Blue Curacao","Lime juice", "Salt")));


        this.allCocktails = cocktails;
    }

    public List<Cocktail> getAllCocktails() {
        return allCocktails;
    }

    public Cocktail getCocktail(String cocktailId) {
        for (Cocktail aCocktail : allCocktails) {
            if (aCocktail.getCocktailId().equals(cocktailId)) return aCocktail;
        }
        return null;
    }

    public void createCocktailList(CocktailDBResponse cocktailDBResponse) {
        List<CocktailDBResponse.DrinkResource> drinks = cocktailDBResponse.getDrinks();
        for (CocktailDBResponse.DrinkResource aDrink : drinks) {
            List<String> ingredients = new ArrayList<>();
            if (aDrink.getStrIngredient1() != null && aDrink.getStrIngredient1().trim().length()>0) ingredients.add(aDrink.getStrIngredient1());
            if (aDrink.getStrIngredient2() != null && aDrink.getStrIngredient2().trim().length()>0) ingredients.add(aDrink.getStrIngredient2());
            if (aDrink.getStrIngredient3() != null && aDrink.getStrIngredient3().trim().length()>0) ingredients.add(aDrink.getStrIngredient3());
            if (aDrink.getStrIngredient4() != null && aDrink.getStrIngredient4().trim().length()>0) ingredients.add(aDrink.getStrIngredient4());
            if (aDrink.getStrIngredient5() != null && aDrink.getStrIngredient5().trim().length()>0) ingredients.add(aDrink.getStrIngredient5());
            if (aDrink.getStrIngredient6() != null && aDrink.getStrIngredient6().trim().length()>0) ingredients.add(aDrink.getStrIngredient6());
            if (aDrink.getStrIngredient7() != null && aDrink.getStrIngredient7().trim().length()>0) ingredients.add(aDrink.getStrIngredient7());
            if (aDrink.getStrIngredient8() != null && aDrink.getStrIngredient8().trim().length()>0) ingredients.add(aDrink.getStrIngredient8());
            Cocktail cocktail = new Cocktail(aDrink.getIdDrink(), aDrink.getStrDrink(), aDrink.getStrGlass(), aDrink.getStrInstructions(), aDrink.getStrDrinkThumb(), ingredients);
            allCocktails.add(cocktail);
        }
    }
    public List<Cocktail> searchCocktails(String cocktailString) {
        List<Cocktail> allCocktailsMatchSearch = new ArrayList<>();
        for (Cocktail aCocktail : allCocktails) {
            if (aCocktail.getName().toLowerCase().indexOf(cocktailString.toLowerCase())>-1) allCocktailsMatchSearch.add(aCocktail);
        }
        return allCocktailsMatchSearch;
    }
}
