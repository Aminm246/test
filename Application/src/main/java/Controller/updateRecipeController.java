package Controller;

import Model.*;
import Repository.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class updateRecipeController  {
    int recipeID;
    private DatabaseConnection databaseConnection;
    private RecipeRepository recipeRepository;
    private RecipeIngRepository recipeIngRepository;
    private IngredientsRepository ingredientsRepository;
    private InstructionsRepository instructionsRepository;
    private TagRepository tagRepository;

    private RecipeTagRepository recipeTagRepository;


    @FXML TextField imagePathInput,recipeNameInput, durationInput,servingSizeInput, ingredientNameInput, ingredientQtyInput, tagInput;
    @FXML TextArea instructionFxList,tagFxList,recipeDescriptionInput,ingredientFxList, instructionInput;

    @FXML ComboBox ingredientNumPicker, instructionNumPicker, tagNumPicker;
    @FXML Button ingredientSubmit, instructionSubmit, tagsSubmit, recipeSubmit, cancelButton;

    @FXML
    void initialize() {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeIngRepository = new RecipeIngRepository(databaseConnection);
        ingredientsRepository = new IngredientsRepository(databaseConnection);
        instructionsRepository = new InstructionsRepository(databaseConnection);
        tagRepository = new TagRepository(databaseConnection);
        recipeTagRepository = new RecipeTagRepository(databaseConnection);

        instructionNumPicker.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                setInstruction(Integer.parseInt(newValue.toString()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(newValue);
        });

        tagNumPicker.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                setTag(Integer.parseInt(newValue.toString()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ingredientNumPicker.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                setIngredient(Integer.parseInt(newValue.toString()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        List<TextInputControl> x = Arrays.asList(instructionFxList,tagFxList,recipeDescriptionInput,
                ingredientFxList,recipeNameInput, durationInput,servingSizeInput,imagePathInput);
        for(TextInputControl field: x){
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!oldValue.equals(newValue) && !oldValue.isEmpty()){
                    field.setStyle("-fx-control-inner-background: red");
                }
            });
        }


        ingredientSubmit.setOnAction(event -> saveIngredient());
        instructionSubmit.setOnAction(event -> saveInstruction());
        tagsSubmit.setOnAction(event -> saveTag());
        recipeSubmit.setOnAction(event -> saveRecipe());

    }

    private void saveIngredient(){

    }

    private void saveInstruction(){

    }

    private void saveTag(){
        //recipeRepository.updateRecipe();
    }
    private void saveRecipe(){
        List<TextInputControl> x = Arrays.asList(instructionFxList,tagFxList,recipeDescriptionInput,
                ingredientFxList,recipeNameInput, durationInput,servingSizeInput,imagePathInput);
        for(TextInputControl field: x){
            if(field.getStyle().contains("-fx-control-inner-background: red")){
                System.out.println(field.getText());
            }
        }

    }

    public void setRecipe(int recipeID) throws SQLException {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        this.recipeID = recipeID;
        recipeNameInput.setText(recipe.getRecipeName());
        List<String> ingredients = new ArrayList<>();
        List<String> instructions = new ArrayList<>();

        for (InstructionStep instructionStep : instructionsRepository.getInstructionsByRecipeId(recipeID)) {
            instructionNumPicker.getItems().add(instructionStep.getStepNum());
            String instruction = "Instruction " + instructionStep.getStepNum() + ": " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }

        List<String> tags = new ArrayList<>();
        for (RecipeTag recipeTag : recipeTagRepository.getTagsByRecipeId(recipeID)) {
            int tagID = recipeTag.getTagID();
            Tag tag = tagRepository.getTagById(tagID);
            tagNumPicker.getItems().add(tag.getTagId());
            tags.add("(" + tag.getTagId() + ") " + tag.getTagName());
        }

        instructionFxList.setText(String.join("\n", instructions));
        tagFxList.setText(String.join(", ", tags));
        recipeDescriptionInput.setText(recipe.getDescription());
        for (RecipeIngredient recipeIngredient : recipeIngRepository.getIngredientsByRecipeId(recipeID)) {
            Ingredient ingredient = ingredientsRepository.getIngredientsById(recipeIngredient.getIngredientID());
            ingredientNumPicker.getItems().add(ingredient.getIngredientId());
            ingredients.add("(" + ingredient.getIngredientId() + ") " + ingredient.getIngredientName() + " " + recipeIngredient.getQuantity() + " " + recipeIngredient.getMeasurementUnit());
        }
        ingredientFxList.setText(String.join("\n", ingredients));
        durationInput.setText(Integer.toString(recipe.getDuration()));
        servingSizeInput.setText(Integer.toString(recipe.getServingSize()));


    }

    private void setInstruction(int index) throws SQLException {
        instructionInput.setText(instructionsRepository.getInstructionsByRecipeId(recipeID).get(index - 1).getStepDescription());
    }

    private void setTag(int tagID) throws SQLException {
        tagInput.setText(tagRepository.getTagById(tagID).getTagName());
    }

    private void setIngredient(int ingredientID) throws SQLException {
        ingredientNameInput.setText(ingredientsRepository.getIngredientsById(ingredientID).getIngredientName());

        List<RecipeIngredient> x = recipeIngRepository.getIngredientsByRecipeId(recipeID);
        for (RecipeIngredient ingredient: x){
            if(ingredient.getIngredientID() == ingredientID){
                ingredientQtyInput.setText(ingredient.getQuantity().toString());
            }
        }

    }
}
