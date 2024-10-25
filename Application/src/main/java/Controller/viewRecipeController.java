package Controller;

import Model.RecipeIngredient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import Model.InstructionStep;
import Model.Recipe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class viewRecipeController {

    private FXMLLoader createLoader;
    private FXMLLoader listLoader;

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
    private RecipeManager recipeManager;

    public void setRecipe(int recipeID) {
        Recipe recipe = recipeManager.getRecipe(recipeID);

        recipeNameLabel.setText(recipe.getRecipeName());
        List<String> ingredients = new ArrayList<>();

        for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
            ingredients.add(recipeIngredient.getIngredient().getIngredientName() + " " + recipeIngredient.getQuantity() + " " + recipeIngredient.getMeasurementUnit());
        }


        List<String> instructions = new ArrayList<>();
        for (InstructionStep instructionStep : recipe.getInstructions()) {
            String instruction = "Instruction " + instructionStep.getStepNum() + ": " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }
        ingredientsTextArea.setText(String.join("\n", ingredients));
        instructionsTextArea.setText(String.join("\n", instructions));
        tagsLabel.setText(String.join(", ", recipe.getTags()));
        descriptionLabel.setText(recipe.getDescription());


        if (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) {
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(recipe.getImagePath())));
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
        recipeNameLabel.getScene().setRoot(createLoader.getRoot());
    }

    public void switchToRecipeList() {
        recipeNameLabel.getScene().setRoot(listLoader.getRoot());
        recipeListController listController = listLoader.getController();
        listController.populateList();
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    public void setCreateLoader(FXMLLoader createLoader) {
        this.createLoader = createLoader;
    }

    public void setListLoader(FXMLLoader listLoader) {
        this.listLoader = listLoader;
    }


}