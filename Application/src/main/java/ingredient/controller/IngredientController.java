package ingredient.controller;

import ingredient.model.IngredientsInventory;
import ingredient.view.IngredientView;

import java.awt.event.ActionEvent;

public class IngredientController {
    private IngredientsInventory ingredientsInventory;
    private IngredientView ingredientView;

    public IngredientController(IngredientsInventory ingredientsInventory, IngredientView ingredientView) {
        this.ingredientsInventory = ingredientsInventory;
        this.ingredientView = ingredientView;
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
