package com.example.demo;

import com.example.demo.Service.RecipeRepository;
import com.example.demo.Service.RecipeService;
import com.example.demo.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RecipeServiceTests {

    @Mock
    RecipeRepository mockRepository;

    @InjectMocks
    RecipeService service;

    @BeforeEach
    public void init() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        for(int i =0; i< 5; i++){
            Recipe recipe = new Recipe();
            recipe.setIsVegetarian(false);
            recipe.setCooking_instructions("cooking");
            recipe.setConsumer_amount(i + 1);
            recipe.setCreation_date(LocalDateTime.now());
            recipes.add(recipe);
        }
        Mockito.lenient().when(mockRepository.findAll()).thenReturn(recipes);
        Mockito.lenient().when(mockRepository.findById(1L)).thenReturn(recipes.stream().findFirst());
    }

    @Test
    public void allRecipesShouldBe5(){
        //given

        //when
        List<Recipe> recipes = service.getAll();

        //then
        verify(mockRepository).findAll();
        Assertions.assertNotNull(recipes);
        Assertions.assertEquals(recipes.size(), 5);
    }

    @Test
    public void getRecipeId1ShouldReturnFirstRecipe(){
        //given

        //when
        Recipe recipe = service.getRecipe(1L);

        //then
        Assertions.assertNotNull(recipe);
        Assertions.assertEquals(recipe.getConsumer_amount(), 1);
    }

    @Test
    public void RemoveRecipeShouldRemoveRecipe(){
        //given
        Recipe recipe = mockRepository.findById(1L).get();
        //when
        mockRepository.delete(recipe);

        //then
        verify(mockRepository, times(1)).delete(recipe);
    }

    @Test
    public void AddNewRecipeShouldAddRecipe(){
        //given
        Recipe recipe = mockRepository.findById(1L).get();
        //when
        Recipe savedRecipe = service.saveRecipe(recipe);

        //then
        Assertions.assertNotNull(savedRecipe);
        Assertions.assertEquals(savedRecipe.getConsumer_amount(), recipe.getConsumer_amount());
    }

    @Test
    public void UpdateRecipeShouldBeUpdated(){
        //given
        Recipe recipe = service.getRecipe(1L);
        recipe.setCooking_instructions("This is the updated recipe");
        //when
        Recipe updatedRecipe = service.update_Recipe(recipe, 1L);

        //then
        Assertions.assertNotNull(updatedRecipe);
        Assertions.assertEquals(updatedRecipe.getCooking_instructions(), recipe.getCooking_instructions());
    }
}
