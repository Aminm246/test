package ingredient.view;

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

}
