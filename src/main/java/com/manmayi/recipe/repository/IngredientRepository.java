package com.manmayi.recipe.repository;

import com.manmayi.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    List<Ingredient> findByIngredientContaining(String ingredient);
}
