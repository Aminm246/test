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
            tag1Input, tag2Input, tag3Input, tag4Input, tag5Input, durationInput, servingSizeInput, imagePathInput;
    @FXML
    private TextArea instructionInput, recipeDescriptionInput,instructionFxList,ingredientFxList;


    @FXML
    private Button ingredientSubmit, recipeNameSubmit, descriptionSubmit, tag1Submit, tag2Submit, tag3Submit, tag4Submit,
            tag5Submit, instructionSubmit, allInstructionsSubmit, durationSubmit, servingSizeSubmit, imagePathSubmit,
            allIngredientsSubmit, instructionsSubmit, tagsSubmit, recipeSubmit, cancelRecipe;

    @FXML
    private Text recipeNameLabel, recipeTagsLabel, ingredientNameLabel1, ingredientQtyLabel1,recipeDescriptionLabel,
            instructionsLabel, durationLabel, servingSizeLabel, imagePathLabel;

    String recipeName;
    List<Ingredient> ingredientList;
    List<BigDecimal> ingredientQtyList;
    List<InstructionStep> instructionSteps;

    List<TextField> ingredientNameInputs;
    List<TextField> ingredientQtyInputs;
    List<Button> ingredientSubmitButtons;
    viewRecipeController viewRecipeControl = new viewRecipeController();
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

    @FXML
    public void initialize() {
        ingredientCount = 0;
        submitCounter = 0;
        instructionCount = 0;

        ingredientList = new ArrayList<Ingredient>();
        ingredientQtyList = new ArrayList<BigDecimal>();
        instructionSteps = new ArrayList<InstructionStep>();


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
            System.out.println(instructionStep.toString());
            instructionSteps.add(instructionStep);
            instructionFxList.setText(instructionFxList.getText() + "Instruction " + instructionStep.getStepNum() + ": " + instructionStep.getStepDescription() + "\n");
            instructionInput.clear();
            instructionCount++;
            allInstructionsSubmit.setDisable(false);

        }

    }

    private void addAllInstructionsClick() {
        if (!instructionInput.getText().isEmpty()) {
            System.out.println("Inside");
            addSingleInstructionClick();
        }

        allInstructionsSubmit.setDisable(true);
        instructionSubmit.setDisable(true);
        System.out.println(instructionSteps.toString());
        recipeDescriptionInput.setDisable(false);
        descriptionSubmit.setDisable(false);
        submitCounter++;
    }


    @FXML
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to view the recipe?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES){
                List<String> tagList = new ArrayList<>();
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

    }
    private void openInstructionField() {
        instructionStepsInputs.get(instructionCount).setDisable(false);
    }

}
