package ingredient.model;

import java.math.BigDecimal;

public class Ingredient {
    int ingredientId;
    String ingredientName;
    NutritionalFacts nutrition;
    BigDecimal quantity;
    MeasurementUnit measurementUnit;

    public Ingredient(int ingredientId, String ingredientName, NutritionalFacts nutrition, MeasurementUnit measurementUnit, BigDecimal quantity) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.nutrition = nutrition;
        this.measurementUnit = measurementUnit;
        this.quantity = quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public NutritionalFacts getNutrition() {
        return nutrition;
    }

    public void setNutrition(NutritionalFacts nutrition) {
        this.nutrition = nutrition;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

}