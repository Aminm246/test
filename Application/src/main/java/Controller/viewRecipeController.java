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
import java.io.File;

public class viewRecipeController {

    private FXMLLoader createLoader;
    private FXMLLoader listLoader;
    private FXMLLoader updateLoader;
    private FXMLLoader menuLoader;
    private FXMLLoader searchLoader;

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

    private RecipeManager recipeManager;
    private RecipeTagManager recipeTagManager;
    private RecipeIngManager recipeIngManager;


    @FXML
    public void initialize() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeIngRepository = new RecipeIngRepository(databaseConnection);
        ingredientsRepository = new IngredientsRepository(databaseConnection);
        instructionsRepository = new InstructionsRepository(databaseConnection);
        tagRepository = new TagRepository(databaseConnection);
        recipeTagRepository = new RecipeTagRepository(databaseConnection);
        recipeTagManager = new RecipeTagManager(recipeTagRepository, tagRepository);
        recipeIngManager = new RecipeIngManager(recipeIngRepository, ingredientsRepository);
        recipeManager = new RecipeManager(recipeRepository, recipeTagManager, recipeIngManager, instructionsRepository);

    }


    public void setRecipe(int recipeID) {
        Recipe recipe = recipeManager.getRecipe(recipeID);
        recipeNameLabel.setText(recipe.getRecipeName());
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
                    recipeImageView.setImage(image);
                } else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
                    recipeImageView.setImage(image);
                }
            } catch (Exception e) {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
                recipeImageView.setImage(image);
            }
        } else {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg")));
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
                try {
                    for (RecipeIngredient ingredient : recipe.getRecipeIngredients()) {
                        recipeIngRepository.deleteIngredient(ingredient.getRecipeIngredientID());
                    }
                    for (RecipeTag tag : recipe.getTags()) {
                        recipeTagRepository.deleteTag(tag.getRecipeTagID());
                    }
                    for (InstructionStep instructionStep : recipe.getInstructions()) {
                        instructionsRepository.deleteInstruction(instructionStep.getInstructionStepID());
                    }
                    recipeRepository.deleteRecipe(recipeID);
                    MenuBarController menuController = menuLoader.getController();
                    menuController.switchToRecipeList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    @FXML
    public void switchToRecipeList(){
        recipeNameLabel.getScene().setRoot(listLoader.getRoot());
        recipeListController listController = listLoader.getController();
        listController.populateList();
    }

    private void recipeUpdatePage(int recipeID) {
        recipeNameLabel.getScene().setRoot(updateLoader.getRoot());
        updateRecipeController updateController = updateLoader.getController();
        updateController.setRecipe(recipeID);
    }

    @FXML
    private void switchToCreateRecipe(){
        recipeNameLabel.getScene().setRoot(createLoader.getRoot());
    }

    @FXML
    private void switchToSearch(){
        recipeNameLabel.getScene().setRoot(searchLoader.getRoot());
    }
    public void setUpdateLoader(FXMLLoader updateLoader){
        this.updateLoader = updateLoader;
    }
    public void setCreateLoader(FXMLLoader createLoader) {
        this.createLoader = createLoader;
    }

    public void setListLoader(FXMLLoader listLoader) {
        this.listLoader = listLoader;
    }

    public void setSearchLoader(FXMLLoader searchLoader){
        this.searchLoader = searchLoader;
    }

    public void setMenuLoader(FXMLLoader menuLoader) {
        this.menuLoader = menuLoader;
    }
}
