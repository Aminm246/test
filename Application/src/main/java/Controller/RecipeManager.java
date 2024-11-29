package Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Model.InstructionStep;
import Model.Recipe;
import Repository.DatabaseConnection;
import Repository.RecipeRepository;

public class RecipeManager {
    private final RecipeRepository recipeRepository;
    private final RecipeTagManager recipeTagManager;
    private final RecipeIngManager recipeIngManager;
    private final InstructionsManager instructionsManager;

    public RecipeManager(DatabaseConnection databaseConnection, RecipeTagManager recipeTagManager, RecipeIngManager recipeIngManager, InstructionsManager instructionsManager) {
        this.recipeRepository = new RecipeRepository(databaseConnection);
        this.recipeTagManager = recipeTagManager;
        this.recipeIngManager = recipeIngManager;
        this.instructionsManager = instructionsManager;
    }

    public Recipe getRecipe(int recipeID)  {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        recipe.setRecipeIngredients(recipeIngManager.getIngredientsByRecipeId(recipe.getRecipeID()));
        recipe.setInstructions(instructionsManager.getInstructionsByRecipeId(recipe.getRecipeID()));
        recipe.setTagList(recipeTagManager.getTagsByRecipeId(recipe.getRecipeID()));
        return recipe;
    }

    public int addRecipe(String recipeName, int createdBy, int servingSize, String imagePath, String description, int duration)  {

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
                            int duration, List<String> recipeIngredients,List<String> instructions,List<String> tags)  {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);

        List<InstructionStep> instructionSteps = new ArrayList<>();
        for(String instruction: instructions){
            instructionsManager.updateInstruction(Integer.parseInt(instruction.substring(0,instruction.indexOf(":"))),
                    instruction.substring(instruction.indexOf(":") + 1) );
        }

        recipeTagManager.removeAllTags(recipeID);
        for(String tag: tags){
            int tagID = Integer.parseInt(tag.substring(tag.indexOf("(") + 1,tag.indexOf(")")));
            recipeTagManager.addTag(recipeID,tagID);
        }


        for(String ingredient : recipeIngredients){
            int ingredientID = Integer.parseInt(ingredient.substring(ingredient.indexOf("(") + 1,ingredient.indexOf(")")));
            String measurementUnit = ingredient.substring(ingredient.indexOf("}") + 2);
            double quantity  = Double.parseDouble(ingredient.substring(ingredient.indexOf("{") + 1,ingredient.indexOf("}")));
            recipeIngManager.updateIngredient(ingredientID,measurementUnit, BigDecimal.valueOf(quantity));
        }

        recipe.setAll(recipeName, createdBy, servingSize, imagePath, description, duration);
        recipeRepository.updateRecipe(recipe);
        return 0;
    }

    public void removeRecipe(int recipeID){
        recipeRepository.deleteRecipe(recipeID);
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipes = recipeRepository.getAllRecipes();
        for (Recipe recipe : recipes) {
            recipe.setRecipeIngredients(recipeIngManager.getIngredientsByRecipeId(recipe.getRecipeID()));
            recipe.setInstructions(instructionsManager.getInstructionsByRecipeId(recipe.getRecipeID()));
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
