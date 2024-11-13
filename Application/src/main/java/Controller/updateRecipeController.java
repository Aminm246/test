package Controller;

import Model.*;
import Repository.*;
import javafx.collections.ObservableArray;
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
    private TagManager tagManager;
    private InstructionsManager instructionsManager;
    private RecipeIngManager recipeIngManager;
    private IngredientsManager ingredientsManager;
    private RecipeTagManager recipeTagManager;


    @FXML TextField imagePathInput,recipeNameInput, durationInput,servingSizeInput, ingredientNameInput, ingredientQtyInput, tagInput;
    @FXML TextArea instructionFxList,tagFxList,recipeDescriptionInput,ingredientFxList, instructionInput;

    @FXML ComboBox ingredientNumPicker, instructionNumPicker, tagNumPicker, measurementPicker;
    @FXML Button ingredientSubmit, instructionSubmit, tagRemove,tagAdd, recipeSubmit, cancelButton;

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
        tagManager = new TagManager(new TagRepository(databaseConnection));
        recipeTagManager =new RecipeTagManager(new RecipeTagRepository(databaseConnection));

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
        tagAdd.setOnAction(event -> {
            try {
                addTag();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tagRemove.setOnAction(event -> removeTag());
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
            setIngredients();
            System.out.println(ingredientName + " : " + ingredientQuantity + " : " + ingredientID);
        }
    }
    private void saveInstruction() throws SQLException {
        if(!instructionInput.getText().isEmpty()) {
            String instruction = instructionInput.getText();
            int instructionID = Integer.parseInt(instructionNumPicker.getSelectionModel().getSelectedItem().toString());
            int i = 0;

            for(String instructionParse: instructions){
                String parseID = instructionParse.substring(0,instructionParse.indexOf(":"));
                if(Integer.parseInt(parseID) == instructionID){
                    instructions.set(i,(instructionID + ": " + instruction));
                }
                i++;
            }

            setInstructions();
            System.out.println(instruction + " : " + instructionID);
        }
    }
    private void removeTag(){
        if(!tagInput.getText().isEmpty()){
            int tagID = Integer.parseInt(tagNumPicker.getSelectionModel().getSelectedItem().toString());
            int i = 0;

            for(String tagParse: tags){
                if(tagParse.contains(Integer.toString(tagID))){
                    tags.remove(i);
                    break;
                }
                i++;
            }
            setTags();
        }
    }

    private void addTag() throws SQLException {
        if(!tagInput.getText().isEmpty()){
            int tagid = tagManager.addTag(tagInput.getText());
            Tag tag = tagManager.getTagById(tagid);
            String string = "(" + tag.getTagId() + ") " + tag.getTagName();
            tags.add(string);
            tagNumPicker.getItems().add(tag.getTagId());
            setTags();
        }
    }
    private void saveRecipe() throws SQLException {
        RecipeManager recipeManager = new RecipeManager(recipeRepository,databaseConnection);
        recipeManager.updateRecipe(recipeID,recipeNameInput.getText(),1,Integer.parseInt(servingSizeInput.getText())
                ,imagePathInput.getText(),recipeDescriptionInput.getText(),Integer.parseInt(durationInput.getText()),ingredients,instructions,tags);

    }

    public void setRecipe(int recipeID) throws SQLException {
        tagNumPicker.getItems().clear();
        ingredientNumPicker.getItems().clear();
        instructionNumPicker.getItems().clear();
        Recipe recipe = recipeRepository.getRecipeById(recipeID);
        this.recipeID = recipeID;
        recipeNameInput.setText(recipe.getRecipeName());
        instructions = new ArrayList<>();
        ingredients = new ArrayList<>();
        tags = new ArrayList<>();

        for (InstructionStep instructionStep : instructionsManager.getInstructionsByRecipeId(recipeID)) {
            instructionNumPicker.getItems().add(instructionStep.getInstructionStepID());
            String instruction = instructionStep.getInstructionStepID() + ": " + instructionStep.getStepDescription();
            instructions.add(instruction);
        }

        for (RecipeTag recipeTag : recipeTagManager.getTagsByRecipeId(recipeID)) {
            int tagID = recipeTag.getTagID();
            Tag tag = tagManager.getTagById(tagID);
            tagNumPicker.getItems().add(tag.getTagId());
            tags.add("(" + tag.getTagId() + ") " + tag.getTagName());
        }

        for (RecipeIngredient ingredient : recipeIngManager.getIngredientsByRecipeId(recipeID)) {
            ingredientNumPicker.getItems().add(ingredient.getRecipeIngredientID());
            ingredients.add("(" + ingredient.getRecipeIngredientID() + ") [" + ingredientsManager.getIngredientById(ingredient.getIngredientID()).getIngredientName() +
                    "] {" + ingredient.getQuantity() + "} " + ingredient.getMeasurementUnit());
        }

        recipeDescriptionInput.setText(recipe.getDescription());
        setTags();
        setIngredients();
        setInstructions();
        durationInput.setText(Integer.toString(recipe.getDuration()));
        servingSizeInput.setText(Integer.toString(recipe.getServingSize()));
    }

    private void setIngredients() {
        ingredientFxList.setText(String.join("\n", ingredients));
    }
    private void setInstructions(){
        System.out.println(instructions.toString());
        instructionFxList.setText(String.join("\n", instructions));
    }
    private void setTags(){
        tagFxList.setText(String.join(", ", tags));
    }

    private void setInstruction(int id) throws SQLException {
        instructionInput.setText(instructionsManager.getInstruction(id).getStepDescription());
    }
    private void setTag(int tagID) throws SQLException {
        tagInput.setText(tagManager.getTagById(tagID).getTagName());
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
