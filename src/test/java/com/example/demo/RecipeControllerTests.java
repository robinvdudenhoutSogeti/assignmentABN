package com.example.demo;

import com.example.demo.exceptions.RecipeNotFoundException;
import com.example.demo.service.RecipeService;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTests {

    @MockBean
    private RecipeService mockService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void init() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        for(int i =1; i< 6; i++){
            Recipe recipe = new Recipe();
            recipe.setIsVegetarian(false);
            recipe.setCookingInstructions("cooking");
            recipe.setConsumerAmount(i);
            recipe.setCreationDate(LocalDateTime.now());
            recipes.add(recipe);
            recipe.setId(i);
        }
        Mockito.lenient().when(mockService.getAll()).thenReturn(recipes);
        Mockito.lenient().when(mockService.getRecipe(1L)).thenReturn(recipes.get(1));
    }

    @Test
     void AddNewUserShouldGiveValidationError() throws Exception {
        RecipeDTO recipe = new RecipeDTO();

        this.mvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/recipes")
                        .contentType("application/json")
                        .content(String.valueOf(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
     void GetAllRecipesShouldGive200() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/recipes")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void GetRecipe1ShouldGive200() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/recipes/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void DeleteRecipeShouldGive204() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/recipes/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void CreateRecipeShouldGive201() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/recipes")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"isVegetarian\": 1,\n" +
                                "  \"consumerAmount\": 5,\n" +
                                "  \"cookingInstructions\": \"cooking intructions\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

}
