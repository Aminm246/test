package Controller;

import Model.Ingredient;
import Repository.DatabaseConnection;
import Repository.IngredientsRepository;

import java.sql.SQLException;
import java.util.List;

public class IngredientsManager {
    private final IngredientsRepository ingredientsRepository;

    public IngredientsManager(DatabaseConnection databaseConnection) {
        this.ingredientsRepository = new IngredientsRepository(databaseConnection);
    }

    public int addIngredient(String ingredientName){
        Ingredient ingredient = new Ingredient(ingredientName);
        int ingredientId = ingredientsRepository.insertIngredient(ingredient);
        if (ingredientId != -1) {
            System.out.println("Ingredient created with ID: " + ingredientId);
        } else {
            System.out.println("Failed to create ingredient.");
        }
        return ingredientId;
    }


    public Ingredient getIngredientById(int ingredientId){
        return ingredientsRepository.getIngredientsById(ingredientId);
    }



    public List<Ingredient> getIngredients(List<Integer> ingredientList){
        return ingredientsRepository.getAllIngredients();
    }
}
