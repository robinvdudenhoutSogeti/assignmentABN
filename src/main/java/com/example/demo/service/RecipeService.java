package com.example.demo.service;

import com.example.demo.exceptions.DatabaseException;
import com.example.demo.exceptions.RecipeNotFoundException;
import com.example.demo.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    public List<Recipe> getAll() {
        return repository.findAll();
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipe.setCreationDate(LocalDateTime.now());
        this.saveToDatabase(recipe);
        return recipe;
    }

    public Recipe updateRecipe(Recipe updatedRecipe, long id) {
        Recipe oldRecipe = getRecipe(id);
        oldRecipe.setConsumerAmount(updatedRecipe.getConsumerAmount());
        oldRecipe.setCookingInstructions(updatedRecipe.getCookingInstructions());
        oldRecipe.setCreationDate(LocalDateTime.now());
        oldRecipe.setIsVegetarian(updatedRecipe.getIsVegetarian());
        this.saveToDatabase(oldRecipe);
        return oldRecipe;
    }

    private void saveToDatabase(Recipe recipe){
        try {
            repository.save(recipe);
        } catch(DuplicateKeyException e) {
            throw new DatabaseException("Recipe already exist");
        } catch(DataAccessException e) {
            throw new DatabaseException("Database unreachable");
        }
    }

    public Recipe getRecipe(Long id) {
      return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    public void deleteRecipe(Long id) {
        if(getRecipe(id) == null) throw new RecipeNotFoundException(id);
        repository.deleteById(id);
    }
}
