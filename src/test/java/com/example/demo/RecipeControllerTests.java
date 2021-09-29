package com.example.demo;

import com.example.demo.Controllers.RecipeController;
import com.example.demo.Service.RecipeService;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTests {

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
            recipe.setCooking_instructions("cooking");
            recipe.setConsumer_amount(i);
            recipe.setCreation_date(LocalDateTime.now());
            recipes.add(recipe);
            recipe.setId(i);
        }
        Mockito.lenient().when(mockService.getAll()).thenReturn(recipes);
        Mockito.lenient().when(mockService.getRecipe(1L)).thenReturn(recipes.get(1));
    }

    @Test
    public void AddNewUserShouldGiveValidationError() throws Exception {
        RecipeDTO recipe = new RecipeDTO();

        this.mvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/recipes")
                        .contentType("application/json")
                        .content(String.valueOf(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void GetAllRecipesShouldGive200() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/recipes")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void GetRecipe1ShouldGive200() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/recipes/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void DeleteRecipeShouldGive204() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/recipes/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void DeleteNonExistingRecipeShouldGive404() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/recipes/11")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void CreateNonValidRecipeShouldGive400() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/recipes")
                        .contentType("application/json")
                        .content({"consumer_amount": "5", }))
                .andExpect(status().isBadRequest());
    }

}
