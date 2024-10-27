package Model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import Controller.IngredientsInventory;
import Repository.RecipeRepository;

public class RecipeManager {
    private Map<Integer, Recipe> recipeList;
    private IngredientsInventory ingredientsInventory;
    private int nextRecipeId;
    private final RecipeRepository recipeRepository;

    public RecipeManager(RecipeRepository recipeRepository) {
        this.recipeList = new HashMap<>();
        this.nextRecipeId = 1;
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

    public int addRecipe(String recipeName, int createdBy, List<String> tagList, int duration, int servingSize, String description,
                         String imagePath, List<RecipeIngredient> recipeIngredients, List<InstructionStep> instructions) throws SQLException {

        Recipe recipe = new Recipe(nextRecipeId,recipeName,createdBy,tagList,duration,servingSize,description,imagePath,recipeIngredients,instructions);
        int recipeId = recipeRepository.insertRecipe(recipe);
        if (recipeId != -1) {
            System.out.println("Recipe created with ID: " + recipeId);
        } else {
            System.out.println("Failed to create recipe.");
        }
        return recipeId;
    }

    private Recipe updateRecipe(Recipe updatedRecipe) {
        recipeList.put(updatedRecipe.getRecipeID(), updatedRecipe);
        return updatedRecipe;
    }

    public void deleteRecipe(int recipeID) {
        recipeList.remove(recipeID);
    }

    public List<Recipe> findRecipes(String keyword) {
        return null;
    }

    public List<String> addIngredient(int ingredientID) {
        return new ArrayList<>();
    }

    public List<String> removeIngredient(int ingredientID) {
        return new ArrayList<>();
    }

    public List<InstructionStep> editInstruction(int recipeID, int stepNumber, String newInstruction) {
        return null;
    }

    public List<String> addTag(String tag) {
        return new ArrayList<>();
    }

    public List<String> removeTag(String tag) {
        return new ArrayList<>();
    }

    public List<String> generateGroceryList(int recipeID){
        return null;
    }

    public void setRecipeList(Map<Integer, Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public List<Recipe> getRecipes() throws SQLException {
        return recipeRepository.getAllRecipes();
    }
    public void setIngredientInventory(IngredientsInventory ingredientInventory) {
        this.ingredientsInventory = ingredientInventory;
    }
    
    @Override
    public String toString() {
        return "RecipeManager{" +
                "recipeList=" + recipeList +
                ", ingredientsInventory=" + ingredientsInventory +
                ", nextRecipeId=" + nextRecipeId +
                '}';
    }

}
