package Controller;

import Model.RecipeIngredient;
import Repository.RecipeIngRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class RecipeIngManager {
    private final RecipeIngRepository recipeIngRepository;

    public RecipeIngManager(RecipeIngRepository recipeIngRepository) {
        this.recipeIngRepository = recipeIngRepository;
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
        return recipeIngRepository.getIngredientById(ingredientId);
    }

    public void updateIngredient(int ingredientId, String measurementUnit, BigDecimal quantity) throws SQLException {
        RecipeIngredient ingredient = recipeIngRepository.getIngredientById(ingredientId);
        ingredient.setMeasurementUnit(measurementUnit);
        ingredient.setQuantity(quantity);
        recipeIngRepository.updateIngredient(ingredient);
    }

    public List<RecipeIngredient> getIngredients(List<Integer> ingredientList) throws SQLException {
        return recipeIngRepository.getAllIngredients();
    }

    public List<RecipeIngredient> getIngredientsByRecipeId(int recipeId) throws SQLException {
        return recipeIngRepository.getIngredientsByRecipeId(recipeId);
    }


    @Override
    public String toString() {
        return "RecipeIngManager{" +
                "recipeIngRepository=" + recipeIngRepository +
                '}';
    }
}
