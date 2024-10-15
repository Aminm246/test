package ingredient.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IngredientsInventory {
    List<Ingredient> inventory;
    int ingredientID;

    public IngredientsInventory(){
        inventory = new ArrayList<Ingredient>();
        ingredientID = 1;
    }
    public IngredientsInventory(List<Ingredient> inventory) {
        this.inventory = inventory;
    }

    public List<Ingredient> getInventory() {
        return inventory;
    }

    public void setInventory(List<Ingredient> inventory) {
        this.inventory = inventory;
    }

    public Ingredient addIngredient(String ingredientName) {
        Ingredient x = new Ingredient(ingredientName,ingredientID);
        inventory.add(x);
        ingredientID++;
        return x;
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

    public void createIngredient(String name) {
        Ingredient ingredient = new Ingredient(name);

    }
}
