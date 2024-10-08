package recipe.model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import ingredient.model.IngredientsInventory;

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

    public Recipe addRecipe(String recipeName) {
        return null;
    }

    public Recipe updateRecipe(int recipeID, Recipe updatedRecipe) {
        return null;
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

    public List<String> addCategory(String category) {
        return new ArrayList<>();
    }

    public List<String> removeCategory(String category) {
        return new ArrayList<>();
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
}
