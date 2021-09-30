package com.example.demo.controllers;

import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOConverter {

    @Autowired
    private static ModelMapper modelMapper;

    private DTOConverter(){}

    public static RecipeDTO convertToDto(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    public static Recipe convertToEntity(RecipeDTO recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }

}
