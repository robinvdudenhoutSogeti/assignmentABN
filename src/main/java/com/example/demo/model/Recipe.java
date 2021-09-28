package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creation_date;

    @Column(name = "is_vegetarian", columnDefinition = "BOOLEAN")
    @NotNull(message = "Is vegetarian is required")
    private boolean is_vegetarian;

    @Column(name = "consumer_amount")
    @NotNull(message = "Consumer amount is required")
    private int consumer_amount;

    @Column(name = "cooking_instructions")
    @NotBlank(message = "Cooking instructions are mandatory")
    private String cooking_instructions;

    public void setCreation_date(LocalDateTime date){ this.creation_date = date; }
    public LocalDateTime getCreation_date(){ return this.creation_date; }

    public void setIsVegetarian(boolean isVega){ this.is_vegetarian = isVega; }
    public boolean getIsVegetarian(){ return this.is_vegetarian; }

    public void setConsumer_amount(int consumer_amount){ this.consumer_amount = consumer_amount; }
    public int getConsumer_amount(){ return this.consumer_amount; }

    public void setCooking_instructions(String instructions){ this.cooking_instructions = instructions; }
    public String getCooking_instructions(){ return this.cooking_instructions; }
}
