package Controller;

import java.sql.SQLException;
import java.util.List;

import Model.Recipe;
import Repository.InstructionsRepository;
import Repository.RecipeRepository;

public class RecipeManager {
    private final RecipeRepository recipeRepository;
    private RecipeTagManager recipeTagManager;
    private RecipeIngManager recipeIngManager;
    private InstructionsRepository instructionsRepository;

    public RecipeManager(RecipeRepository recipeRepository, RecipeTagManager recipeTagManager, RecipeIngManager recipeIngManager, InstructionsRepository instructionsRepository) {
        this.recipeTagManager = recipeTagManager;
        this.recipeIngManager = recipeIngManager;
        this.instructionsRepository = instructionsRepository;
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipe(int recipeID) throws SQLException {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        recipe.setRecipeIngredients(recipeIngManager.getIngredientsByRecipeId(recipe.getRecipeID()));
        recipe.setInstructions(instructionsRepository.getInstructionsByRecipeId(recipe.getRecipeID()));
        recipe.setTagList(recipeTagManager.getTagsByRecipeId(recipe.getRecipeID()));
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
        List<Recipe> recipes = recipeRepository.getAllRecipes();
        for (Recipe recipe : recipes) {
            recipe.setRecipeIngredients(recipeIngManager.getIngredientsByRecipeId(recipe.getRecipeID()));
            recipe.setInstructions(instructionsRepository.getInstructionsByRecipeId(recipe.getRecipeID()));
            recipe.setTagList(recipeTagManager.getTagsByRecipeId(recipe.getRecipeID()));
        }
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeManager{" +
                "recipeRepository=" + recipeRepository +
                '}';
    }
}
