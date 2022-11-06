package com.manmayi.recipe.controller;

import com.manmayi.recipe.model.Ingredient;
import com.manmayi.recipe.model.Recipe;
import com.manmayi.recipe.model.SearchCriteria;
import com.manmayi.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@CrossOrigin(origins = "https://soft-naiad-e75617.netlify.app/")
@Slf4j
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes =  recipeService.getAllRecipes();
        log.info("Total number of recipes fetched : "+recipes.size());
        return recipes;
    }

    @PostMapping("/search")
    public List<Recipe> search(@RequestBody SearchCriteria searchCriteria){
        long start = System.currentTimeMillis();
        log.info("Received request for the search criteria "+ searchCriteria);
        List<Recipe> recipes = recipeService.search(searchCriteria);
        log.info("Total number of recipes fetched are "+recipes.size());
        long end = System.currentTimeMillis();
        log.info("Request completed. Time taken: "+ (end-start)+"ms");
        return recipes;
    }

    @GetMapping("/searchByKeyword/{keyword}")
    public List<Recipe> searchRecipeByKeyword(@PathVariable String keyword){
        List<Recipe> recipes = recipeService.searchRecipeByKeyword(keyword);
        log.info("Total number of recipes fetched with the keyword "+keyword+" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchWithIncludedIngredients")
    public List<Recipe> searchRecipeWithIncludedIngredients(@RequestBody List<Ingredient> ingredients){
        List<Recipe> recipes = recipeService.searchRecipeWithIncludedIngredients(ingredients);
        log.info("Total number of recipes fetched with included ingredients "+ ingredients +" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchWithExcludedIngredients")
    public List<Recipe> searchRecipeWithExcludedIngredients(@RequestBody List<Ingredient> ingredients){
        List<Recipe> recipes = recipeService.searchRecipeWithExcludedIngredients(ingredients);
        log.info("Total number of recipes fetched with excluded ingredients "+ ingredients +" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchRecipeByCategory")
    public List<Recipe> searchRecipeByCategory(@RequestBody List<String> categories){
        List<Recipe> recipes = recipeService.searchRecipeByCategory(categories);
        log.info("Total number of recipes fetched with the categories "+categories+" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchRecipeByCourse")
    public List<Recipe> searchRecipeByCourse(@RequestBody List<String> courses){
        List<Recipe> recipes = recipeService.searchRecipeByCourse(courses);
        log.info("Total number of recipes fetched with the courses "+courses+" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchRecipeByCuisine")
    public List<Recipe> searchRecipeByCuisine(@RequestBody List<String> cuisines){
        List<Recipe> recipes = recipeService.searchRecipeByCuisine(cuisines);
        log.info("Total number of recipes fetched with the cuisines "+cuisines+" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchRecipeByDiet")
    public List<Recipe> searchRecipeByDiet(@RequestBody List<String> diets){
        List<Recipe> recipes = recipeService.searchRecipeByDiet(diets);
        log.info("Total number of recipes fetched with the diets "+diets+" are: "+recipes.size());
        return recipes;
    }

    @PostMapping("/searchRecipeByTag")
    public List<Recipe> searchRecipeByTag(@RequestBody List<String> tags){
        List<Recipe> recipes = recipeService.searchRecipeByTag(tags);
        log.info("Total number of recipes fetched with the tags "+ tags + " are: "+recipes.size());
        return recipes;
    }
}
