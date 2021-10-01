package com.example.demo.controllers;

import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeDTO;
import com.example.demo.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.controllers.DTOConverter.convertToDto;
import static com.example.demo.controllers.DTOConverter.convertToEntity;

@RestController
public class RecipeController {

    @Autowired
    private RecipeService service;

    /**
     * Returns a list of all the recipes known to the database
     * @return a list of all RecipeDTO
     */
    @ApiOperation(value = "View a list of all available recipes",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list"),})
    @GetMapping("/recipes")
    public List<RecipeDTO> getAll() {
        List<Recipe> recipes = service.getAll();
        return recipes.stream()
                .map(DTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns the recipe specified by id param
     * @param id the id of the Recipe to identify it within the database
     * @return RecipeDTO with the same id as the param
     */
    @ApiOperation(value = "View a a recipe",response = RecipeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the recipe"),
            @ApiResponse(code = 404, message = "The recipe you were trying to get is not found")
    }
    )
    @GetMapping("/recipes/{id}")
    @ResponseBody
    public RecipeDTO getRecipe(@PathVariable Long id) {
        return convertToDto(service.getRecipe(id));
    }

    /**
     * Creates a new Recipe in the database based of the DTO given in the params
     * @param newRecipe is the new Recipe which is going to be added to the database
     * @return the newly added recipe as a DTO
     */
    @ApiOperation(value = "Create a a recipe",response = RecipeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the recipe"),
            @ApiResponse(code = 404, message = "The recipe you were trying to get is not found")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/recipes")
    public @ResponseBody RecipeDTO newRecipe(@Valid @RequestBody RecipeDTO newRecipe) {
        Recipe recipe = convertToEntity(newRecipe);
        service.saveRecipe(recipe);
        return newRecipe;
    }

    /**
     * Updates the recipe based on the DTO given in the params
     * @param updatedRecipe is the Recipe which needs to be updated
     * @param id is the id of the Recipe which needs to be updated
     * @return a Response depending on if the Recipe exist or not
     */
    @ApiOperation(value = "Update a a recipe",response = RecipeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the recipe"),
            @ApiResponse(code = 404, message = "The recipe you were trying to get is not found")
    })
    @PutMapping("/recipes/{id}")
    public ResponseEntity<Object> updateRecipe(@RequestBody RecipeDTO updatedRecipe, @PathVariable Long id) {
        Recipe oldRecipe = service.getRecipe(id);
        if (oldRecipe != null) {
            return new ResponseEntity<>(service.updateRecipe(convertToEntity(updatedRecipe), id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Deletes the recipe from the database
     * @param id is the id of the recipe that will be deleted
     * @return a response based on if the recipe is deleted or not
     */
    @ApiOperation(value = "Delete a a recipe")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the recipe"),
            @ApiResponse(code = 404, message = "The recipe you were trying to get is not found")
    })
    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Long id) {
        service.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}