package Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import Model.InstructionStep;
import Model.Recipe;
import Model.RecipeIngredient;

public class RecipeManager {
    private Map<Integer, Recipe> recipeList;
    private IngredientsInventory ingredientsInventory;
    private int nextRecipeId;

    public RecipeManager() {
        this.recipeList = new HashMap<>();
        this.nextRecipeId = 1;
    }

    public Recipe getRecipe(int recipeID) {
        return recipeList.get(recipeID);
    }

    public int addRecipe(String recipeName, int createdBy, List<String> tagList, int duration, int servingSize, String description,
                         String imagePath, List<RecipeIngredient> recipeIngredients, List<InstructionStep> instructions){

        Recipe recipe = new Recipe(nextRecipeId,recipeName,createdBy,tagList,duration,servingSize,description,imagePath,recipeIngredients,instructions);
        recipeList.put(nextRecipeId,recipe);
        nextRecipeId++;
        return recipe.getRecipeID();
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

    public Object[] getRecipes(){
        return recipeList.keySet().toArray();
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

    public IngredientsInventory getIngredientInventory() {
        return ingredientsInventory;
    }
}
