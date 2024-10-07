package recipe.classes;

import java.util.ArrayList;
import java.util.List;
import ingredient.model.Ingredient;
import ingredient.model.NutritionalFacts;

public class Recipe {
    private int recipeId;
    private String recipeName;
    private List<String> categoryList;
    private List<String> tagList;
    private List<Ingredient> ingredientList;
    private List<InstructionStep> instructions;
    private NutritionalFacts nutrition;
    private int createdBy;
    private int servingSize;
    private String imagePath;
    private String description;
    private int duration;

    public Recipe(int recipeId, String recipeName, int createdBy) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.createdBy = createdBy;
        this.categoryList = new ArrayList<>();
        this.tagList = new ArrayList<>();
        this.ingredientList = new ArrayList<Ingredient>();
        this.instructions = new ArrayList<InstructionStep>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredientList.remove(ingredient);
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

    public List<String> getCategories() {
        return categoryList;
    }

    public void setCategories(List<String> categories) {
        this.categoryList = categories;
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
}
