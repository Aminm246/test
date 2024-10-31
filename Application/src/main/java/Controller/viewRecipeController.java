package Controller;

import Model.*;
import Repository.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.SQLException;
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
    private RecipeIngManager recipeIngManager;
    private IngredientsManager ingredientsManager;
    private InstructionsManager instructionsManager;
    private TagManager tagManager;
    private RecipeTagManager recipeTagManager;

    private DatabaseConnection databaseConnection;
    private RecipeRepository recipeRepository;
    private RecipeIngRepository recipeIngRepository;
    private IngredientsRepository ingredientsRepository;
    private InstructionsRepository instructionsRepository;
    private TagRepository tagRepository;
    private RecipeTagRepository recipeTagRepository;

    @FXML
    public void initialize() {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeIngRepository = new RecipeIngRepository(databaseConnection);
        ingredientsRepository = new IngredientsRepository(databaseConnection);
        instructionsRepository = new InstructionsRepository(databaseConnection);
        tagRepository = new TagRepository(databaseConnection);
        recipeTagRepository = new RecipeTagRepository(databaseConnection);


        recipeManager = new RecipeManager(recipeRepository);
        recipeIngManager = new RecipeIngManager(recipeIngRepository);
        ingredientsManager = new IngredientsManager(ingredientsRepository);
        instructionsManager = new InstructionsManager(instructionsRepository);
        tagManager = new TagManager(tagRepository);
        recipeTagManager = new RecipeTagManager(recipeTagRepository);
    }



    public void setRecipe(int recipeID) throws SQLException {
        Recipe recipe = recipeManager.getRecipe(recipeID);
        recipeNameLabel.setText(recipe.getRecipeName());
        List<String> ingredients = new ArrayList<>();

        List<String> instructions = new ArrayList<>();
        for (InstructionStep instructionStep : instructionsManager.getInstructionsByRecipeId(recipeID)) {
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
        for (RecipeIngredient recipeIngredient : recipeIngManager.getIngredientsByRecipeId(recipeID)) {
            Ingredient ingredient = ingredientsManager.getIngredientById(recipeIngredient.getIngredientID());
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