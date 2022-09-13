package com.manmayi.recipe.data;

import lombok.Data;

@Data
public class RecipeInput {
    private int id;
    private String recipe_title;
    private String url;
    private int vote_count;
    private double rating;
    private String description;
    private String cuisine;
    private String course;
    private String diet;
    private String prep_time;
    private String cook_time;
    private String ingredients;
    private String instructions;
    private String author;
    private String tags;
    private String category;
}
