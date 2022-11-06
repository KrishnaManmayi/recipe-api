package com.manmayi.recipe.controller;

import com.manmayi.recipe.model.Ingredient;
import com.manmayi.recipe.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@CrossOrigin(origins = "https://soft-naiad-e75617.netlify.app")
@Slf4j
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/getSimilarIngredients/{ingredient}")
    public List<Ingredient> getSimilarIngredients(@PathVariable String ingredient){
        List<Ingredient> ingredients = ingredientService.getSimilarIngredients(ingredient);
        log.info("Total number of ingredients returned similar to the ingredient "+ingredient+" are: "+ingredients.size());
        return ingredients;
    }
}
