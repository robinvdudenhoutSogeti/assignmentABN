package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "is_vegetarian", columnDefinition = "BOOLEAN")
    private boolean isVegetarian;

    @Column(name = "consumer_amount")
    private int consumerAmount;

    @Column(name = "cooking_instructions")
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
