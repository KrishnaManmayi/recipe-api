package com.manmayi.recipe.service;

import com.manmayi.recipe.model.Ingredient;
import com.manmayi.recipe.model.Recipe;
import com.manmayi.recipe.model.SearchCriteria;
import com.manmayi.recipe.model.Tag;
import com.manmayi.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> searchRecipeByKeyword(String keyword) {
        return recipeRepository.searchRecipeByKeyword(keyword);
    }

    public List<Recipe> searchRecipeWithIncludedIngredients(List<Ingredient> ingredients) {
        return recipeRepository.searchRecipeWithIncludedIngredients(ingredients);
    }

    public List<Recipe> searchRecipeWithExcludedIngredients(List<Ingredient> ingredients) {
        return recipeRepository.searchRecipeWithExcludedIngredients(ingredients);
    }

    public List<Recipe> searchRecipeByCategory(List<String> categories) {
        return recipeRepository.findByCategoryIn(categories);
    }

    public List<Recipe> searchRecipeByCourse(List<String> courses) {
        return recipeRepository.findByCourseIn(courses);
    }

    public List<Recipe> searchRecipeByCuisine(List<String> cuisines) {
        return recipeRepository.findByCuisineIn(cuisines);
    }

    public List<Recipe> searchRecipeByDiet(List<String> diets) {
        return recipeRepository.findByDietIn(diets);
    }

    public List<Recipe> searchRecipeByTag(List<String> tags) {
        return recipeRepository.searchRecipeByTag(tags);
    }

    public List<Recipe> search(SearchCriteria searchCriteria) {
        List<Recipe> recipeList =  recipeRepository.findAll(new Specification<Recipe>() {
            @Override
            public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Recipe, Ingredient> recipeIngredientJoin = root.join("ingredients");
                Join<Recipe, Tag> recipeTagJoin = root.join("tags");

                List<Predicate> predicates = new ArrayList<>();

                if(searchCriteria.getKeyword()!=null && searchCriteria.getKeyword().length()!=0){
                    predicates.add(criteriaBuilder.like(root.get("recipeTitle"), "%"+searchCriteria.getKeyword()+"%"));
                }
                if(searchCriteria.getIncludedIngredients()!=null && !searchCriteria.getIncludedIngredients().isEmpty()){
                    predicates.add(recipeIngredientJoin.get("ingredient").in(searchCriteria.getIncludedIngredients()));
                }
                if(searchCriteria.getCategories()!=null && !searchCriteria.getCategories().isEmpty()){
                    predicates.add(root.get("category").in(searchCriteria.getCategories()));
                }
                if(searchCriteria.getCourses()!=null && !searchCriteria.getCourses().isEmpty()){
                    predicates.add(root.get("course").in(searchCriteria.getCourses()));
                }
                if(searchCriteria.getCuisines()!=null && !searchCriteria.getCuisines().isEmpty()){
                    predicates.add(root.get("cuisine").in(searchCriteria.getCuisines()));
                }
                if(searchCriteria.getDiets()!=null && !searchCriteria.getDiets().isEmpty()){
                    predicates.add(root.get("diet").in(searchCriteria.getDiets()));
                }
                if(searchCriteria.getTags()!=null && !searchCriteria.getTags().isEmpty()){
                    predicates.add(recipeTagJoin.get("tag").in(searchCriteria.getTags()));
                }

                query.distinct(true);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        if(searchCriteria.getExcludedIngredients()!=null && !searchCriteria.getExcludedIngredients().isEmpty()){
            List<Recipe> excludedRecipesBasedOnIngredients = recipeRepository.findAll(new Specification<Recipe>() {
                @Override
                public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    Join<Recipe, Ingredient> recipeIngredientJoin = root.join("ingredients");

                    List<Predicate> predicates = new ArrayList<>();
                    if(searchCriteria.getExcludedIngredients()!=null && !searchCriteria.getExcludedIngredients().isEmpty()){
                        predicates.add(recipeIngredientJoin.get("ingredient").in(searchCriteria.getExcludedIngredients()));
                    }
                    query.distinct(true);
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            });

            recipeList.removeAll(new HashSet<>(excludedRecipesBasedOnIngredients));
        }
        return recipeList;
    }
}
