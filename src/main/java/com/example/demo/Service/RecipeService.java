package com.example.demo.Service;

import com.example.demo.exceptions.RecipeNotFoundException;
import com.example.demo.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository _repository;

    public List<Recipe> getAll() {
        return _repository.findAll();
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipe.setCreation_date(LocalDateTime.now());
        _repository.save(recipe);
        return recipe;
    }

    public Recipe update_Recipe(Recipe updatedRecipe, long id) {
        Recipe oldRecipe = getRecipe(id);
        oldRecipe.setConsumer_amount(updatedRecipe.getConsumer_amount());
        oldRecipe.setCooking_instructions(updatedRecipe.getCooking_instructions());
        oldRecipe.setCreation_date(LocalDateTime.now());
        oldRecipe.setIsVegetarian(updatedRecipe.getIsVegetarian());
        _repository.save(oldRecipe);
        return oldRecipe;
    }

    public Recipe getRecipe(Long id) {
      return _repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    public void deleteRecipe(Long id) {
        _repository.deleteById(id);
    }
}
