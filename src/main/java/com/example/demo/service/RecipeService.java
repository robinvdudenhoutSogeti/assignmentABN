package com.example.demo.service;

import com.example.demo.exceptions.DatabaseException;
import com.example.demo.exceptions.RecipeNotFoundException;
import com.example.demo.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    /**
     * Gets all recipes from the database
     * @return a list of all the recipes in the database
     */
    public List<Recipe> getAll() {
        return repository.findAll();
    }

    /**
     * @param recipe the new Recipe
     * @return the saved recipe
     */
    public Recipe saveRecipe(Recipe recipe) {
        recipe.setCreationDate(LocalDateTime.now());
        this.saveToDatabase(recipe);
        return recipe;
    }

    /**
     * @param updatedRecipe the updated recipe
     * @param id the id of the updated recipe
     * @return the updated recipe
     */
    public Recipe updateRecipe(Recipe updatedRecipe, long id) {
        Recipe oldRecipe = getRecipe(id);
        oldRecipe.setConsumerAmount(updatedRecipe.getConsumerAmount());
        oldRecipe.setCookingInstructions(updatedRecipe.getCookingInstructions());
        oldRecipe.setCreationDate(LocalDateTime.now());
        oldRecipe.setIsVegetarian(updatedRecipe.getIsVegetarian());
        this.saveToDatabase(oldRecipe);
        return oldRecipe;
    }

    /**
     * Saves a recipe in the database
     * @param recipe is the Recipe which will be saved in the database
     */
    private void saveToDatabase(Recipe recipe){
        try {
            repository.save(recipe);
        } catch(DuplicateKeyException e) {
            throw new DatabaseException("Recipe already exist", HttpStatus.CONFLICT);
        } catch(DataAccessException e) {
            throw new DatabaseException("Something went wrong fetching the data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the wanted recipe from the database
     * @param id the of the wanted recipe
     * @return a recipe
     */
    public Recipe getRecipe(Long id) {
      return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    /**
     * Deletes the recipe from the database
     * @param id the id of the recipe that needs to be deleted
     */
    public void deleteRecipe(Long id) {
        if(getRecipe(id) == null) throw new RecipeNotFoundException(id);
        repository.deleteById(id);
    }
}
