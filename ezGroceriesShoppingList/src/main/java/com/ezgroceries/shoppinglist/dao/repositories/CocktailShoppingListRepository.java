package com.ezgroceries.shoppinglist.dao.repositories;

import com.ezgroceries.shoppinglist.dao.entities.CocktailShoppingListEntity;
import com.ezgroceries.shoppinglist.dao.entities.CocktailShoppingListKey;
import com.ezgroceries.shoppinglist.dao.entities.ShoppingListEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CocktailShoppingListRepository extends Repository<CocktailShoppingListEntity, CocktailShoppingListKey>{
    public CocktailShoppingListEntity save(CocktailShoppingListEntity cocktailShoppingListEntity);
    public CocktailShoppingListEntity findById(CocktailShoppingListKey cocktailShoppingListKey);

    public List<CocktailShoppingListEntity> findByShoppingList(ShoppingListEntity shoppingList);
}
