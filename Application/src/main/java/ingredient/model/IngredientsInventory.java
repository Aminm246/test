package ingredient.model;

import java.math.BigDecimal;
import java.util.List;

public class IngredientsInventory {
    List<Ingredient> inventory;

    public IngredientsInventory(List<Ingredient> inventory) {
        this.inventory = inventory;
    }

    public List<Ingredient> getInventory() {
        return inventory;
    }

    public void setInventory(List<Ingredient> inventory) {
        this.inventory = inventory;
    }

    public void addIngredient(Ingredient ingredient) {
        this.inventory.add(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        this.inventory.remove(ingredient);
    }

    public void updateIngredient(Ingredient ingredient) {
        this.inventory.set(inventory.indexOf(ingredient), ingredient);
    }

    public void getIngredientById(int ingredientId) {
        //Implement later
    }

    public void getIngredientByName(String name) {
        //Implement later
    }

    public void createIngredient(String name, NutritionalFacts nutritionalFacts, BigDecimal quantity, MeasurementUnit measurementUnit) {
        //Implement later
    }
}
