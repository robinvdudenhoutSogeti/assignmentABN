package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeDTO {
    @Id
    private Long id;

    private LocalDateTime creation_date;

    @NotNull(message = "Is vegetarian is required")
    private boolean is_vegetarian;

    @NotNull(message = "Consumer amount is required")
    private int consumer_amount;

    @NotBlank(message = "Cooking instructions are mandatory")
    private String cooking_instructions;

    @OneToMany(mappedBy="recipe")
    private List<Ingredient> ingredientsList;

    public void setCreation_date(LocalDateTime date){ this.creation_date = date; }
    public LocalDateTime getCreation_date(){ return this.creation_date; }

    public void setIsVegetarian(boolean isVega){ this.is_vegetarian = isVega; }
    public boolean getIsVegetarian(){ return this.is_vegetarian; }

    public void setConsumer_amount(int consumer_amount){ this.consumer_amount = consumer_amount; }
    public int getConsumer_amount(){ return this.consumer_amount; }

    public void setCooking_instructions(String instructions){ this.cooking_instructions = instructions; }
    public String getCooking_instructions(){ return this.cooking_instructions; }

    public void setIngredientsList(List<Ingredient> ingredients){ this.ingredientsList = ingredients; }
    public List<Ingredient> getIngredientsList(){ return this.ingredientsList; }
}
