package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.controller.contracts.CocktailRequest;
import com.ezgroceries.shoppinglist.controller.contracts.ShoppingRequest;
import com.ezgroceries.shoppinglist.dao.Cocktail;

import com.ezgroceries.shoppinglist.dao.CocktailDBResponse;
import com.ezgroceries.shoppinglist.manager.CocktailDBClient;
import com.ezgroceries.shoppinglist.manager.CocktailManager;
import com.ezgroceries.shoppinglist.dao.ShoppingList;
import com.ezgroceries.shoppinglist.manager.ShoppingListManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class ShoppingController {

    private CocktailManager cocktailManager;
    private  ShoppingListManager shoppingListManager;

    @Autowired
    private CocktailDBClient cocktailDBClient;

    private static final Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    public ShoppingController(CocktailManager coctailManager, ShoppingListManager shoppingListManager) {
        this.cocktailManager = coctailManager;
        this.shoppingListManager = shoppingListManager;
    }

    @GetMapping(path="/cocktails", produces = "application/json")
    public List<Cocktail> cocktails(@RequestParam(value = "search", required = false) String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        cocktailManager.createCocktailList(cocktailDBResponse);

        return cocktailManager.getAllCocktails();
    }

    @PostMapping(path="/shopping-lists")
    public ResponseEntity<Void> addShoppingList(@RequestBody ShoppingRequest shoppingList) {
        String shoppingListId = shoppingListManager.createShoppingList(shoppingList.getName());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListId}")
                .buildAndExpand(shoppingListId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @PostMapping(path="/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@PathVariable("shoppingListId") String shoppingListId, HttpServletRequest request, @RequestBody CocktailRequest cocktailId) {
        ShoppingList aShoppingList = shoppingListManager.getShoppingList(shoppingListId);
        Cocktail aCocktail = cocktailManager.getCocktail(cocktailId.getCocktailId());
        if (aShoppingList != null && aCocktail != null) {
            aShoppingList.addIngredients(aCocktail.getIngredients());
        }

        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        URI location = ServletUriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/shopping-lists")
                .path("/{shoppingListId}")
                .buildAndExpand(shoppingListId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path="/shopping-lists/{shoppingListId}", produces = "application/json")
    public ShoppingList getShoppingList(@PathVariable("shoppingListId") String shoppingListId) {
        return shoppingListManager.getShoppingList(shoppingListId);
    }

    @GetMapping(path="/shopping-lists", produces = "application/json")
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListManager.getAllShoppingLists();
    }

}
