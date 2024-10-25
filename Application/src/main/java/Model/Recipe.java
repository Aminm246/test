package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int recipeId;
    private String recipeName;
    private List<String> tagList;
    private List<RecipeIngredient> recipeIngredients;
    private List<InstructionStep> instructions;
    private int createdBy;
    private int servingSize;
    private String imagePath;
    private String description;
    private int duration;

    public Recipe(int recipeId, String recipeName, int createdBy, List<String> tagList, int duration, int servingSize, String description,
                  String imagePath, List<RecipeIngredient> recipeIngredients, List<InstructionStep> instructions) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.createdBy = createdBy;
        this.tagList = tagList;
        this.recipeIngredients = recipeIngredients;
        this.instructions = instructions;
        this.duration = duration;
        this.servingSize = servingSize;
        this.description = description;
        this.imagePath = imagePath;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<InstructionStep> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<InstructionStep> instructions) {
        this.instructions = instructions;
    }

    public List<InstructionStep> getInstructionSteps() {
        return instructions;
    }

    public void setInstructionSteps(List<InstructionStep> instructions) {
        this.instructions = instructions;
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

    public String getRecipeName() {
        return recipeName;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", recipeName='" + recipeName + '\'' +
                ", tagList=" + tagList +
                ", recipeIngredients=" + recipeIngredients +
                ", instructions=" + instructions +
                ", createdBy=" + createdBy +
                ", servingSize=" + servingSize +
                ", imagePath='" + imagePath + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}
