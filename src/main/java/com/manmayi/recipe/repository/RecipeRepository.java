package com.manmayi.recipe.repository;

import com.manmayi.recipe.model.Ingredient;
import com.manmayi.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    @Query(value = "select * from recipe where recipe_title like %:keyword% OR description like %:keyword%", nativeQuery = true)
    List<Recipe> searchRecipeByKeyword(String keyword);

    @Query(value = "SELECT * FROM recipe join recipe_ingredients_mapping ON recipe.recipe_id = recipe_ingredients_mapping.recipe_id WHERE ingredient_id in ?1", nativeQuery = true)
    List<Recipe> searchRecipeWithIncludedIngredients(List<Ingredient> ingredients);

    @Query(value = "SELECT * FROM recipe where recipe_id not in (SELECT recipe.recipe_id FROM recipe join recipe_ingredients_mapping ON recipe.recipe_id = recipe_ingredients_mapping.recipe_id WHERE ingredient_id in ?1)", nativeQuery = true)
    List<Recipe> searchRecipeWithExcludedIngredients(List<Ingredient> ingredients);

    List<Recipe> findByCategoryIn(List<String> categories);

    List<Recipe> findByCourseIn(List<String> courses);

    List<Recipe> findByCuisineIn(List<String> cuisines);

    List<Recipe> findByDietIn(List<String> diets);
    @Query(value = "SELECT * FROM recipe join recipe_tags_mapping ON recipe.recipe_id = recipe_tags_mapping.recipe_id WHERE tag_id in ?1", nativeQuery = true)
    List<Recipe> searchRecipeByTag(List<String> tags);

}
