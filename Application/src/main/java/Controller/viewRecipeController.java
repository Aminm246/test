package Controller;

import Model.*;
import Repository.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class viewRecipeController {

    private FXMLLoader createLoader;
    private FXMLLoader listLoader;
    private FXMLLoader updateLoader;

    @FXML
    private Text recipeNameTag;

    @FXML
    private Label recipeNameLabel;

    @FXML
    private Button recipeUpdate;

    @FXML
    private Button recipeDelete;

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

    private RecipeRepository recipeRepository;
    private RecipeIngRepository recipeIngRepository;
    private IngredientsRepository ingredientsRepository;
    private InstructionsRepository instructionsRepository;
    private TagRepository tagRepository;
    private RecipeTagRepository recipeTagRepository;


    @FXML
    public void initialize() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeIngRepository = new RecipeIngRepository(databaseConnection);
        ingredientsRepository = new IngredientsRepository(databaseConnection);
        instructionsRepository = new InstructionsRepository(databaseConnection);
        tagRepository = new TagRepository(databaseConnection);
        recipeTagRepository = new RecipeTagRepository(databaseConnection);

    }

    public void setRecipe(int recipeID) throws SQLException {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        recipeNameLabel.setText(recipe.getRecipeName());
        List<String> ingredients = new ArrayList<>();

        List<String> instructions = new ArrayList<>();
        for (InstructionStep instructionStep : instructionsRepository.getInstructionsByRecipeId(recipeID)) {
            String instruction = "Instruction " + instructionStep.getStepNum() + ": " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }

        List<String> tags = new ArrayList<>();
        for (RecipeTag recipeTag : recipeTagRepository.getTagsByRecipeId(recipeID)) {
            int tagID = recipeTag.getTagID();
            Tag tag = tagRepository.getTagById(tagID);
            tags.add(tag.getTagName());
        }

        instructionsTextArea.setText(String.join("\n", instructions));
        tagsLabel.setText(String.join(", ", tags));
        descriptionLabel.setText(recipe.getDescription());

        for (RecipeIngredient recipeIngredient : recipeIngRepository.getIngredientsByRecipeId(recipeID)) {
            Ingredient ingredient = ingredientsRepository.getIngredientsById(recipeIngredient.getIngredientID());
            ingredients.add(ingredient.getIngredientName() + " " + recipeIngredient.getQuantity() + " " + recipeIngredient.getMeasurementUnit());
        }
        ingredientsTextArea.setText(String.join("\n", ingredients));

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
        recipeUpdate.setOnAction(event -> {
            try {
                recipeUpdatePage(recipe.getRecipeID());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        recipeDelete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Are you sure you want to delete the recipe?");
            alert.setContentText("Recipe will be deleted from the application.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    recipeRepository.deleteRecipe(recipeID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Recipe has been deleted");
                try {
                    switchToRecipeList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("Deletion cancelled");
            }
        });
    }

    private void recipeUpdatePage(int recipeID) throws SQLException {
        recipeNameLabel.getScene().setRoot(updateLoader.getRoot());
        updateRecipeController updateController = updateLoader.getController();
        updateController.setRecipe(recipeID);
    }

    @FXML
    private void switchToCreateRecipe(){
        recipeNameLabel.getScene().setRoot(createLoader.getRoot());
    }

    public void switchToRecipeList() throws SQLException {
        recipeNameLabel.getScene().setRoot(listLoader.getRoot());
        recipeListController listController = listLoader.getController();
        listController.populateList();
    }

    public void setCreateLoader(FXMLLoader createLoader) {
        this.createLoader = createLoader;
    }

    public void setListLoader(FXMLLoader listLoader) {
        this.listLoader = listLoader;
    }

    public void setUpdateLoader(FXMLLoader updateLoader){
        this.updateLoader = updateLoader;
    }
}
