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
    private LocalDateTime creation_date;

    @Column(name = "is_vegetarian", columnDefinition = "BOOLEAN")
    private boolean is_vegetarian;

    @Column(name = "consumer_amount")
    private int consumer_amount;

    @Column(name = "cooking_instructions")
    private String cooking_instructions;

    @OneToMany(mappedBy="recipe")
    private List<Ingredient> ingredientsList;

    public long getId(){ return this.id; }
    public void setId(long id){ this.id = id; }

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
