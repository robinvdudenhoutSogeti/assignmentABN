package com.example.demo.controllers;

import com.example.demo.service.RecipeService;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RecipeController {

    @Autowired
    private RecipeService service;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "View a list of all available recipes",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list"),})
    @GetMapping("/recipes")
    public List<RecipeDTO> getAll() {
        List<Recipe> recipes = service.getAll();
        return recipes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "View a a recipe",response = Recipe.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the recipe"),
            @ApiResponse(code = 404, message = "The recipe you were trying to reach is not found")
    }
    )
    @GetMapping("/recipes/{id}")
    @ResponseBody
    public Recipe getRecipe(@PathVariable Long id) {
        return service.getRecipe(id);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO newRecipe(@Valid @RequestBody RecipeDTO newRecipe) {
        Recipe recipe = convertToEntity(newRecipe);
        service.saveRecipe(recipe);
        return newRecipe;
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Object> updateRecipe(@RequestBody RecipeDTO updatedRecipe, @PathVariable Long id) {
        Recipe oldRecipe = service.getRecipe(id);
        if (oldRecipe != null) {
            return new ResponseEntity<>(service.updateRecipe(convertToEntity(updatedRecipe), id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Long id) {
        Recipe oldRecipe = service.getRecipe(id);
        if (oldRecipe != null) {
            service.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private RecipeDTO convertToDto(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    private Recipe convertToEntity(RecipeDTO recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }

}