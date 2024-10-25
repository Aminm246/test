package Controller;

import Model.Ingredient;
import Model.MeasurementUnit;
import Model.RecipeIngredient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import Model.InstructionStep;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class createRecipeController {

    @FXML
    private TextField recipeNameInput, ingredientNameInput, ingredientQtyInput,
            tagInput, durationInput, servingSizeInput, imagePathInput;
    @FXML
    private TextArea instructionInput, recipeDescriptionInput,instructionFxList,ingredientFxList,tagFxList;


    @FXML
    private Button ingredientSubmit, recipeNameSubmit, descriptionSubmit, tagSubmit, instructionSubmit,
            allInstructionsSubmit, durationSubmit, servingSizeSubmit, imagePathSubmit,
            allIngredientsSubmit, tagsSubmit, recipeSubmit, clearRecipeButton;

    FXMLLoader viewLoader;
    FXMLLoader listLoader;
    FXMLLoader createLoader;

    String recipeName;

    List<RecipeIngredient> recipeIngredients;
    List<Integer> ingredientList;
    List<InstructionStep> instructionSteps;

    int ingredientCount;
    String description;
    int duration;
    int servingSize;
    String imagePath;
    IngredientsInventory ingredientInventory;
    RecipeManager recipeManager;

    //submitCounter counts the submits of each section to make sure it's all submitted before creating recipe.
    int submitCounter;
    int instructionCount;

    List<String> tagList;
    boolean tagsPlusClicked;

    public void setListLoader(FXMLLoader listLoader){
        this.listLoader = listLoader;
        recipeListController controller = this.listLoader.getController();
        controller.setRecipeManager(recipeManager);
    }

    public void setViewLoader(FXMLLoader viewLoader){
        this.viewLoader = viewLoader;
        viewRecipeController controller = this.viewLoader.getController();
        controller.setRecipeManager(recipeManager);
    }

    public void setCreateLoader(FXMLLoader createLoader){
        this.createLoader = createLoader;
    }
    @FXML
    public void initialize() {
        ingredientCount = 0;
        submitCounter = 0;
        instructionCount = 0;
        ingredientList = new ArrayList<Integer>();
        recipeIngredients = new ArrayList<>();
        instructionSteps = new ArrayList<InstructionStep>();
        imagePath = "";
        tagList = new ArrayList<>();
        tagsPlusClicked = false;

        if(ingredientInventory == null){
            ingredientInventory = new IngredientsInventory();
            recipeManager = new RecipeManager();
            recipeManager.setIngredientInventory(ingredientInventory);
            tagSubmit.setOnAction(event -> addSingleTagClick());
            tagsSubmit.setOnAction(event -> addAllTagsClick());
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
            clearRecipeButton.setOnAction(event -> clearRecipe());
        }

    }

    private void addSingleTagClick() {
        String tag = tagInput.getText().trim();
        if (tag.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Tag cannot be empty. Please enter a valid tag.");
            alert.showAndWait();
        } else {
            System.out.println("Tag: " + tag);
            tagList.add(tag);
            if(tagFxList.getText().isEmpty()){
                tagFxList.setText(tag);
            }
            else{
                tagFxList.setText(tagFxList.getText() + ", " + tag );
            }

            tagInput.clear();
            tagsPlusClicked = true;
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
        /* //This should be implemented in the tag functions
        String lastTag = tagInputs.get(tagInputs.size() - 1).getText().trim();
        if (!lastTag.isEmpty() && !tagList.contains(lastTag)) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You have an unsubmitted tag. Do you want to add it before creating the recipe?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    addSingleTagClick();
                }
            });
        }*/
        tagSubmit.setDisable(true);
        tagsSubmit.setDisable(true);
        tagInput.setDisable(true);
        submitCounter++;
        System.out.println("All tags: " + tagFxList.getText());
    }

    private void addSingleIngredientClick() {
        String ingredientName = ingredientNameInput.getText().trim();
        String ingredientQty = ingredientQtyInput.getText().trim();
        BigDecimal qty = new BigDecimal(-1);
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
            qty = new BigDecimal(ingredientQty);
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
        int ingredientID = ingredient.getIngredientId();
        ingredientList.add(ingredientID);
        RecipeIngredient recipeIngredient = new RecipeIngredient(ingredientCount, 1, ingredient, MeasurementUnit.GRAM, qty);
        recipeIngredients.add(recipeIngredient);
        ingredientNameInput.clear();
        ingredientQtyInput.clear();
        allIngredientsSubmit.setDisable(false);
        ingredientFxList.setText(ingredientFxList.getText() + ingredientQty + "g " + ingredientInventory.getIngredientById(ingredientID).getIngredientName() + "\n");
        ingredientCount++;
    }

    private void addAllIngredientsClick() {
        if (ingredientCount < 6) {
            if (!ingredientNameInput.getText().isEmpty() && !ingredientQtyInput.getText().isEmpty()) {
                addSingleIngredientClick();
            }
        }
        ingredientNameInput.setDisable(true);
        ingredientQtyInput.setDisable(true);
        ingredientSubmit.setDisable(true);
        allIngredientsSubmit.setDisable(true);
        System.out.println(ingredientList.toString());
        System.out.println(recipeIngredients.toString());
        submitCounter++;
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
        instructionInput.setDisable(true);
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

        if(imagePath.isEmpty()){

        }
        else{
            System.out.println("Image path: " + imagePath);
            imagePath = imagePath.replace("\"","");
            imagePath = imagePath.replace("\\","/");

            int index = imagePath.indexOf("/");
            String recipeName;
            while(imagePath.indexOf("/",index + 1) != -1){
                index = imagePath.indexOf("/",index + 1);
            }
            recipeName = imagePath.substring(index + 1);

            File imageFile = new File(imagePath);
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //Need to add functionality to check if the file already exists with that name and handle it
            File output = new File("src/main/resources/edu/metrostate/images/" + recipeName);
            try {
                Files.copy(imageFile.toPath(),output.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            imagePath = output.toPath().toString().replace("\\","/");
            imagePath = imagePath.substring(imagePath.indexOf("edu")-1);
        }

        this.imagePath = imagePath;
        imagePathInput.setDisable(true);
        imagePathSubmit.setDisable(true);
        submitCounter++;
    }

    private void createRecipe(){
        System.out.print(submitCounter);
        if(submitCounter == 8){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to view the recipe?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            int recipeID = recipeManager.addRecipe(recipeName,1/*requires user implement*/,tagList,duration,servingSize,description,
                    imagePath,recipeIngredients,instructionSteps);
            System.out.println(recipeManager.getRecipe(recipeID).toString());

            if(result.get() == ButtonType.YES){
                //Changes scene to viewRecipe
                ingredientSubmit.getScene().setRoot(viewLoader.getRoot());
                //Sets up the Recipe on the viewRecipe page.
                viewRecipeController controller = viewLoader.getController();
                controller.setRecipe(recipeID);

            }
            //Clears the create recipe page for additional creations
            clearRecipe();
        }

    }

    @FXML
    private void clearRecipe(){//needs rework
        Button [] buttons = {ingredientSubmit, recipeNameSubmit, descriptionSubmit, tagSubmit, instructionSubmit,
                allInstructionsSubmit, durationSubmit, servingSizeSubmit, imagePathSubmit,
                allIngredientsSubmit, tagsSubmit, recipeSubmit, clearRecipeButton};
        TextArea [] inputs1 = {instructionInput, recipeDescriptionInput,instructionFxList,ingredientFxList,tagFxList};
        TextField [] inputs2 = {recipeNameInput, ingredientNameInput, ingredientQtyInput, tagInput, durationInput,
                servingSizeInput, imagePathInput};

        for (Button button : buttons){
            button.setDisable(false);
        }

        for (TextArea text : inputs1){
            text.setDisable(false);
            text.setText("");
        }

        for (TextField text : inputs2){
            text.setDisable(false);
            text.setText("");
        }

        initialize();
    }

    public void switchToRecipeList() {
        ingredientSubmit.getScene().setRoot(listLoader.getRoot());
        recipeListController listController = listLoader.getController();
        listController.populateList();
    }
}
