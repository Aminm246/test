package recipe.controller;

import javafx.event.ActionEvent;
import recipe.model.RecipeManager;
import recipe.view.RecipeView;
import recipe.model.InstructionStep;
import recipe.model.Recipe;

import java.util.List;

public class RecipeController {
    private RecipeManager model;
    private RecipeView view;

    public RecipeController(RecipeManager model, RecipeView view) {
        this.model = model;
        this.view = view;
    }

    public Recipe getRecipe(ActionEvent event) {
        return null;
    }

    public void updateView(){}
    public Recipe createRecipe(ActionEvent event) {
        return null;
    }

    public Recipe updateRecipe(ActionEvent event) {
        return null;
    }

    public void deleteRecipe(ActionEvent event) {
    }

    public List<Recipe> findRecipes(ActionEvent event) {
        return null;
    }

    public void addIngredient(ActionEvent event) {

    }

    public void removeIngredient(ActionEvent event) {

    }

    public List<InstructionStep> editInstruction(ActionEvent event) {
        return null;
    }

    public void createTag(ActionEvent event) {
    }

    public void deleteTag(ActionEvent event) {
    }

    public void createCategory(ActionEvent event) {
    }

    public void deleteCategory(ActionEvent event) {
    }

    public int setDuration(ActionEvent event) {
        return 0;
    }

    public String setImagePath(ActionEvent event) {
        return null;
    }

    public String setDescription(ActionEvent event) {
        return null;
    }

    public String setRecipeName(ActionEvent event) {
        return null;
    }

    public List<String> generateGroceryList(int recipeID){
        return null;
    }

    @Override
    public String toString() {
        return "RecipeController{" +
                "model=" + model +
                ", view=" + view +
                '}';
    }
}
