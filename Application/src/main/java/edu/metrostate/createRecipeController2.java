package edu.metrostate;

import ingredient.model.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class createRecipeController2 {

    @FXML
    private TextField recipeNameInput, ingredient1NameInput, ingredient2NameInput, ingredient3NameInput,
            ingredient4NameInput, ingredient5NameInput, ingredient6NameInput, ingredient1QtyInput,
            ingredient2QtyInput, ingredient3QtyInput, ingredient4QtyInput, ingredient5QtyInput,
            ingredient6QtyInput, tag1Input, tag2Input, tag3Input, tag4Input, tag5Input, durationInput, servingSizeInput, imagePathInput;
    @FXML
    private TextArea instruction1Input, instruction2Input, instruction3Input, instruction4Input,
            instruction5Input, recipeDescriptionInput;


    @FXML
    private Button ingredient1Submit, ingredient2Submit, ingredient3Submit, ingredient4Submit,
            ingredient5Submit, ingredient6Submit, recipeNameSubmit, descriptionSubmit, tag1Submit, tag2Submit, tag3Submit, tag4Submit,
            tag5Submit, instruction1SubmitButton, instruction2SubmitButton, instruction3SubmitButton,
            instruction4SubmitButton, instruction5SubmitButton, ingredient5NameSubmit, ingredient6NameSubmit,
            durationSubmit, servingSizeSubmit, imagePathSubmit,
            ingredientsSubmit, instructionsSubmit, tagsSubmit, recipeSubmit, cancelRecipe;

    @FXML
    private Text recipeNameLabel, recipeTagsLabel, ingredientNameLabel1, ingredientNameLabel2, ingredientNameLabel3,
            ingredientQtyLabel1, ingredientQtyLabel2, ingredientQtyLabel3, recipeDescriptionLabel,
            ingredientNameLabel4, ingredientNameLabel5, ingredientQtyLabel4, ingredientQtyLabel5,
            instructionsLabel, durationLabel, servingSizeLabel, imagePathLabel;

    String recipeName;
    List<Ingredient> ingredientList;
    List<BigDecimal> ingredientQtyList;

    @FXML
    public void initialize() {
        ingredientList = new ArrayList<Ingredient>();
        ingredientQtyList = new ArrayList<BigDecimal>();
        recipeNameSubmit.setOnAction(event -> onRecipeNameSubmitClicked());
        ingredient1Submit.setOnAction(event -> onIngredient1SubmitClicked());
        ingredientsSubmit.setOnAction(event -> onIngredientsSubmitClicked());
    }

    private void onIngredientsSubmitClicked() {
        System.out.println(ingredientList.toString());
        System.out.println(ingredientQtyList.toString());
    }

    private void onIngredient1SubmitClicked() {
        String ingredientName = ingredient1NameInput.getText().trim();
        String ingredientQty = ingredient1QtyInput.getText().trim();

        if (ingredientName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient name must not be empty, please enter a valid value.");
            alert.showAndWait();

        }

        if (ingredientQty.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must not be empty, please enter a valid value.");
            alert.showAndWait();
        }

        try {
            BigDecimal qty = new BigDecimal(ingredientQty);
            System.out.println("Ingredient Name: " + ingredientName);
            System.out.println("Ingredient Quantity: " + qty);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must be a valid integer.");
            alert.showAndWait();
            ingredient1QtyInput.clear();
            return;
        }
        ingredient1NameInput.setDisable(true);
        ingredient1QtyInput.setDisable(true);
        ingredient1Submit.setDisable(true);
        ingredientsSubmit.setDisable(false);
        ingredient2NameInput.setDisable(false);
        ingredient2QtyInput.setDisable(false);
        ingredient2Submit.setDisable(false);
        ingredientList.add(new Ingredient(ingredientName));
        ingredientQtyList.add(new BigDecimal(ingredientQty));

    }


    @FXML
    private void onRecipeNameSubmitClicked() {
        String recipeName = recipeNameInput.getText().trim();

        if (recipeName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Recipe cannot be empty, please enter a valid recipe name.");
            alert.showAndWait();

        } else {
            System.out.println("Recipe Name: " + recipeName);
            this.recipeName = recipeName;
            recipeNameInput.setDisable(true);
            recipeNameSubmit.setDisable(true);
            ingredient1NameInput.setDisable(false);
            ingredient1QtyInput.setDisable(false);
            ingredient1Submit.setDisable(false);
        }
    }

}
