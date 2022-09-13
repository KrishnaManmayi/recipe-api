package com.manmayi.recipe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {

    @Id
    private long recipeId;
    private String recipe_title;
    private String url;
    private int vote_count;
    private double rating;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String prep_time;
    private String cook_time;
    @Column(columnDefinition = "TEXT")
    private String instructions;
    private String cuisine;
    private String course;
    private String diet;
    private String category;
    private String author;

    //Many to many relationships
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredients_mapping",
            joinColumns = @JoinColumn(
                    name = "recipe_id",
                    referencedColumnName = "recipeId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ingredient_id",
                    referencedColumnName = "ingredient"
            )
    )
    private List<Ingredient> ingredients;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_tags_mapping",
            joinColumns = @JoinColumn(
                    name = "recipe_id",
                    referencedColumnName = "recipeId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id",
                    referencedColumnName = "tag"
            )
    )
    private List<Tag> tags;

    public void addIngredient(Ingredient ingredient){
        if(ingredients == null){
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
    }

    public void addTag(Tag tag){
        if(tags == null){
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }
}
