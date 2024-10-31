package Controller;

import Model.Ingredient;
import Repository.IngredientsRepository;

import java.sql.SQLException;
import java.util.List;

public class IngredientsManager {
    private final IngredientsRepository ingredientsRepository;

    public IngredientsManager(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    public int addIngredient(String ingredientName) throws SQLException {
        Ingredient ingredient = new Ingredient(ingredientName);
        int ingredientId = ingredientsRepository.insertIngredient(ingredient);
        if (ingredientId != -1) {
            System.out.println("Ingredient created with ID: " + ingredientId);
        } else {
            System.out.println("Failed to create ingredient.");
        }
        return ingredientId;
    }


    public Ingredient getIngredientById(int ingredientId) throws SQLException {
        return ingredientsRepository.getIngredientsById(ingredientId);
    }



    public List<Ingredient> getIngredients(List<Integer> ingredientList) throws SQLException {
        return ingredientsRepository.getAllIngredients();
    }
}
