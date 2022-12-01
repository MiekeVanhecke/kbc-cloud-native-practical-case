package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.dao.repositories.ShoppingListRepository;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ShoppingListsControllerTests {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String BASE_URL = "http://localhost:8080";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

    @Test
    public void listCocktails() throws Exception {
        int expectedNumberOfCocktails = 7;

        this.mockMvc //
                .perform(get( "/cocktails?search=russian")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(expectedNumberOfCocktails));
    }

    @Test
    public void createShoppingList() throws Exception {
        // arrange
        this.mockMvc
                .perform(post("/shopping-lists", 1)
                        .content("{\n  \"name\": \"Test shoppinglist\"\n}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void addShoppingList() throws Exception {

        ShoppingListResource aTestShopping = getShoppingListResource("testShoppingListName");

        given(shoppingListService.getShoppingList("shoppinglistID"))
                .willReturn(aTestShopping);

        // act and assert
        this.mockMvc.perform(get("/shopping-lists/shoppinglistID"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void addCocktailToShoppingListFails() throws Exception {

        ShoppingListResource aTestShopping = getShoppingListResource("testShoppingListName");

        // act and assert
        this.mockMvc
                .perform(post("/shopping-lists/" + aTestShopping.getShoppingListId() + "/cocktails" , 1)
                        .content("{\n  \"cocktailId\": \"testCocktailId\"\n}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void addCocktailToShoppingList() throws Exception {

        ShoppingListResource aTestShopping = getShoppingListResource("testShoppingListName");

        given(shoppingListService.addCocktailsToShoppingList("shoppinglistID", "testCocktailId"))
                .willReturn(true);
        // act and assert
        this.mockMvc
                .perform(post("/shopping-lists/" + aTestShopping.getShoppingListId() + "/cocktails" , 1)
                        .content("{\n  \"cocktailId\": \"testCocktailId\"\n}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    public void getAllShoppingLists() throws Exception {

        ShoppingListResource testShopping = getShoppingListResource("testShoppingListName");
        List<ShoppingListResource> testShoppingList = new ArrayList<>();
        testShoppingList.add(testShopping);

        given(shoppingListService.getAllShoppingLists()).willReturn(testShoppingList);
        // act and assert
        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
    private ShoppingListResource getShoppingListResource (String name) {
        ShoppingListResource aTestShopping = new ShoppingListResource("Test shopping");
        aTestShopping.addIngredients(Arrays.asList("cava","kirr"));
        aTestShopping.setShoppingListId("shoppinglistID");
        return aTestShopping;
    }
}
