package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.controller.contracts.CocktailRequest;
import com.ezgroceries.shoppinglist.controller.contracts.ShoppingRequest;
import com.ezgroceries.shoppinglist.dao.CocktailResource;

import com.ezgroceries.shoppinglist.dao.CocktailDBResponse;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailDBClient;
import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
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

    //Custom tomcat configuration, we add a connector that allows http traffic next to https
    @Bean
    public ServletWebServerFactory servletContainer(@Value("${server.http.port}") int httpPort){
        Connector connector=new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(httpPort);

        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }
    @GetMapping(path="/cocktails", produces = "application/json")
    public List<CocktailResource> cocktails(@RequestParam(value = "search", required = false) String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);

        return cocktailService.mergeCocktails(cocktailDBResponse.getDrinks());
    }

    @PostMapping(path="/shopping-lists")
    public ResponseEntity<Void> addShoppingList(@RequestBody ShoppingRequest shoppingList) {
        String shoppingListId = shoppingListService.create(shoppingList.getName());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shoppingListId}")
                .buildAndExpand(shoppingListId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @PostMapping(path="/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@PathVariable("shoppingListId") String shoppingListId, HttpServletRequest request, @RequestBody CocktailRequest cocktailId) {
        boolean success = shoppingListService.addCocktailsToShoppingList(shoppingListId, cocktailId.getCocktailId());

        if (success) {
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
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/shopping-lists/{shoppingListId}", produces = "application/json")
    public ShoppingListResource getShoppingList(@PathVariable("shoppingListId") String shoppingListId) {
        return shoppingListService.getShoppingList(shoppingListId);
    }

    @GetMapping(path="/shopping-lists", produces = "application/json")
    public List<ShoppingListResource> getAllShoppingLists() {
        return shoppingListService.getAllShoppingLists();
    }

}
