package Controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.InstructionStep;
import Model.Recipe;
import Model.RecipeIngredient;
import Repository.DatabaseConnection;
import Repository.InstructionsRepository;
import Repository.RecipeIngRepository;
import Repository.RecipeRepository;

public class RecipeManager {
    private final RecipeRepository recipeRepository;
    private final RecipeIngManager recipeIngManager;
    private final InstructionsManager instructionsManager;

    public RecipeManager(RecipeRepository recipeRepository,DatabaseConnection databaseConnection) {
        RecipeIngRepository repo = new RecipeIngRepository(databaseConnection);
        this.recipeIngManager = new RecipeIngManager(repo);
        this.recipeRepository = recipeRepository;

        InstructionsRepository instRepo = new InstructionsRepository(databaseConnection);
        instructionsManager = new InstructionsManager(instRepo);
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

    public int updateRecipe(int recipeID, String recipeName, int createdBy, int servingSize, String imagePath, String description,
                            int duration, List<String> recipeIngredients,List<String> instructions,List<String> tags) throws SQLException {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);

        List<InstructionStep> instructionSteps = new ArrayList<>();
        for(String instruction: instructions){
            instructionsManager.updateInstruction(Integer.parseInt(instruction.substring(0,instruction.indexOf(":"))),
                    instruction.substring(instruction.indexOf(":") + 1) );
        }


        recipe.setAll(recipeName, createdBy, servingSize, imagePath, description, duration, tags,instructionSteps);
        for(String ingredient : recipeIngredients){
            int ingredientID = Integer.parseInt(ingredient.substring(ingredient.indexOf("(") + 1,ingredient.indexOf(")")));
            String measurementUnit = ingredient.substring(ingredient.indexOf("}") + 2);
            double quantity  = Double.parseDouble(ingredient.substring(ingredient.indexOf("{") + 1,ingredient.indexOf("}")));
            recipeIngManager.updateIngredient(ingredientID,measurementUnit, BigDecimal.valueOf(quantity));
        }
        recipeRepository.updateRecipe(recipe);
        return 0;
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
