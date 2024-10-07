package ingredient.controller;

import ingredient.model.IngredientsInventory;
import ingredient.view.IngredientListView;
import ingredient.view.IngredientView;

import java.awt.event.ActionEvent;

public class IngredientController {
    private IngredientsInventory ingredientsInventory;
    private IngredientView ingredientView;
    private IngredientListView ingredientListView;

    public IngredientController(IngredientsInventory ingredientsInventory, IngredientView ingredientView, IngredientListView ingredientListView) {
        this.ingredientsInventory = ingredientsInventory;
        this.ingredientView = ingredientView;
        this.ingredientListView = ingredientListView;
    }

    public void createIngredient(ActionEvent actionEvent) {
        //Implement Later
    }

    public void deleteIngredient(ActionEvent actionEvent) {
        //Implement Later
    }

    public void updateIngredient(ActionEvent actionEvent) {
        //Implement Later
    }

    public void selectIngredient(ActionEvent actionEvent) {
        //Implement Later
    }

    public void viewIngredients(ActionEvent actionEvent) {
        //Implement Later
    }

    public void updateView() {
        //Implement Later
    }
}
