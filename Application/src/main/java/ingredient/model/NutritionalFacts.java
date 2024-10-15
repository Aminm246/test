package ingredient.model;

import java.math.BigDecimal;

public class NutritionalFacts {
    int nutritionId;
    BigDecimal calories;
    BigDecimal fat;
    BigDecimal carbohydrates;
    BigDecimal protein;
    BigDecimal sodium;
    BigDecimal fiber;
    BigDecimal sugar;

    public NutritionalFacts(){

    }
    public NutritionalFacts(BigDecimal calories, int nutritionId, BigDecimal fat, BigDecimal carbohydrates, BigDecimal protein, BigDecimal sodium, BigDecimal fiber, BigDecimal sugar) {
        this.calories = calories;
        this.nutritionId = nutritionId;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.sodium = sodium;
        this.fiber = fiber;
        this.sugar = sugar;
    }

    public int getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(int nutritionId) {
        this.nutritionId = nutritionId;
    }

    public BigDecimal getCalories() {
        return calories;
    }

    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }

    public BigDecimal getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(BigDecimal carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getSodium() {
        return sodium;
    }

    public void setSodium(BigDecimal sodium) {
        this.sodium = sodium;
    }

    public BigDecimal getFiber() {
        return fiber;
    }

    public void setFiber(BigDecimal fiber) {
        this.fiber = fiber;
    }

    public BigDecimal getSugar() {
        return sugar;
    }

    public void setSugar(BigDecimal sugar) {
        this.sugar = sugar;
    }

}
