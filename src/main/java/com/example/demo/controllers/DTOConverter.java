package com.example.demo.controllers;

import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    private static ModelMapper modelMapper;

    @Autowired
    DTOConverter(ModelMapper mapper){ DTOConverter.modelMapper = mapper;}

    public static RecipeDTO convertToDto(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    public static Recipe convertToEntity(RecipeDTO recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }

}
