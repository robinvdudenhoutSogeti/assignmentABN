package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingredient {

    private @Id @GeneratedValue Long id;
    private String name;
    private String amount;
    private int recipe_id;

    public long getId(){ return this.id; }

    public void setName(String name){ this.name = name; }
    public String getName(){ return this.name; }

    public void setAmount(String amount){ this.amount = amount; }
    public String getAmount(){ return this.amount; }

    public void setRecipe_id(int recipe_id){ this.recipe_id = recipe_id; }
    public int getRecipe_id(){ return this.recipe_id; }
}
