package com.example.demo.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException(Long id) {
        super("Could not find recipe " + id);
    }
}
