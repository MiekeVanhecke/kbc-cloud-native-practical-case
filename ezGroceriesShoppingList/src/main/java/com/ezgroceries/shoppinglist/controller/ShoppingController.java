package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.controller.contracts.ShoppingRequest;
import com.ezgroceries.shoppinglist.dao.Cocktail;

import com.ezgroceries.shoppinglist.dao.CocktailManager;
import com.ezgroceries.shoppinglist.dao.ShoppingList;
import com.ezgroceries.shoppinglist.dao.ShoppingListManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ShoppingController {

    private CocktailManager cocktailManager;
    private  ShoppingListManager shoppingListManager;

    private static final Logger log = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    public ShoppingController() {
        cocktailManager = new CocktailManager();
        shoppingListManager = new ShoppingListManager();
    }

    @GetMapping(path="/cocktails", produces = "application/json")
    public List<Cocktail> cocktails(@RequestParam(value = "search") String search) {
         return cocktailManager.getAllCocktails();
    }

    @PostMapping(path="/shopping-lists")
    public ResponseEntity<Void> addShoppingList(@RequestHeader HttpHeaders httpHeaders, @RequestBody String name) {
        String shoppingListId = shoppingListManager.createShoppingList(name);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListId}")
                .buildAndExpand(shoppingListId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
