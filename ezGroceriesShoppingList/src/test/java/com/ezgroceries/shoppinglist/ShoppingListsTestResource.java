package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.dao.ShoppingListResource;
import com.ezgroceries.shoppinglist.manager.CocktailManager;
import com.ezgroceries.shoppinglist.manager.ShoppingListManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ShoppingListsTestResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String BASE_URL = "http://localhost:8080";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListManager shoppingListManager;

    @MockBean
    private CocktailManager cocktailManager;

    @Test
    public void listCocktails() throws Exception {
        int expectedNumberOfCocktails = 0;

        this.mockMvc //
                .perform(get( "/cocktails??search=russian")
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

        ShoppingListResource aTestShopping = new ShoppingListResource("Test shopping");
        aTestShopping.addIngredients(Arrays.asList("cava","kirr"));
        aTestShopping.setShoppingListId("shoppinglistID");

        given(shoppingListManager.getShoppingList("shoppinglistID"))
                .willReturn(aTestShopping);

        // act and assert
        mockMvc.perform(get("/shopping-lists/shoppinglistID"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
