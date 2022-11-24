package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.dao.CocktailResource;
import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import com.ezgroceries.shoppinglist.dao.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.dao.entities.CocktailShoppingListEntity;
import com.ezgroceries.shoppinglist.dao.entities.CocktailShoppingListKey;
import com.ezgroceries.shoppinglist.dao.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailShoppingListRepository;
import com.ezgroceries.shoppinglist.dao.repositories.ShoppingListRepository;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ShoppingListServiceTests {
    @MockBean
    private CocktailRepository cocktailRepository;
    @MockBean
    private ShoppingListRepository shoppingListRepository;
    @MockBean
    private CocktailShoppingListRepository cocktailShoppingListRepository;
    @SpyBean
    ShoppingListService shoppingListService;
    @Test
    public void testHappyFlow_AddCocktailsToShoppingList() {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(UUID.randomUUID(), "Test shopping");
        CocktailEntity cocktailEntity = new CocktailEntity("testIdDrink", "TestCocktailName");
        CocktailShoppingListKey cocktailShoppingListKey = new CocktailShoppingListKey(cocktailEntity.getId(), shoppingListEntity.getId());
        CocktailShoppingListEntity cocktailShoppingListEntity = new CocktailShoppingListEntity(cocktailShoppingListKey, cocktailEntity, shoppingListEntity);

        doReturn(shoppingListEntity).when(shoppingListRepository).findById(shoppingListEntity.getId());
        doReturn(cocktailEntity).when(cocktailRepository).findById(cocktailEntity.getId());
        doReturn(cocktailShoppingListEntity).when(cocktailShoppingListRepository).findById(cocktailShoppingListKey);

        boolean success = shoppingListService.addCocktailsToShoppingList(String.valueOf(shoppingListEntity.getId()), String.valueOf(cocktailEntity.getId()) );
        assertEquals(true,success);

    }
    @Test
    public void testUnHappyFlow_AddCocktailsToShoppingList() {
        boolean success = shoppingListService.addCocktailsToShoppingList(String.valueOf(UUID.randomUUID()), String.valueOf(UUID.randomUUID()));
        assertEquals(false,success);
    }
}
