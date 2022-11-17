package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.controller.contracts.CocktailRequest;
import com.ezgroceries.shoppinglist.controller.contracts.ShoppingRequest;
import com.ezgroceries.shoppinglist.dao.CocktailResource;

import com.ezgroceries.shoppinglist.dao.CocktailDBResponse;
import com.ezgroceries.shoppinglist.manager.CocktailDBClient;
import com.ezgroceries.shoppinglist.manager.CocktailManager;
import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import com.ezgroceries.shoppinglist.manager.ShoppingListManager;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
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

    private CocktailService cocktailService;
    private ShoppingListService shoppingListService;

    @Autowired
    private CocktailDBClient cocktailDBClient;

    private static final Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    public ShoppingController(CocktailService cocktailService, ShoppingListService shoppingListService) {
        this.cocktailService = cocktailService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping(path="/cocktails", produces = "application/json")
    public List<CocktailResource> cocktails(@RequestParam(value = "search", required = false) String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);

        return cocktailService.mergeCocktails(cocktailDBResponse.getDrinks());
    }

/*    @PostMapping(path="/shopping-lists")
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
        ShoppingListResource aShoppingListResource = shoppingListManager.getShoppingList(shoppingListId);
        CocktailResource aCocktailResource = cocktailManager.getCocktail(cocktailId.getCocktailId());
        if (aShoppingListResource != null && aCocktailResource != null) {
            aShoppingListResource.addIngredients(aCocktailResource.getIngredients());
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
    public ShoppingListResource getShoppingList(@PathVariable("shoppingListId") String shoppingListId) {
        return shoppingListManager.getShoppingList(shoppingListId);
    }

    @GetMapping(path="/shopping-lists", produces = "application/json")
    public List<ShoppingListResource> getAllShoppingLists() {
        return shoppingListManager.getAllShoppingLists();
    }*/

}
