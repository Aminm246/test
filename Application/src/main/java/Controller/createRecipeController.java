package Controller;
import Model.*;
import Repository.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.image.Image;

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

    @FXML
    private ComboBox<String> measurementUnitComboBox;
    private List<String> ingredientUnits;

    private FXMLLoader viewLoader;

    String recipeName;

    List<RecipeIngredient> recipeIngredients;
    List<String> ingredientNames;
    List<BigDecimal> ingredientQtys;
    List<InstructionStep> instructionSteps;
    List<String> descriptions;
    List<String> tagNames;

    int ingredientCount;
    String description;
    int duration;
    int servingSize;
    String imagePath;

    IngredientsManager ingredientsManager;
    RecipeManager recipeManager;
    RecipeIngManager recipeIngManager;
    InstructionsManager instructionsManager;
    TagManager tagManager;
    RecipeTagManager recipeTagManager;

    DatabaseConnection databaseConnection;

    //submitCounter counts the submits of each section to make sure it's all submitted before creating recipe.
    int submitCounter;
    int instructionCount;

    List<String> tagList;
    boolean tagsPlusClicked;

    public void setViewLoader(FXMLLoader viewLoader){
        this.viewLoader = viewLoader;
    }

    @FXML
    public void initialize() {
        ingredientCount = 0;
        submitCounter = 0;
        instructionCount = 0;
        ingredientNames = new ArrayList<>();
        ingredientQtys = new ArrayList<>();
        ingredientUnits = new ArrayList<>();
        recipeIngredients = new ArrayList<>();
        instructionSteps = new ArrayList<>();
        tagNames = new ArrayList<>();
        descriptions = new ArrayList<>();
        imagePath = "";
        tagList = new ArrayList<>();
        tagsPlusClicked = false;

        measurementUnitComboBox.getItems().addAll(
                MeasurementUnits.getAll()
        );
        measurementUnitComboBox.setValue("grams"); // Default value

        databaseConnection = new DatabaseConnection();

        ingredientsManager = new IngredientsManager(databaseConnection);
        recipeIngManager = new RecipeIngManager(databaseConnection);
        instructionsManager = new InstructionsManager(databaseConnection);
        tagManager = new TagManager(databaseConnection);
        recipeTagManager = new RecipeTagManager(databaseConnection);
        recipeManager = new RecipeManager(databaseConnection,recipeTagManager,recipeIngManager,instructionsManager);

    }

    @FXML
    private void addSingleTagClick() {
        String tag = tagInput.getText().trim();
        if (tag.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Tag cannot be empty. Please enter a valid tag.");
            alert.showAndWait();
        } else {
            System.out.println("Tag: " + tag);
//            tagList.add(tag);
            tagNames.add(tag);
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

    @FXML
    private void addAllTagsClick() {
        // First check for any unsaved tag in the input field
        if (!tagInput.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Unsaved Tag");
            alert.setContentText("You have an unsaved tag. Would you like to add it before submitting?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    addSingleTagClick();
                }
            });
        }

        if (!tagsPlusClicked) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Tags Warning");
            alert.setContentText("You didn't click the '+' button to add any tags. Are you sure you want to submit without tags?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    submitTags();
                }
            });
        } else {
            submitTags();
        }
    }

    private void submitTags() {
        tagSubmit.setDisable(true);
        tagsSubmit.setDisable(true);
        tagInput.setDisable(true);
        submitCounter++;
        System.out.println("All tags: " + tagFxList.getText());
    }

    @FXML
    private void addSingleIngredientClick(){
        String ingredientName = ingredientNameInput.getText().trim();
        String ingredientQty = ingredientQtyInput.getText().trim();
        String unit = measurementUnitComboBox.getValue();

        if (ingredientName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient name must not be empty, please enter a valid value.");
            alert.showAndWait();
            return;
        }
        if (ingredientQty.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must not be empty, please enter a valid value.");
            alert.showAndWait();
            return;
        }

        try {
            BigDecimal qty = new BigDecimal(ingredientQty);
            ingredientNames.add(ingredientName);
            ingredientQtys.add(qty);
            ingredientUnits.add(unit);

            // Update display with unit
            ingredientFxList.setText(ingredientFxList.getText() +
                    ingredientQty + " " + unit + " " + ingredientName + "\n");

            ingredientCount++;
            ingredientNameInput.clear();
            ingredientQtyInput.clear();
            allIngredientsSubmit.setDisable(false);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ingredient quantity must be a valid integer.");
            alert.showAndWait();
            ingredientQtyInput.clear();
        }
    }

    @FXML
    private void addAllIngredientsClick() {
        // Check if no ingredients have been added
        if (ingredientCount == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Ingredients");
            alert.setHeaderText("Missing Ingredients");
            alert.setContentText("Please add at least one ingredient before submitting.");
            alert.showAndWait();
            return;
        }

        // Check for unsaved text in input fields
        if (!ingredientNameInput.getText().trim().isEmpty() ||
                !ingredientQtyInput.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("Unsaved Ingredient");
            alert.setContentText("You have unsaved ingredient details. Would you like to add them?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                addSingleIngredientClick();
            }
        }

        // Proceed with submission if validation passes
        ingredientNameInput.setDisable(true);
        ingredientQtyInput.setDisable(true);
        ingredientSubmit.setDisable(true);
        allIngredientsSubmit.setDisable(true);
        measurementUnitComboBox.setDisable(true);
        System.out.println(recipeIngredients.toString());
        submitCounter++;
    }

    @FXML
    private void addSingleInstructionClick() {
        String instructionStepString = instructionInput.getText().trim();

        if (instructionStepString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Instruction must not be empty, please enter a valid value.");
            alert.showAndWait();
        }

        else {
//            InstructionStep instructionStep = new InstructionStep(instructionCount + 1, instructionStepString);
            descriptions.add(instructionStepString);

//            System.out.println(instructionStep);
//            instructionSteps.add(instructionStep);
            instructionFxList.setText(instructionFxList.getText() + "Instruction " + descriptions.size() + ": " + instructionStepString + "\n");
            instructionInput.clear();
            instructionCount++;
            allInstructionsSubmit.setDisable(false);

        }

    }

    @FXML
    private void addAllInstructionsClick() {
        // Check if no instructions have been added
        if (instructionCount == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Instructions");
            alert.setHeaderText("Missing Instructions");
            alert.setContentText("Please add at least one instruction before submitting.");
            alert.showAndWait();
            return;
        }

        // Check for unsaved text in input field
        if (!instructionInput.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("Unsaved Instruction");
            alert.setContentText("You have an unsaved instruction. Would you like to add it?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                addSingleInstructionClick();
            }
        }

        instructionInput.setDisable(true);
        allInstructionsSubmit.setDisable(true);
        instructionSubmit.setDisable(true);
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

    @FXML
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

    @FXML
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

    @FXML
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
                System.out.println("Serving size is: " + this.servingSize);
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

    @FXML
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

    private void imagePathHelper(String imagePath) {
        if (imagePath.isEmpty()) {
            // Handle empty path case
            this.imagePath = "";
            imagePathInput.setDisable(true);
            imagePathSubmit.setDisable(true);
            submitCounter++;
            return;
        }

        try {
            System.out.println("Image path: " + imagePath);
            imagePath = imagePath.replace("\"", "");
            imagePath = imagePath.replace("\\", "/");

            // Get the file name from the path
            int index = imagePath.indexOf("/");
            String fileName;
            while (imagePath.indexOf("/", index + 1) != -1) {
                index = imagePath.indexOf("/", index + 1);
            }
            fileName = imagePath.substring(index + 1);

            // Validate file extension
            String lowercaseName = fileName.toLowerCase();
            if (!lowercaseName.endsWith(".jpg") && !lowercaseName.endsWith(".jpeg") &&
                    !lowercaseName.endsWith(".png")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Only JPG, JPEG, and PNG files are supported.");
                alert.showAndWait();
                imagePathInput.clear();
                return;
            }

            try {
                new Image(new File(imagePath).toURI().toString());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Image");
                alert.setHeaderText("File Error");
                alert.setContentText("The selected file is not a valid image file.");
                alert.showAndWait();
                imagePathInput.clear();
                return;
            }

            // Create source and destination files
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Image file not found: " + imagePath);
                alert.showAndWait();
                imagePathInput.clear();
                return;
            }

            // Create resources directory if it doesn't exist
            File resourceDir = new File("src/main/resources/edu/metrostate/images/");
            if (!resourceDir.exists()) {
                resourceDir.mkdirs();
            }

            File output = new File(resourceDir, fileName);

            // Copy file with proper error handling
            try {
                Files.copy(imageFile.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image copied to: " + output.getPath());

                // Update image path for database
                this.imagePath = "/edu/metrostate/images/" + fileName;

                imagePathInput.setDisable(true);
                imagePathSubmit.setDisable(true);
                submitCounter++;

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error copying image file: " + e.getMessage());
                alert.showAndWait();
                imagePathInput.clear();
                return;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error processing image: " + e.getMessage());
            alert.showAndWait();
            imagePathInput.clear();
        }
    }

    @FXML
    private void createRecipe() {
        // Check all required fields before allowing submission
        if (recipeName == null || recipeName.trim().isEmpty()) {
            showError("Recipe Name", "Please enter a recipe name.");
            return;
        }
        if (ingredientCount == 0) {
            showError("Ingredients", "Please add at least one ingredient.");
            return;
        }
        if (instructionCount == 0) {
            showError("Instructions", "Please add at least one instruction.");
            return;
        }
        if (description == null || description.trim().isEmpty()) {
            showError("Description", "Please enter a description.");
            return;
        }
        if (duration <= 0) {
            showError("Duration", "Please enter a valid duration.");
            return;
        }
        if (servingSize <= 0) {
            showError("Serving Size", "Please enter a valid serving size.");
            return;
        }

        if(submitCounter == 8) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to view the recipe?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            try {
                // Create recipe and get ID first
                int recipeID = recipeManager.addRecipe(recipeName, 1, servingSize, imagePath, description, duration);
                if (recipeID == -1) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("Failed to create recipe");
                    alert2.showAndWait();
                    return;
                }

                // Add ingredients
                for (int i = 0; i < ingredientCount; i++) {
                    String ingredientName = ingredientNames.get(i);
                    BigDecimal ingredientQty = ingredientQtys.get(i);
                    String ingredientUnit = ingredientUnits.get(i);
                    int ingredientId = ingredientsManager.addIngredient(ingredientName);
                    recipeIngManager.addIngredient(ingredientId, recipeID, ingredientUnit, ingredientQty);
                }

                // Add instructions
                for (int i = 0; i < descriptions.size(); i++) {
                    String description = descriptions.get(i);
                    instructionsManager.insertInstruction(recipeID, i+1, description);
                }

                // Add tags
                for (int i = 0; i < tagNames.size(); i++) {
                    String tagName = tagNames.get(i);
                    int tagId = tagManager.addTag(tagName);
                    recipeTagManager.addTag(recipeID, tagId);
                }

                Recipe recipe = recipeManager.getRecipe(recipeID);
                if(result.get() == ButtonType.YES) {
                    //Changes scene to viewRecipe
                    ingredientSubmit.getScene().setRoot(viewLoader.getRoot());
                    //Sets up the Recipe on the viewRecipe page
                    viewRecipeController controller = viewLoader.getController();
                    controller.setRecipe(recipeID);
                }
                //Clears the create recipe page for additional creations
                clearRecipe();

            } catch (SQLException e) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Database error: " + e.getMessage());
                alert2.showAndWait();
            }
        }
    }

    private void showError(String field, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Missing Information");
        alert.setHeaderText("Missing " + field);
        alert.setContentText(message);
        alert.showAndWait();
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

}
