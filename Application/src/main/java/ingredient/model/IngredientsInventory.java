package ingredient.model;

import recipe.model.Recipe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientsInventory {
    private Map<Integer, Ingredient> inventory;
    int ingredientID;

    public IngredientsInventory(){

        inventory = new HashMap<>();
        ingredientID = 1;
    }
    public IngredientsInventory(Map<Integer, Ingredient> inventory) {
        this.inventory = inventory;
    }

    public Map<Integer, Ingredient> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Integer, Ingredient> inventory) {
        this.inventory = inventory;
    }

    public Ingredient addIngredient(String ingredientName) {
        Ingredient x = new Ingredient(ingredientName,ingredientID);
        inventory.put(ingredientID, x);
        ingredientID++;
        return x;
    }

    public void deleteIngredient(Ingredient ingredient) {
        this.inventory.remove(ingredient.getIngredientId());
    }

    public void updateIngredient(Ingredient ingredient) {
        //this.inventory.set(inventory.indexOf(ingredient), ingredient);
    }

    public Ingredient getIngredientById(int ingredientId) {
        return inventory.get(ingredientId);
        //Implement later
    }

    public void getIngredientByName(String name) {
        //Implement later
    }

    public void createIngredient(String name, NutritionalFacts nutritionalFacts, BigDecimal quantity, MeasurementUnit measurementUnit) {
        //Implement later
    }

    public void createIngredient(String name) {
        Ingredient ingredient = new Ingredient(name,ingredientID);
        ingredientID++;

    }

    public List<Ingredient> getIngredients(List<Integer> ingredientList) {
        List<Ingredient> ingredients = new ArrayList<>();
        for(Integer ingredientID: ingredientList){
            ingredients.add(getIngredientById(ingredientID));
        }
        return ingredients;
    }
}
