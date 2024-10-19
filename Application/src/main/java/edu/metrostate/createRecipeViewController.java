package edu.metrostate;

import ingredient.model.Ingredient;
import ingredient.model.IngredientsInventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipe.model.InstructionStep;
import recipe.model.Recipe;
import recipe.model.RecipeManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class createRecipeViewController {

    @FXML
    private TextField recipeNameInput, ingredientNameInput, ingredientQtyInput,
            tagInput, durationInput, servingSizeInput, imagePathInput;
    @FXML
    private TextArea instructionInput, recipeDescriptionInput,instructionFxList,ingredientFxList;


    @FXML
    private Button ingredientSubmit, recipeNameSubmit, descriptionSubmit, tagSubmit, instructionSubmit,
            allInstructionsSubmit, durationSubmit, servingSizeSubmit, imagePathSubmit,
            allIngredientsSubmit, tagsSubmit, recipeSubmit, clearRecipeButton;

    String recipeName;
    List<Ingredient> ingredientList;
    List<BigDecimal> ingredientQtyList;
    List<InstructionStep> instructionSteps;

    int ingredientCount;
    String description;
    int duration;
    int servingSize;
    String imagePath;
    IngredientsInventory ingredientInventory = new IngredientsInventory();
    RecipeManager recipeManager = new RecipeManager();
    //submitCounter counts the submits of each section to make sure it's all submitted before creating recipe.
    int submitCounter;

    List<TextArea> instructionStepsInputs;

    int instructionCount;

    List<String> tagList;
    List<TextField> tagInputs;
    List<Button> tagSubmitButtons;
    int tagCount;
    boolean tagsPlusClicked;

    @FXML
    public void initialize() {
        try {
        ingredientCount = 0;
        submitCounter = 0;
        instructionCount = 0;

        ingredientList = new ArrayList<Ingredient>();
        ingredientQtyList = new ArrayList<BigDecimal>();
        instructionSteps = new ArrayList<InstructionStep>();

<<<<<<< Updated upstream
=======
        ingredientNameInputs = List.of(ingredient1NameInput, ingredient2NameInput, ingredient3NameInput, ingredient4NameInput, ingredient5NameInput, ingredient6NameInput);
        ingredientQtyInputs = List.of(ingredient1QtyInput, ingredient2QtyInput, ingredient3QtyInput, ingredient4QtyInput, ingredient5QtyInput, ingredient6QtyInput);
        instructionStepsInputs = List.of(instruction1Input, instruction2Input, instruction3Input, instruction4Input, instruction5Input);

        tagList = new ArrayList<>();
        tagInputs = List.of(tag1Input, tag2Input, tag3Input, tag4Input, tag5Input);
        tagSubmitButtons = List.of(tag1Submit, tag2Submit, tag3Submit, tag4Submit, tag5Submit);
        tagCount = 0;
        tagsPlusClicked = false;

        // Set up tag button actions
        for (int i = 0; i < tagSubmitButtons.size(); i++) {
            final int index = i;
            tagSubmitButtons.get(i).setOnAction(event -> addSingleTagClick(index));
        }
        tagsSubmit.setOnAction(event -> addAllTagsClick());

>>>>>>> Stashed changes

        recipeNameSubmit.setOnAction(event -> addRecipeNameClick());
        ingredientSubmit.setOnAction(event -> addSingleIngredientClick());
        allIngredientsSubmit.setOnAction(event -> addAllIngredientsClick());
        durationSubmit.setOnAction(event -> addRecipeDuration());
        descriptionSubmit.setOnAction(event -> addRecipeDescription());
        servingSizeSubmit.setOnAction(event -> addRecipeServingSize());
        imagePathSubmit.setOnAction(event -> addRecipeImagePath());
        recipeSubmit.setOnAction(event -> createRecipe());
        instructionSubmit.setOnAction(event -> addSingleInstructionClick());
        allInstructionsSubmit.setOnAction(event -> addAllInstructionsClick());
<<<<<<< Updated upstream
        clearRecipeButton.setOnAction(event -> clearRecipe());
=======

        } catch (Exception e) {
            System.err.println("Error in initialize method:");
            e.printStackTrace();
        }
>>>>>>> Stashed changes
    }

    private void addAllIngredientsClick() {
        if (ingredientCount < 6) {
            if (!ingredientNameInput.getText().isEmpty() && !ingredientQtyInput.getText().isEmpty()) {
                addSingleIngredientClick();
            }
        }
        ingredientSubmit.setDisable(true);
        allIngredientsSubmit.setDisable(true);
        System.out.println(ingredientList.toString());
        System.out.println(ingredientQtyList.toString());
        submitCounter++;
    }

    private void addSingleTagClick(int index) {
        String tag = tagInputs.get(index).getText().trim();
        if (tag.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Tag cannot be empty. Please enter a valid tag.");
            alert.showAndWait();
        } else {
            System.out.println("Tag: " + tag);
            tagList.add(tag);
            tagInputs.get(index).clear();
            tagsPlusClicked = true;
            if (index < tagInputs.size() - 1) {
                tagInputs.get(index + 1).setDisable(false);
                tagSubmitButtons.get(index + 1).setDisable(false);
            }
            tagsSubmit.setDisable(false);
        }
    }

    private void addAllTagsClick() {
        if (!tagsPlusClicked) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You didn't click the '+' button to add any tags. Are you sure you want to submit without tags?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    submitTags();
                }
            });
        } else {
            submitTags();
        }
    }

    private void submitTags() {
        for (TextField input : tagInputs) {
            input.setDisable(true);
        }
        for (Button button : tagSubmitButtons) {
            button.setDisable(true);
        }
        tagsSubmit.setDisable(true);
        System.out.println("All tags: " + tagList.toString());
        // Enable the next field or button as needed
        // For example: durationInput.setDisable(false);
    }

    private void addSingleIngredientClick() {
        String ingredientName = ingredientNameInput.getText().trim();
        String ingredientQty = ingredientQtyInput.getText().trim();

        if (ingredientName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient name must not be empty, please enter a valid value.");
            alert.showAndWait();
        }

        if (ingredientQty.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must not be empty, please enter a valid value.");
            alert.showAndWait();
        }

        try {
            BigDecimal qty = new BigDecimal(ingredientQty);
            System.out.println("Ingredient Name: " + ingredientName + " Ingredient qty: " + qty);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must be a valid integer.");
            alert.showAndWait();
            ingredientQtyInput.clear();
            return;
        }

        //ingredientList.add(new Ingredient(ingredientName));
        //need to add implementation for if the ingredient is already added to the inventory. if add just return the existing object.
        Ingredient ingredient = ingredientInventory.addIngredient(ingredientName);
        ingredientList.add(ingredient);
        ingredientQtyList.add(new BigDecimal(ingredientQty));
        ingredientNameInput.clear();
        ingredientQtyInput.clear();
        allIngredientsSubmit.setDisable(false);
        ingredientFxList.setText(ingredientFxList.getText() + ingredientQty + "g " + ingredient.getIngredientName() + "\n");
        ingredientCount++;
    }

    private void addSingleInstructionClick() {
        String instructionStepString = instructionInput.getText().trim();

        if (instructionStepString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Instruction must not be empty, please enter a valid value.");
            alert.showAndWait();
        }

        else {
            InstructionStep instructionStep = new InstructionStep(instructionCount + 1, instructionStepString);
            System.out.println(instructionStep);
            instructionSteps.add(instructionStep);
            instructionFxList.setText(instructionFxList.getText() + "Instruction " + instructionStep.getStepNum() + ": " + instructionStep.getStepDescription() + "\n");
            instructionInput.clear();
            instructionCount++;
            allInstructionsSubmit.setDisable(false);

        }

    }

    private void addAllInstructionsClick() {
        if (!instructionInput.getText().isEmpty()) {
            addSingleInstructionClick();
        }

        allInstructionsSubmit.setDisable(true);
        instructionSubmit.setDisable(true);
        System.out.println(instructionSteps.toString());
        submitCounter++;
    }
    
    private void addRecipeNameClick() {
        String recipeName = recipeNameInput.getText().trim();

        if (recipeName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Recipe cannot be empty, please enter a valid recipe name.");
            alert.showAndWait();

        } else {
            System.out.println("Recipe Name: " + recipeName);
            this.recipeName = recipeName;
            recipeNameInput.setDisable(true);
            recipeNameSubmit.setDisable(true);
            ingredientSubmit.setDisable(false);
            submitCounter++;
        }
    }

    private void addRecipeDescription(){
        String description = recipeDescriptionInput.getText().trim();
        if(description.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Description cannot be empty, please enter a valid description.");
            alert.showAndWait();
        }
        else{
            System.out.println("Description: " + description);
            this.description = description;
            recipeDescriptionInput.setDisable(true);
            descriptionSubmit.setDisable(true);
            submitCounter++;
        }
    }

    private void addRecipeDuration(){
        String duration = durationInput.getText().trim();
        if(duration.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Description cannot be empty, please enter a valid description.");
            alert.showAndWait();
        }
        else{
            try {
                this.duration = Integer.parseInt(duration);
                if(this.duration < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Duration must be a positive integer.");
                    alert.showAndWait();
                    durationInput.clear();
                    return;
                }
                System.out.println("Duration is: " + this.duration);
                durationInput.setDisable(true);
                durationSubmit.setDisable(true);
                submitCounter++;

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Duration must be a valid integer.");
                alert.showAndWait();
                durationInput.clear();
            }
        }
    }

    private void addRecipeServingSize(){
        String servingSize = servingSizeInput.getText().trim();
        if(servingSize.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Serving size cannot be empty, please enter a valid quantity.");
            alert.showAndWait();
        }
        else{
            try {
                this.servingSize = Integer.parseInt(servingSize);
                if(this.servingSize < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Serving size must be a positive integer.");
                    alert.showAndWait();
                    servingSizeInput.clear();
                    return;
                }
                System.out.println("Serving size is: " + this.duration);
                servingSizeInput.setDisable(true);
                servingSizeSubmit.setDisable(true);
                submitCounter++;

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Serving size must be a valid integer.");
                alert.showAndWait();
                servingSizeInput.clear();
            }
        }
    }

    private void addRecipeImagePath(){
        String imagePath = imagePathInput.getText().trim();
        if(imagePath.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to leave image path empty?.");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> imagePathHelper(imagePath));
        }
        else{
            imagePathHelper(imagePath);
        }
    }

    private void imagePathHelper(String imagePath){
        System.out.println("Image path: " + imagePath);
        this.imagePath = imagePath;
        imagePathInput.setDisable(true);
        imagePathSubmit.setDisable(true);
        submitCounter++;
    }

    private void createRecipe(){
        System.out.println(submitCounter);
        if(submitCounter == 7){

            String lastTag = tagInputs.get(tagInputs.size() - 1).getText().trim();
            if (!lastTag.isEmpty() && !tagList.contains(lastTag)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You have an unsubmitted tag. Do you want to add it before creating the recipe?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        addSingleTagClick(tagInputs.size() - 1);
                    }
                });
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to view the recipe?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES){
                Recipe recipe = recipeManager.addRecipe(recipeName,1/*requires user implement*/,tagList,duration,servingSize,description,
                        imagePath,ingredientList,ingredientQtyList,instructionSteps);
                System.out.println(recipe.toString());

                try {
                    Stage stage = (Stage) recipeNameInput.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
                    Parent root = loader.load();
                    viewRecipeController controller = loader.getController();

                    stage.setScene(new Scene(root));
                    controller.setRecipe(recipe);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                clearRecipe();
            }
        }

    }

    private void clearRecipe(){
        Stage stage = (Stage) ingredientSubmit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipeView.fxml"));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
