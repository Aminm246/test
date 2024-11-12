package Controller;

import Model.*;
import Repository.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class updateRecipeController  {
    int recipeID;
    private DatabaseConnection databaseConnection;
    private RecipeRepository recipeRepository;
    private TagRepository tagRepository;
    private InstructionsManager instructionsManager;
    private RecipeIngManager recipeIngManager;
    private IngredientsManager ingredientsManager;

    private RecipeTagRepository recipeTagRepository;


    @FXML TextField imagePathInput,recipeNameInput, durationInput,servingSizeInput, ingredientNameInput, ingredientQtyInput, tagInput;
    @FXML TextArea instructionFxList,tagFxList,recipeDescriptionInput,ingredientFxList, instructionInput;

    @FXML ComboBox ingredientNumPicker, instructionNumPicker, tagNumPicker, measurementPicker;
    @FXML Button ingredientSubmit, instructionSubmit, tagsSubmit, recipeSubmit, cancelButton;

    List<String> ingredients;
    List<String> instructions;
    List<String> tags;
    @FXML
    void initialize() {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeIngManager = new RecipeIngManager(new RecipeIngRepository(databaseConnection));
        instructionsManager = new InstructionsManager(new InstructionsRepository(databaseConnection));
        ingredientsManager = new IngredientsManager(new IngredientsRepository(databaseConnection));
        tagRepository = new TagRepository(databaseConnection);
        recipeTagRepository = new RecipeTagRepository(databaseConnection);
        ingredients = new ArrayList<>();

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
                loadIngredient(Integer.parseInt(newValue.toString()));
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


        ingredientSubmit.setOnAction(event -> {
            try {
                saveIngredient();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        instructionSubmit.setOnAction(event -> {
            try {
                saveInstruction();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tagsSubmit.setOnAction(event -> saveTag());
        recipeSubmit.setOnAction(event -> {
            try {
                saveRecipe();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void saveIngredient() throws SQLException {
        if(!ingredientNameInput.getText().isEmpty()){
            String ingredientName = ingredientNameInput.getText();
            Double ingredientQuantity = Double.parseDouble(ingredientQtyInput.getText());
            String measurementUnit = measurementPicker.getSelectionModel().getSelectedItem().toString();
            int ingredientID = Integer.parseInt(ingredientNumPicker.getSelectionModel().getSelectedItem().toString());
            int i =0;
            for(String ingredient : ingredients){
                if(ingredient.contains("(" + ingredientID + ")")){
                    ingredients.set(i, ("(" + ingredientID + ") [" + ingredientName + "] {" +
                            ingredientQuantity + "} " + measurementUnit));
                }
                i++;
            }
            recipeIngManager.updateIngredient(ingredientID,measurementUnit, BigDecimal.valueOf(ingredientQuantity));
            setIngredients();
            System.out.println(ingredientName + " : " + ingredientQuantity + " : " + ingredientID);
        }
    }
    private void saveInstruction() throws SQLException {
        if(!instructionInput.getText().isEmpty()) {
            String instruction = instructionInput.getText();
            int instructionID = Integer.parseInt(instructionNumPicker.getSelectionModel().getSelectedItem().toString());
            instructionsManager.updateInstruction(instructionID,instruction);
            setInstructions();
            System.out.println(instruction + " : " + instructionID);
        }
    }
    private void saveTag(){
        if(!tagInput.getText().isEmpty()){
            String tag = tagInput.getText();
            int tagID = Integer.parseInt(tagNumPicker.getSelectionModel().getSelectedItem().toString());

            System.out.println(tag + " : " + tagID);
        }
    }
    private void saveRecipe() throws SQLException {
        List<TextInputControl> x = Arrays.asList(instructionFxList,tagFxList,recipeDescriptionInput,
                ingredientFxList,recipeNameInput, durationInput,servingSizeInput,imagePathInput);
        for(TextInputControl field: x){
            if(field.getStyle().contains("-fx-control-inner-background: red")){
                field.setStyle("-fx-control-inner-background: green");
                System.out.println(field.getText() + " " + field.getId());
            }
        }

        RecipeManager recipeManager = new RecipeManager(recipeRepository,databaseConnection);
        recipeManager.updateRecipe(recipeID,recipeNameInput.getText(),1,Integer.parseInt(servingSizeInput.getText())
                ,imagePathInput.getText(),recipeDescriptionInput.getText(),Integer.parseInt(durationInput.getText()),ingredients,instructions,tags);



    }

    public void setRecipe(int recipeID) throws SQLException {
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        this.recipeID = recipeID;
        recipeNameInput.setText(recipe.getRecipeName());
        instructions = new ArrayList<>();

        for (InstructionStep instructionStep : instructionsManager.getInstructionsByRecipeId(recipeID)) {
            instructionNumPicker.getItems().add(instructionStep.getInstructionStepID());
            String instruction = instructionStep.getInstructionStepID() + ": " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }

        tags = new ArrayList<>();
        for (RecipeTag recipeTag : recipeTagRepository.getTagsByRecipeId(recipeID)) {
            int tagID = recipeTag.getTagID();
            Tag tag = tagRepository.getTagById(tagID);
            tagNumPicker.getItems().add(tag.getTagId());
            tags.add("(" + tag.getTagId() + ") " + tag.getTagName());
        }


        tagFxList.setText(String.join(", ", tags));
        recipeDescriptionInput.setText(recipe.getDescription());


        for (RecipeIngredient ingredient : recipeIngManager.getIngredientsByRecipeId(recipeID)) {
            ingredientNumPicker.getItems().add(ingredient.getRecipeIngredientID());
            ingredients.add("(" + ingredient.getRecipeIngredientID() + ") [" + ingredientsManager.getIngredientById(ingredient.getIngredientID()).getIngredientName() +
                    "] {" + ingredient.getQuantity() + "} " + ingredient.getMeasurementUnit());
        }
        setIngredients();
        setInstructions();

        durationInput.setText(Integer.toString(recipe.getDuration()));
        servingSizeInput.setText(Integer.toString(recipe.getServingSize()));


    }

    private void setIngredients() {
        ingredientFxList.setText(String.join("\n", ingredients));
    }
    private void setInstructions(){
        instructionFxList.setText(String.join("\n", instructions));
    }

    private void setInstruction(int id) throws SQLException {
        instructionInput.setText(instructionsManager.getInstruction(id).getStepDescription());
    }
    private void setTag(int tagID) throws SQLException {
        tagInput.setText(tagRepository.getTagById(tagID).getTagName());
    }
    private void loadIngredient(int recipeIngID) throws SQLException {
            RecipeIngredient ingredient = recipeIngManager.getIngredientById(recipeIngID);
            ingredientNameInput.setText(ingredientsManager.getIngredientById(ingredient.getIngredientID()).getIngredientName());
            ingredientQtyInput.setText(ingredient.getQuantity().toString());
            measurementPicker.getItems().clear();
            measurementPicker.getItems().add(ingredient.getMeasurementUnit());
            measurementPicker.getSelectionModel().selectFirst();
    }
}
