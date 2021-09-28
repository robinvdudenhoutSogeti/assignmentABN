package com.example.demo.Controllers;

import com.example.demo.Service.RecipeService;
import com.example.demo.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RecipeController {

    @Autowired
    private RecipeService _service;

    @GetMapping("/recipes")
    public Iterable<Recipe> getAll() {
        return _service.getAll();
    }

    @GetMapping("/recipes/{id}")
    @ResponseBody
    Recipe getRecipe(@PathVariable Long id) {
        return _service.getRecipe(id);
    }

    @PostMapping("/recipes")
    Recipe newRecipe(@Valid @RequestBody Recipe newRecipe) {
        return _service.saveRecipe(newRecipe);
    }


    @PutMapping("/recipes/{id}")
    ResponseEntity updateRecipe(@RequestBody Recipe updatedRecipe, @PathVariable Long id) {
        Recipe oldRecipe = _service.getRecipe(id);
        if (oldRecipe != null) {
            return new ResponseEntity<>(_service.update_Recipe(updatedRecipe, id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/recipes/{id}")
    ResponseEntity deleteRecipe(@PathVariable Long id) {
        Recipe oldRecipe = _service.getRecipe(id);
        if (oldRecipe != null) {
            _service.deleteRecipe(id);
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
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}