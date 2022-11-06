package com.manmayi.recipe.service;

import com.manmayi.recipe.model.Ingredient;
import com.manmayi.recipe.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getSimilarIngredients(String ingredient) {
        return ingredientRepository.findByIngredientContaining(ingredient);
    }
}
