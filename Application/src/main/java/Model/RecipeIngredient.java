package Model;

import java.math.BigDecimal;

public class RecipeIngredient {
    int recipeIngredientID;
    int recipeID;
    Ingredient ingredient;
    MeasurementUnit measurementUnit;
    BigDecimal quantity;

    public RecipeIngredient(int recipeIngredientID, int recipeID, Ingredient ingredient, MeasurementUnit measurementUnit, BigDecimal quantity) {
        this.recipeIngredientID = recipeIngredientID;
        this.recipeID = recipeID;
        this.ingredient = ingredient;
        this.measurementUnit = measurementUnit;
        this.quantity = quantity;
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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
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
                ", ingredient=" + ingredient +
                ", measurementUnit=" + measurementUnit +
                ", quantity=" + quantity +
                '}';
    }
}
