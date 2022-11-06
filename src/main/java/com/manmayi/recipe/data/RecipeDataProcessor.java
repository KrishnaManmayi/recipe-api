package com.manmayi.recipe.data;

import com.manmayi.recipe.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class RecipeDataProcessor implements ItemProcessor<RecipeInput, Recipe> {

    @Override
    public Recipe process(final RecipeInput recipeInput) {
        Recipe transformedRecipe = new Recipe();

//        if(recipeInput.getCourse()==null || recipeInput.getCourse().trim().length()==0){
//            return null;
//        }

        transformedRecipe.setRecipeId(recipeInput.getId());
        transformedRecipe.setRecipeTitle(recipeInput.getRecipe_title());
        transformedRecipe.setUrl(recipeInput.getUrl());
        transformedRecipe.setVote_count(recipeInput.getVote_count());
        transformedRecipe.setRating(recipeInput.getRating());
        transformedRecipe.setDescription(recipeInput.getDescription());
        transformedRecipe.setCuisine(recipeInput.getCuisine());
        transformedRecipe.setCourse(recipeInput.getCourse());
        transformedRecipe.setDiet(recipeInput.getDiet());
        transformedRecipe.setPrep_time(recipeInput.getPrep_time());
        transformedRecipe.setCook_time(recipeInput.getCook_time());
        transformedRecipe.setInstructions(recipeInput.getInstructions());
        transformedRecipe.setAuthor(recipeInput.getAuthor());
        transformedRecipe.setCategory(recipeInput.getCategory());

        String[] ingredientsArray = recipeInput.getIngredients().split("\\|");
        for(String ingredient : ingredientsArray){
            transformedRecipe.addIngredient(Ingredient.builder().ingredient(ingredient.trim()).build());
        }

        String[] tagsArray = recipeInput.getTags().split("\\|");
        for(String tag : tagsArray){
            transformedRecipe.addTag(Tag.builder().tag(tag.trim()).build());
        }

        log.info("Converting ("+recipeInput+") to ("+transformedRecipe+")");

        return transformedRecipe;
    }
}
