package Controller;

import Model.RecipeIngredient;
import Repository.IngredientsRepository;
import Repository.RecipeIngRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class RecipeIngManager {
    private final RecipeIngRepository recipeIngRepository;
    private IngredientsRepository ingredientsRepository;

    public RecipeIngManager(RecipeIngRepository recipeIngRepository, IngredientsRepository ingredientsRepository) {
        this.recipeIngRepository = recipeIngRepository;
        this.ingredientsRepository = ingredientsRepository;
    }

    public int addIngredient(int ingredientId, int recipeId, String measurementUnit, BigDecimal quantity) throws SQLException {
        RecipeIngredient ingredient = new RecipeIngredient(ingredientId, recipeId, measurementUnit, quantity);
        int recipeIngId = recipeIngRepository.insertIngredient(ingredient);
        if (recipeIngId != -1) {
            System.out.println("Ingredient created with ID: " + ingredientId);
        } else {
            System.out.println("Failed to create ingredient.");
        }
        return recipeIngId;
    }


    public RecipeIngredient getIngredientById(int ingredientId) throws SQLException {
        RecipeIngredient recipeIngredient = recipeIngRepository.getIngredientById(ingredientId);
        recipeIngredient.setIngredient(ingredientsRepository.getIngredientsById(recipeIngredient.getIngredientID()));
        return recipeIngredient;
    }



    public List<RecipeIngredient> getIngredients(List<Integer> ingredientList) throws SQLException {
        List<RecipeIngredient> recipeIngredientList = recipeIngRepository.getAllIngredients();
        for (RecipeIngredient recipeIngredient : recipeIngredientList) {
            recipeIngredient.setIngredient(ingredientsRepository.getIngredientsById(recipeIngredient.getIngredientID()));
        }
        return recipeIngredientList;
    }

    public List<RecipeIngredient> getIngredientsByRecipeId(int recipeId) throws SQLException {
        List<RecipeIngredient> ingredients = recipeIngRepository.getIngredientsByRecipeId(recipeId);
        for (RecipeIngredient ingredient : ingredients) {
            ingredient.setIngredient(ingredientsRepository.getIngredientsById(ingredient.getIngredientID()));
        }
        return ingredients;
    }




    @Override
    public String toString() {
        return "RecipeIngManager{" +
                "recipeIngRepository=" + recipeIngRepository +
                '}';
    }
}
