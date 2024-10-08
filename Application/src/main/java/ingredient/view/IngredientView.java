package ingredient.view;

import ingredient.model.Ingredient;
import ingredient.model.IngredientsInventory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class IngredientView {
    @FXML
    private Button createIngredientButton;
    @FXML
    private Button updateIngredientButton;
    @FXML
    private Button deleteIngredientButton;
    @FXML
    private Button dateSubmitButton;
    @FXML
    private Button durationSubmitButton;
    @FXML
    private TextField ingredientName;
    @FXML
    private TextField nutritionalFacts;
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox<String> measurementUnits;

    private IngredientsInventory inventory;

    public String getIngredientName() {
        return ingredientName.getText();
    }

    public String getNutritionalFacts() {
        return nutritionalFacts.getText();
    }

    public String getQuantity() {
        return quantity.getText();
    }

    public String getMeasurementUnit() {
        return measurementUnits.getValue();
    }

    public void updateInventory(IngredientsInventory inventory) {
        this.inventory = inventory;
    }

    public void displayIngredient(Ingredient Ingredient) {
        //Implement Later
    }

    public void displayInventory(IngredientsInventory inventory) {
        //Implement Later
    }



}
