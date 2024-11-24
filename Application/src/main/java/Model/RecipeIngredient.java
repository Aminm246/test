package Model;

import java.math.BigDecimal;

public class RecipeIngredient {
    private int recipeIngredientID;
    private int recipeID;
    private int ingredientID;
    private String measurementUnit;
    private BigDecimal quantity;
    private Ingredient ingredient;

    public RecipeIngredient(int recipeIngredientID, int recipeID, Ingredient ingredient, String measurementUnit, BigDecimal quantity) {
        this.recipeIngredientID = recipeIngredientID;
        this.recipeID = recipeID;
        this.ingredient = ingredient;
        this.ingredientID = ingredient.getIngredientId();
        this.measurementUnit = measurementUnit;
        this.quantity = quantity;
    }

    public RecipeIngredient(int ingredientID, int recipeID, String measurementUnit, BigDecimal quantity) {
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
        this.measurementUnit = measurementUnit;
        this.quantity = quantity;
    }

    public RecipeIngredient() {

    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getRecipeIngredientID() {
        return recipeIngredientID;
    }

    public void setRecipeIngredientID(int recipeIngredientID) {
        this.recipeIngredientID = recipeIngredientID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public java.lang.String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String string) {
        this.measurementUnit = string;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "recipeIngredientID=" + recipeIngredientID +
                ", recipeID=" + recipeID +
                ", ingredientID=" + ingredientID +
                ", measurementUnit='" + measurementUnit + '\'' +
                ", quantity=" + quantity +
                ", ingredient=" + ingredient +
                '}';
    }

    public String parseIngredient(){
        return ingredient.getIngredientName() + ": " + quantity + " " + measurementUnit;
    }
}
