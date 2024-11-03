package Controller;

import java.sql.SQLException;
import java.util.List;

import Model.Recipe;
import Repository.RecipeRepository;

public class RecipeManager {
    private final RecipeRepository recipeRepository;

    public RecipeManager(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipe(int recipeID) throws SQLException {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        if (recipe != null) {
            System.out.println("Recipe found: " + recipe.getRecipeName());
        } else {
            System.out.println("Recipe with ID " + recipeID + " not found.");
        }
        return recipe;
    }

    public int addRecipe(String recipeName, int createdBy, int servingSize, String imagePath, String description, int duration) throws SQLException {

        Recipe recipe = new Recipe(recipeName,createdBy,servingSize,imagePath,description,duration);
        int recipeId = recipeRepository.insertRecipe(recipe);
        if (recipeId != -1) {
            System.out.println("Recipe created with ID: " + recipeId);
        } else {
            System.out.println("Failed to create recipe.");
        }
        return recipeId;
    }

    public List<Recipe> getRecipes() throws SQLException {
        return recipeRepository.getAllRecipes();
    }

    @Override
    public String toString() {
        return "RecipeManager{" +
                "recipeRepository=" + recipeRepository +
                '}';
    }
}
