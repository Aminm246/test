package Controller;

import Model.*;
import Repository.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.io.File;

public class viewRecipeController {

    private FXMLLoader updateLoader, menuLoader;

    @FXML
    private Label recipeNameLabel, tagsLabel, descriptionLabel, servingsLabel, durationLabel;

    @FXML
    private Button recipeUpdate, recipeDelete;

    @FXML
    private TextArea ingredientsTextArea, instructionsTextArea;

    @FXML
    private ImageView recipeImageView;

    private RecipeManager recipeManager;
    private InstructionsManager instructionsManager;
    private RecipeTagManager recipeTagManager;
    private RecipeIngManager recipeIngManager;

    private int recipeID;

    @FXML
    public void initialize() {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        recipeTagManager = new RecipeTagManager(databaseConnection);
        recipeIngManager = new RecipeIngManager(databaseConnection);
        instructionsManager = new InstructionsManager(databaseConnection);
        recipeManager = new RecipeManager(databaseConnection, recipeTagManager, recipeIngManager,instructionsManager);

    }
    public int getRecipeID(){
        return recipeID;
    }
    public void setRecipe(int recipeID) {
        this.recipeID = recipeID;
        Recipe recipe = recipeManager.getRecipe(recipeID);
        recipeNameLabel.setText(recipe.getRecipeName());
        servingsLabel.setText(String.valueOf(recipe.getServingSize()));
        durationLabel.setText(String.valueOf(recipe.getDuration()));

        List<String> ingredients = new ArrayList<>();

        List<String> instructions = new ArrayList<>();
        for (InstructionStep instructionStep : recipe.getInstructionSteps()) {
            String instruction = "Instruction " + instructionStep.getStepNum() + ": " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }

        List<String> tags = new ArrayList<>();
        for (RecipeTag recipeTag : recipe.getTags()) {
            tags.add(recipeTag.getTag().getTagName());
        }

        instructionsTextArea.setText(String.join("\n", instructions));
        tagsLabel.setText(String.join(", ", tags));
        descriptionLabel.setText(recipe.getDescription());

        for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
            ingredients.add(recipeIngredient.getIngredient().getIngredientName() + " " + recipeIngredient.getQuantity() + " " + recipeIngredient.getMeasurementUnit());
        }

        ingredientsTextArea.setText(String.join("\n", ingredients));

        if (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) {
            try {
                File imageFile = new File("src/main/resources" + recipe.getImagePath());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    System.err.println("Image exists");
                    recipeImageView.setImage(image);
                } else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
                    System.err.println("Image does not exist 1");
                    recipeImageView.setImage(image);
                }
            } catch (Exception e) {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
                System.err.println("Image does not exist 2");
                recipeImageView.setImage(image);
            }
        } else {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
            System.err.println("Image does not exist 3");
            recipeImageView.setImage(image);
        }
        recipeUpdate.setOnAction(event -> recipeUpdatePage(recipe.getRecipeID()));

        recipeDelete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Are you sure you want to delete the recipe?");
            alert.setContentText("Recipe will be deleted from the application.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (RecipeIngredient ingredient : recipe.getRecipeIngredients()) {
                    recipeIngManager.removeIngredient(ingredient.getRecipeIngredientID());
                }
                for (RecipeTag tag : recipe.getTags()) {
                    recipeTagManager.removeTag(tag.getRecipeTagID());
                }
                for (InstructionStep instructionStep : recipe.getInstructions()) {
                    instructionsManager.removeInstruction(instructionStep.getInstructionStepID());
                }
                recipeManager.removeRecipe(recipeID);
                MenuBarController menuController = menuLoader.getController();
                menuController.switchToRecipeList(recipeNameLabel);
            }
        });
    }

    private void recipeUpdatePage(int recipeID) {
        recipeNameLabel.getScene().setRoot(updateLoader.getRoot());
        updateRecipeController updateController = updateLoader.getController();
        updateController.setRecipe(recipeID);
    }

    public void setUpdateLoader(FXMLLoader updateLoader){
        this.updateLoader = updateLoader;
    }

    public void setMenuLoader(FXMLLoader menuLoader) {
        this.menuLoader = menuLoader;
    }
}
