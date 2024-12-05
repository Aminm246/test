package Controller;

import Model.Recipe;
import Model.RecipeIngredient;
import Model.RecipeTag;
import Repository.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class searchRecipeController implements Initializable  {
    private FXMLLoader viewLoader;

    @FXML
    private Button ingredientSubmit, nameSubmitButton, allIngredientsSubmit, addTagButton, submitTagsButton,
            searchSubmit, clearButton;

    @FXML
    private TextField recipeNameInput, ingredientNameInput, tagNameInput;

    @FXML
    private TextArea ingredientFxList, tagFxList;

    @FXML
    private ListView<String> recipeListView;

    @FXML
    private ObservableList<String> recipes;

    private String recipeName;
    private List<String> ingredientNames;
    private List<String> tagNames;

    RecipeManager recipeManager;
    IngredientsManager ingredientsManager;
    RecipeTagManager recipeTagManager;
    TagManager tagManager;
    RecipeIngManager recipeIngManager;
    InstructionsManager instructionsManager;

    DatabaseConnection databaseConnection;

    private URL location;
    private ResourceBundle resources;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        databaseConnection = new DatabaseConnection();

        ingredientsManager = new IngredientsManager(databaseConnection);
        recipeTagManager = new RecipeTagManager(databaseConnection);
        tagManager = new TagManager(databaseConnection);
        recipeIngManager = new RecipeIngManager(databaseConnection);
        instructionsManager = new InstructionsManager(databaseConnection);
        recipeManager = new RecipeManager(databaseConnection,recipeTagManager,recipeIngManager,instructionsManager);

        nameSubmitButton.setOnAction(event -> addRecipeNameClick());
        ingredientSubmit.setOnAction(event -> addIngredientNameClick());
        allIngredientsSubmit.setOnAction(event -> addAllIngredientsClick());
        addTagButton.setOnAction(event -> addSingleTagClick());
        submitTagsButton.setOnAction(event -> addAllTagsClick());
        clearButton.setOnAction(event -> clearButtonClick());
        searchSubmit.setOnAction(event -> searchButtonClick());

        ingredientNames = new ArrayList<>();
        tagNames = new ArrayList<>();
        searchSubmit.setDisable(true);
        recipes = FXCollections.observableArrayList();
        recipeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String recipeName = recipeListView.getSelectionModel().getSelectedItem();
                if(!recipeName.isEmpty()){
                    int recipeID = Integer.parseInt(recipeName.substring(recipeName.indexOf("(") + 1,recipeName.indexOf(")")));
                    switchToViewRecipe(recipeID);
                }

            }
        });
    }

    @FXML
    private void searchButtonClick() {
        if (!recipeNameInput.isDisable()) recipeNameInput.setDisable(true);
        if (!ingredientNameInput.isDisable()) ingredientNameInput.setDisable(true);
        if (!tagNameInput.isDisable()) tagNameInput.setDisable(true);
        if (!tagFxList.isDisable()) tagFxList.setDisable(true);
        if (!ingredientFxList.isDisable()) ingredientFxList.setDisable(true);
        if (!ingredientSubmit.isDisable()) ingredientSubmit.setDisable(true);
        if (!nameSubmitButton.isDisable()) nameSubmitButton.setDisable(true);
        if (!allIngredientsSubmit.isDisable()) allIngredientsSubmit.setDisable(true);
        if (!submitTagsButton.isDisable()) submitTagsButton.setDisable(true);

        recipes.clear();
        for (Recipe recipe : recipeManager.getRecipes()) {
            if (recipeName != null && !recipeName.isEmpty()) {
                if (!recipe.getRecipeName().trim().equalsIgnoreCase(recipeName.trim())) {
                    continue;
                }
            }

            if (!tagNames.isEmpty()) {
                boolean tagMatchFound = false;
                for (RecipeTag recipeTag : recipe.getTags()) {
                    String recipeTagName = recipeTag.getTag().getTagName().toLowerCase();
                    if (tagNames.stream().map(String::toLowerCase).anyMatch(recipeTagName::equals)) {
                        tagMatchFound = true;
                        break;
                    }
                }
                if (!tagMatchFound) {
                    continue;
                }
            }

            if (!ingredientNames.isEmpty()) {
                boolean ingredientMatchFound = false;
                for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
                    String recipeIngredientName = recipeIngredient.getIngredient().getIngredientName().toLowerCase();
                    if (ingredientNames.stream().map(String::toLowerCase).anyMatch(recipeIngredientName::equals)) {
                        ingredientMatchFound = true;
                        break;
                    }
                }
                if (!ingredientMatchFound) {
                    continue;
                }
            }
            recipes.add(recipe.getRecipeName() + " (" + recipe.getRecipeID() + ")");
        }
        if (recipes.isEmpty()) recipes.add("No results found!");
        recipeListView.setItems(recipes);
    }

    private void clearButtonClick() {
        Button [] buttons = {ingredientSubmit, nameSubmitButton, allIngredientsSubmit, addTagButton, submitTagsButton,
                searchSubmit, clearButton};
        TextArea [] inputs1 = {ingredientFxList, tagFxList};
        TextField [] inputs2 = {recipeNameInput, ingredientNameInput, tagNameInput};

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

        recipeName = null;
        ingredientNames.clear();
        tagNames.clear();

        recipes.clear();
        recipeListView.setItems(recipes);
        initialize(this.location, this.resources);

    }

    private void addSingleTagClick() {
        String tag = tagNameInput.getText().trim().toLowerCase();
        if (!tag.isEmpty()) {
            tagNames.add(tag);
            if(tagFxList.getText().isEmpty()){
                tagFxList.setText(tag);
            }
            else{
                tagFxList.setText(tagFxList.getText() + ", " + tag );
            }
            tagNameInput.clear();
        }
    }

    private void addAllTagsClick() {
        addSingleTagClick();
        tagNameInput.setDisable(true);
        addTagButton.setDisable(true);
        submitTagsButton.setDisable(true);
        tagFxList.setDisable(true);
        makeSearchable();
    }

    private void addAllIngredientsClick() {
        addIngredientNameClick();
        ingredientFxList.setDisable(true);
        ingredientNameInput.setDisable(true);
        ingredientSubmit.setDisable(true);
        allIngredientsSubmit.setDisable(true);
        makeSearchable();
    }

    private void addIngredientNameClick() {
        String ingredientName = ingredientNameInput.getText().toLowerCase();
        if (!ingredientName.isEmpty()) {
            this.ingredientNames.add(ingredientName);
            ingredientFxList.setText(ingredientFxList.getText() + ingredientName + "\n");
            ingredientNameInput.clear();
        }

    }

    private void addRecipeNameClick() {
        this.recipeName = recipeNameInput.getText().trim().toLowerCase();
        recipeNameInput.setDisable(true);
        nameSubmitButton.setDisable(true);
        makeSearchable();
    }

    public void makeSearchable() {
        if (searchSubmit.isDisable()) {
            searchSubmit.setDisable(false);
        }
    }

    public void setViewLoader(FXMLLoader viewLoader){
        this.viewLoader = viewLoader;
    }

    @FXML
    private void switchToViewRecipe(int recipeID) {
        ingredientSubmit.getScene().setRoot(viewLoader.getRoot());
        viewRecipeController controller = viewLoader.getController();
        controller.setRecipe(recipeID);
    }

}
