package com.manmayi.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String keyword;
    private List<String> includedIngredients;
    private List<String> excludedIngredients;
    private List<String> categories;
    private List<String> courses;
    private List<String> cuisines;
    private List<String> diets;
    private List<String> tags;
}
