package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Column(name = "amount")
    @NotNull(message = "Amount is required")
    @NotEmpty(message = "Amount can not be empty")
    private String amount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="recipe_id", nullable=false)
    private Recipe recipe;

    public void setName(String name){ this.name = name; }
    public String getName(){ return this.name; }

    public void setAmount(String amount){ this.amount = amount; }
    public String getAmount(){ return this.amount; }

    public void setRecipe(Recipe recipe){ this.recipe = recipe; }
    public Recipe getRecipe(){ return this.recipe; }
}
