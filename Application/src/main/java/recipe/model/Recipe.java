package recipe.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ingredient.model.Ingredient;
import ingredient.model.NutritionalFacts;

public class Recipe {
    private int recipeId;
    private String recipeName;
    private List<String> tagList;
    private List<Ingredient> ingredientList;
    private List<BigDecimal> ingredientQtyList;
    private List<InstructionStep> instructions;
    private NutritionalFacts nutrition;
    private int createdBy;
    private int servingSize;
    private String imagePath;
    private String description;
    private int duration;

    public Recipe(int recipeId, String recipeName, int createdBy,ArrayList<String> tagList, int duration, int servingSize, String description,
                  String imagePath, List<Ingredient> ingredientList, List<BigDecimal> ingredientQtyList, List<InstructionStep> instructions) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.createdBy = createdBy;
        this.tagList = tagList;
        this.ingredientList = ingredientList;
        this.ingredientQtyList = ingredientQtyList;
        this.instructions = instructions;
        this.duration = duration;
        this.servingSize = servingSize;
        this.description = description;
        this.imagePath = imagePath;
    }

    public void addIngredient(Ingredient ingredient, BigDecimal quantity) {
        ingredientList.add(ingredient);
        ingredientQtyList.add(quantity);
    }

    public void removeIngredient(Ingredient ingredient) {
        int index = ingredientList.indexOf(ingredient);
        ingredientList.remove(index);
        ingredientQtyList.remove(index);
    }

    public List<InstructionStep> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<InstructionStep> instructions) {
        this.instructions = instructions;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<InstructionStep> getInstructionSteps() {
        return instructions;
    }

    public void setInstructionSteps(List<InstructionStep> instructions) {
        this.instructions = instructions;
    }

    public NutritionalFacts getNutrition() {
        return nutrition;
    }

    public NutritionalFacts calculateNutrition() {
        return nutrition;
    }

    public List<String> getTags() {
        return tagList;
    }

    public void setTags(List<String> tags) {
        this.tagList = tags;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeID() {
        return recipeId;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }
}
