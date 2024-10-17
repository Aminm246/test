package edu.metrostate;

import ingredient.model.Ingredient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipe.model.InstructionStep;
import recipe.model.Recipe;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class viewRecipeController {

    @FXML
    private Text recipeNameTag;

    @FXML
    private Label recipeNameLabel;

    @FXML
    private TextArea ingredientsTextArea;

    @FXML
    private Label tagsLabel;

    @FXML
    private TextArea instructionsTextArea;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView recipeImageView;

    public void setRecipe(Recipe recipe) {

        recipeNameLabel.setText(recipe.getRecipeName());
        List<String> ingredients = new ArrayList<>();
        List<BigDecimal> ingredientQty = recipe.getIngredientQtyList();
        List<Ingredient> ingredientList = recipe.getIngredientList();

        for (int i = 0; i < ingredientList.size();i++){
            ingredients.add(ingredientList.get(i).getIngredientName().concat(ingredientQty.get(i).toString()));
        }

        List<String> instructions = new ArrayList<>();
        for (InstructionStep instructionStep : recipe.getInstructions()) {
            String instruction = instructionStep.getStepNum() + " " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }
        ingredientsTextArea.setText(String.join("\n", ingredients));
        instructionsTextArea.setText(String.join("\n", instructions));
        tagsLabel.setText(String.join(", ", recipe.getTags()));
        descriptionLabel.setText(recipe.getDescription());


        if (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) {
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + recipe.getImagePath())));
                recipeImageView.setImage(image);
            } catch (NullPointerException e) {
                System.out.println("Image not found: " + recipe.getImagePath());
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
                recipeImageView.setImage(image);
            }
        } else {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
            recipeImageView.setImage(image);
        }
    }

    @FXML
    private void switchToCreateRecipe(){
        try {
            Stage stage = (Stage) recipeNameLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipeView.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}