package edu.metrostate;

import ingredient.model.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class createRecipeViewController {

    @FXML
    private TextField recipeNameInput, ingredient1NameInput, ingredient2NameInput, ingredient3NameInput,
            ingredient4NameInput, ingredient5NameInput, ingredient6NameInput, ingredient1QtyInput,
            ingredient2QtyInput, ingredient3QtyInput, ingredient4QtyInput, ingredient5QtyInput,
            ingredient6QtyInput, tag1Input, tag2Input, tag3Input, tag4Input, tag5Input, durationInput, servingSizeInput, imagePathInput;
    @FXML
    private TextArea instruction1Input, instruction2Input, instruction3Input, instruction4Input,
            instruction5Input, recipeDescriptionInput;


    @FXML
    private Button ingredientSubmit, recipeNameSubmit, descriptionSubmit, tag1Submit, tag2Submit, tag3Submit, tag4Submit,
            tag5Submit, instruction1SubmitButton, instruction2SubmitButton, instruction3SubmitButton,
            instruction4SubmitButton, instruction5SubmitButton, ingredient5NameSubmit, ingredient6NameSubmit,
            durationSubmit, servingSizeSubmit, imagePathSubmit,
            allIngredientsSubmit, instructionsSubmit, tagsSubmit, recipeSubmit, cancelRecipe;

    @FXML
    private Text recipeNameLabel, recipeTagsLabel, ingredientNameLabel1, ingredientNameLabel2, ingredientNameLabel3,
            ingredientQtyLabel1, ingredientQtyLabel2, ingredientQtyLabel3, recipeDescriptionLabel,
            ingredientNameLabel4, ingredientNameLabel5, ingredientQtyLabel4, ingredientQtyLabel5,
            instructionsLabel, durationLabel, servingSizeLabel, imagePathLabel;

    String recipeName;
    List<Ingredient> ingredientList;
    List<BigDecimal> ingredientQtyList;
    List<TextField> ingredientNameInputs;
    List<TextField> ingredientQtyInputs;
    List<Button> ingredientSubmitButtons;
    int ingredientCount;

    @FXML
    public void initialize() {
        ingredientCount = 0;
        ingredientList = new ArrayList<Ingredient>();
        ingredientQtyList = new ArrayList<BigDecimal>();
        ingredientNameInputs = List.of(ingredient1NameInput, ingredient2NameInput, ingredient3NameInput, ingredient4NameInput, ingredient5NameInput, ingredient6NameInput);
        ingredientQtyInputs = List.of(ingredient1QtyInput, ingredient2QtyInput, ingredient3QtyInput, ingredient4QtyInput, ingredient5QtyInput, ingredient6QtyInput);
        recipeNameSubmit.setOnAction(event -> addRecipeNameClick());
        ingredientSubmit.setOnAction(event -> addSingleIngredientClick());
        allIngredientsSubmit.setOnAction(event -> addAllIngredientsClick());
    }

    private void addAllIngredientsClick() {
        if (ingredientCount < 6) {
            if (!ingredientNameInputs.get(ingredientCount).getText().isEmpty() && !ingredientQtyInputs.get(ingredientCount).getText().isEmpty()) {
                addSingleIngredientClick();
            }
            else {
                ingredientNameInputs.get(ingredientCount).clear();
                ingredientQtyInputs.get(ingredientCount).clear();
                ingredientQtyInputs.get(ingredientCount).setDisable(true);
                ingredientQtyInputs.get(ingredientCount).setDisable(true);
            }
        }
        ingredientSubmit.setDisable(true);
        allIngredientsSubmit.setDisable(true);
        System.out.println(ingredientList.toString());
        System.out.println(ingredientQtyList.toString());
    }

    private void addSingleIngredientClick() {
        String ingredientName = ingredientNameInputs.get(ingredientCount).getText().trim();
        String ingredientQty = ingredientQtyInputs.get(ingredientCount).getText().trim();

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
            System.out.println("Ingredient Name: " + ingredientName + " Ingredient qty: " + qty);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must be a valid integer.");
            alert.showAndWait();
            ingredientQtyInputs.get(ingredientCount).clear();
            return;
        }

        ingredientNameInputs.get(ingredientCount).setDisable(true);
        ingredientQtyInputs.get(ingredientCount).setDisable(true);
        ingredientList.add(new Ingredient(ingredientName));
        ingredientQtyList.add(new BigDecimal(ingredientQty));
        ingredientCount++;

        if (ingredientCount > 0 && ingredientCount < 6) {
            openIngredientField();
            allIngredientsSubmit.setDisable(false);
        }
        else {
            ingredientSubmit.setDisable(true);
        }
    }


    @FXML
    private void addRecipeNameClick() {
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
            ingredientSubmit.setDisable(false);
            openIngredientField();
        }
    }

    private void openIngredientField() {
        ingredientNameInputs.get(ingredientCount).setDisable(false);
        ingredientQtyInputs.get(ingredientCount).setDisable(false);
    }

}
