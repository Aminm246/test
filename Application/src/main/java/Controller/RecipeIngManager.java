package Controller;

import Model.RecipeIngredient;
import Repository.DatabaseConnection;
import Repository.IngredientsRepository;
import Repository.RecipeIngRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class RecipeIngManager {
    private final RecipeIngRepository recipeIngRepository;
    private IngredientsRepository ingredientsRepository;

    public RecipeIngManager(DatabaseConnection databaseConnection) {
        this.recipeIngRepository = new RecipeIngRepository(databaseConnection);
        this.ingredientsRepository = new IngredientsRepository(databaseConnection);
    }

    public int addIngredient(int ingredientId, int recipeId, String measurementUnit, BigDecimal quantity) {
        RecipeIngredient ingredient = new RecipeIngredient(ingredientId, recipeId, measurementUnit, quantity);
        int recipeIngId = recipeIngRepository.insertIngredient(ingredient);
        if (recipeIngId != -1) {
            System.out.println("Ingredient created with ID: " + ingredientId);
        } else {
            System.out.println("Failed to create ingredient.");
        }
        return recipeIngId;
    }


    public RecipeIngredient getIngredientById(int ingredientId) {
        RecipeIngredient recipeIngredient = recipeIngRepository.getIngredientById(ingredientId);
        recipeIngredient.setIngredient(ingredientsRepository.getIngredientsById(recipeIngredient.getIngredientID()));
        return recipeIngredient;
    }

    public void updateIngredient(int ingredientId, String measurementUnit, BigDecimal quantity) {
        RecipeIngredient ingredient = recipeIngRepository.getIngredientById(ingredientId);
        ingredient.setMeasurementUnit(measurementUnit);
        ingredient.setQuantity(quantity);
        recipeIngRepository.updateIngredient(ingredient);
    }


    public List<RecipeIngredient> getIngredients(List<Integer> ingredientList) {
        List<RecipeIngredient> recipeIngredientList = recipeIngRepository.getAllIngredients();
        for (RecipeIngredient recipeIngredient : recipeIngredientList) {
            recipeIngredient.setIngredient(ingredientsRepository.getIngredientsById(recipeIngredient.getIngredientID()));
        }
        return recipeIngredientList;
    }

    public List<RecipeIngredient> getIngredientsByRecipeId(int recipeId) {
        List<RecipeIngredient> ingredients = recipeIngRepository.getIngredientsByRecipeId(recipeId);
        for (RecipeIngredient ingredient : ingredients) {
            ingredient.setIngredient(ingredientsRepository.getIngredientsById(ingredient.getIngredientID()));
        }
        return ingredients;
    }

    public void removeIngredient(int recIngID){
        recipeIngRepository.deleteIngredient(recIngID);
    }



    @Override
    public String toString() {
        return "RecipeIngManager{" +
                "recipeIngRepository=" + recipeIngRepository +
                '}';
    }
}
