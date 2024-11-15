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
    @FXML Button ingredientSubmit, ingredientAdd, ingredientRemove, instructionSubmit, instructionAdd, instructionRemove,
            tagRemove,tagAdd, recipeSubmit, cancelButton;

    List<String> ingredients;
    List<String> instructions;
    List<String> tags;
    @FXML
    void initialize() {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeIngManager = new RecipeIngManager(new RecipeIngRepository(databaseConnection),new IngredientsRepository(databaseConnection));
        instructionsManager = new InstructionsManager(new InstructionsRepository(databaseConnection));
        ingredientsManager = new IngredientsManager(new IngredientsRepository(databaseConnection));
        tagManager = new TagManager(new TagRepository(databaseConnection));
        recipeTagManager =new RecipeTagManager(new RecipeTagRepository(databaseConnection),new TagRepository(databaseConnection));

        measurementPicker.getItems().addAll(
                "grams",
                "cups",
                "tablespoons",
                "teaspoons",
                "ounces",
                "pounds",
                "milliliters",
                "liters",
                "pieces",
                "pinch"
        );

        instructionNumPicker.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null) {
                setInstruction(Integer.parseInt(newValue.toString()));
            }
            System.out.println(newValue);
        });

        tagNumPicker.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null){
                setTag(Integer.parseInt(newValue.toString()));
            }
        });

        ingredientNumPicker.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null){
                loadIngredient(Integer.parseInt(newValue.toString()));
            }
        });

        ingredientSubmit.setOnAction(event -> saveIngredient());
        instructionSubmit.setOnAction(event -> saveInstruction());
        tagAdd.setOnAction(event -> addTag());
        tagRemove.setOnAction(event -> removeTag());
        recipeSubmit.setOnAction(event -> saveRecipe());
        instructionAdd.setOnAction(event -> addInstruction());
        ingredientAdd.setOnAction(event -> addIngredient());
        ingredientRemove.setOnAction(event -> removeIngredient());
        instructionRemove.setOnAction(event -> removeInstruction());
    }

    private void removeInstruction() {
        //Prompt if sure.
        int instructionID = (int) instructionNumPicker.getSelectionModel().getSelectedItem();
        System.out.println("Removing instruction with id: " + instructionID);
        instructionsManager.removeInstruction(instructionID);


        int i = 0;
        for(String instruction: instructions){
            if(instruction.substring(0, instruction.indexOf(":")).equals(Integer.toString(instructionID))){
                System.out.println("REMOVING " + instruction);
                instructions.remove(i);
                break;
            }
            i++;
        }
        instructionNumPicker.getItems().remove(instructionNumPicker.getSelectionModel().getSelectedItem());
        instructionNumPicker.getSelectionModel().clearSelection();
        instructionInput.clear();
        //instructionNumPicker.getSelectionModel().clearSelection();

        setInstructions();
    }

    private void removeIngredient() {
        //Prompt if sure
        int ingredientID = (int) ingredientNumPicker.getSelectionModel().getSelectedItem();
        recipeIngManager.removeIngredient(ingredientID);
        int i =0;

        for(String ingredient: ingredients){
            if(ingredient.contains("(" + ingredientID + ")")){
                ingredients.remove(i);
                break;
            }
            i++;
        }
        ingredientNumPicker.getItems().remove(ingredientNumPicker.getSelectionModel().getSelectedItem());
        ingredientNumPicker.getSelectionModel().clearSelection();
        ingredientNameInput.clear();
        ingredientQtyInput.clear();
        measurementPicker.getSelectionModel().clearSelection();
        //ingredientNumPicker.getSelectionModel().clearSelection();
        setIngredients();
    }


    private void saveIngredient() {
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
    private void saveInstruction()  {
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

    private void addIngredient(){
        if(ingredientNameInput.getText().isEmpty() || ingredientQtyInput.getText().isEmpty() || measurementPicker.getSelectionModel().isEmpty()){
            //Please fill out ingredient before trying to add
        }
        else {
            int ingredientID = ingredientsManager.addIngredient(ingredientNameInput.getText());
            String measurementType = measurementPicker.getSelectionModel().getSelectedItem().toString();
            BigDecimal qty = BigDecimal.valueOf(Double.parseDouble(ingredientQtyInput.getText()));
            int recIngID = recipeIngManager.addIngredient(ingredientID,recipeID,measurementType,qty);
            String ingredientToString = ("(" + recIngID + ") [" + ingredientsManager.getIngredientById(ingredientID).getIngredientName() + "] {" +
                    qty + "} " + measurementType);
            ingredients.add(ingredientToString);
            ingredientNumPicker.getItems().add(recIngID);
            ingredientNameInput.clear();
            ingredientQtyInput.clear();
            ingredientNumPicker.getSelectionModel().clearSelection();
            measurementPicker.getSelectionModel().clearSelection();
        }
        setIngredients();
    }
    private void addInstruction(){
        if(!instructionInput.getText().isEmpty()) {
            int instructionID = (int) instructionNumPicker.getItems().get(instructionNumPicker.getItems().size() - 1);
            int stepNum = instructionsManager.getInstruction(instructionID).getStepNum() + 1;

            instructionID = instructionsManager.insertInstruction(recipeID,stepNum,instructionInput.getText());
            instructions.add(instructionID + ":" + instructionInput.getText());
            instructionNumPicker.getItems().add(instructionID);
            instructionInput.clear();
            instructionNumPicker.getSelectionModel().clearSelection();
        }
        setInstructions();
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
            tagInput.clear();
            setTags();
        }
    }

    private void addTag()  {
        if(!tagInput.getText().isEmpty()){
            int tagid = tagManager.addTag(tagInput.getText());
            Tag tag = tagManager.getTagById(tagid);
            String string = "(" + tag.getTagId() + ") " + tag.getTagName();
            tags.add(string);
            tagNumPicker.getItems().add(tag.getTagId());
            tagInput.clear();
            tagNumPicker.getSelectionModel().clearSelection();
            setTags();
        }
    }
    private void saveRecipe()  {
        RecipeManager recipeManager = new RecipeManager(recipeRepository,recipeTagManager,recipeIngManager,new InstructionsRepository(databaseConnection));
        recipeManager.updateRecipe(recipeID,recipeNameInput.getText(),1,Integer.parseInt(servingSizeInput.getText())
                ,imagePathInput.getText(),recipeDescriptionInput.getText(),Integer.parseInt(durationInput.getText()),ingredients,instructions,tags);

    }

    public void setRecipe(int recipeID)  {
        tagNumPicker.getItems().clear();
        ingredientNumPicker.getItems().clear();
        instructionNumPicker.getItems().clear();
        measurementPicker.getSelectionModel().clearSelection();
        ingredientNameInput.clear();
        ingredientQtyInput.clear();
        instructionInput.clear();
        tagInput.clear();

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

    private void setInstruction(int id)  {
        instructionInput.setText(instructionsManager.getInstruction(id).getStepDescription());
    }
    private void setTag(int tagID)  {
        tagInput.setText(tagManager.getTagById(tagID).getTagName());
    }
    private void loadIngredient(int recipeIngID)  {
            RecipeIngredient ingredient = recipeIngManager.getIngredientById(recipeIngID);
            ingredientNameInput.setText(ingredientsManager.getIngredientById(ingredient.getIngredientID()).getIngredientName());
            ingredientQtyInput.setText(ingredient.getQuantity().toString());
            measurementPicker.getSelectionModel().select(ingredient.getMeasurementUnit());
    }
}
