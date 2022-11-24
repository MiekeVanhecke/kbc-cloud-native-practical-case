package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import com.ezgroceries.shoppinglist.dao.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.dao.entities.CocktailShoppingListEntity;
import com.ezgroceries.shoppinglist.dao.entities.CocktailShoppingListKey;
import com.ezgroceries.shoppinglist.dao.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailShoppingListRepository;
import com.ezgroceries.shoppinglist.dao.repositories.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;

    private final CocktailRepository cocktailRepository;

    private final CocktailShoppingListRepository cocktailShoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository, CocktailShoppingListRepository cocktailShoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
        this.cocktailShoppingListRepository = cocktailShoppingListRepository;
    }

    public String create(String shoppingListName) {
        ShoppingListResource shoppingListResource = new ShoppingListResource(shoppingListName);
        shoppingListRepository.save(new ShoppingListEntity(UUID.fromString(shoppingListResource.getShoppingListId()), shoppingListResource.getName()));
        return shoppingListResource.getShoppingListId();
    }

    public boolean addCocktailsToShoppingList(String shoppingListId, String cocktailId){
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        CocktailEntity cocktailEntity = cocktailRepository.findById(UUID.fromString(cocktailId));
        if (shoppingListEntity != null && cocktailEntity != null) {
            CocktailShoppingListKey cocktailShoppingListKey = new CocktailShoppingListKey(cocktailEntity.getId(), shoppingListEntity.getId());

            CocktailShoppingListEntity cocktailShoppingListEntity = cocktailShoppingListRepository.findById(cocktailShoppingListKey);
            if (cocktailShoppingListEntity == null) {
                cocktailShoppingListEntity = new CocktailShoppingListEntity(cocktailShoppingListKey, cocktailEntity, shoppingListEntity);
                cocktailShoppingListRepository.save(cocktailShoppingListEntity);
            }

            return true;
        }
        return false;
    }

    public ShoppingListResource getShoppingList(String shoppingListId){
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        if (shoppingListEntity != null) {
            return getShoppingResource(shoppingListEntity);
        }
        return null;
    }

    private ShoppingListResource getShoppingResource(ShoppingListEntity shoppingListEntity){
        ShoppingListResource shoppingListResource = new ShoppingListResource(String.valueOf(shoppingListEntity.getId()), shoppingListEntity.getName());
        Set<CocktailShoppingListEntity> cocktailShoppingListEntityList = shoppingListEntity.getCocktailShoppingListEntities();
        for (CocktailShoppingListEntity cocktailShoppingListEntity : cocktailShoppingListEntityList) {
            shoppingListResource.addIngredients(cocktailShoppingListEntity.getCocktail().getIngredients());
        }
        return shoppingListResource;
    }

    public List<ShoppingListResource> getAllShoppingLists(){
        List<ShoppingListResource> allShoppingLists = new ArrayList<>();
        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findBy();
        for (ShoppingListEntity shoppingListEntity : shoppingListEntities) {
            allShoppingLists.add(getShoppingResource(shoppingListEntity));
        }
        return allShoppingLists;
    }
}
