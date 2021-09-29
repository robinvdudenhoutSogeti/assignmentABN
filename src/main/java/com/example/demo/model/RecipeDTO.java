package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeDTO {
    @Id
    private Long id;

    private LocalDateTime creationDate;

    @NotNull(message = "Is vegetarian is required")
    private boolean isVegetarian;

    @NotNull(message = "Consumer amount is required")
    private int consumerAmount;

    @NotBlank(message = "Cooking instructions are mandatory")
    private String cookingInstructions;

    @OneToMany(mappedBy="recipe")
    private List<Ingredient> ingredientsList;

    public void setId(long id){ this.id = id; }

    public void setCreationDate(LocalDateTime date){ this.creationDate = date; }
    public LocalDateTime getCreationDate(){ return this.creationDate; }

    public void setIsVegetarian(boolean isVega){ this.isVegetarian = isVega; }
    public boolean getIsVegetarian(){ return this.isVegetarian; }

    public void setConsumerAmount(int consumerAmount){ this.consumerAmount = consumerAmount; }
    public int getConsumerAmount(){ return this.consumerAmount; }

    public void setCookingInstructions(String instructions){ this.cookingInstructions = instructions; }
    public String getCookingInstructions(){ return this.cookingInstructions; }

    public void setIngredientsList(List<Ingredient> ingredients){ this.ingredientsList = ingredients; }
    public List<Ingredient> getIngredientsList(){ return this.ingredientsList; }

}
