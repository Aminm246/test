package Controller;

import Model.Recipe;
import Model.RecipeIngredient;
import Repository.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class recipeListController implements Initializable {

    private FXMLLoader createLoader;
    private FXMLLoader viewLoader;
    private FXMLLoader listLoader;
    private FXMLLoader searchLoader;

    @FXML
    private ListView<String> recipeListView;

    @FXML
    private Label myLabel;

    @FXML
    private Button groceryListButton,viewRecipeButton;
    private FXMLLoader menuLoader;
    private RecipeManager recipeManager;
    private RecipeTagManager recipeTagManager;
    private RecipeIngManager recipeIngManager;
    private InstructionsManager instructionsManager;
    private TagManager tagManager;
    private IngredientsManager ingredientsManager;

    private DatabaseConnection databaseConnection;
    private RecipeRepository recipeRepository;
    private RecipeTagRepository recipeTagRepository;
    private RecipeIngRepository recipeIngRepository;
    private InstructionsRepository instructionsRepository;
    private TagRepository tagRepository;
    private IngredientsRepository ingredientsRepository;


    ObservableList<String> recipes;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeTagRepository = new RecipeTagRepository(databaseConnection);
        recipeIngRepository = new RecipeIngRepository(databaseConnection);
        instructionsRepository = new InstructionsRepository(databaseConnection);
        tagRepository = new TagRepository(databaseConnection);
        ingredientsRepository = new IngredientsRepository(databaseConnection);

        tagManager = new TagManager(tagRepository);
        ingredientsManager = new IngredientsManager(ingredientsRepository);
        recipeTagManager = new RecipeTagManager(recipeTagRepository, tagRepository);
        recipeIngManager = new RecipeIngManager(recipeIngRepository, ingredientsRepository);
        instructionsManager = new InstructionsManager(instructionsRepository);


        recipeManager = new RecipeManager(recipeRepository, recipeTagManager, recipeIngManager, instructionsRepository);
        recipes = FXCollections.observableArrayList();
        viewRecipeButton.setOnAction(event -> {
            String recipeName = recipeListView.getSelectionModel().getSelectedItem();

            if(!recipeName.isEmpty()){
                int recipeID = Integer.parseInt(recipeName.substring(recipeName.indexOf("(") + 1,recipeName.indexOf(")")));
                switchToViewRecipe(recipeID);

            }
        });

        groceryListButton.setOnAction(event -> generateGroceryList());
    }

    private void generateGroceryList() {
        List<String> selectedItems = recipeListView.getSelectionModel().getSelectedItems();
        List<String> ingredientList = new ArrayList<>();

        for(String selectedRecipe : selectedItems){
            int recipeID = Integer.parseInt(selectedRecipe.substring(selectedRecipe.indexOf("(") + 1, selectedRecipe.indexOf(")")));
            addToIngredientList(recipeID,ingredientList);
        }
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Grocery List");
        String listText = "";
        for(String ingredient: ingredientList){
            listText = listText.concat(ingredient + "\n");
        }
        
        dialog.setContentText(listText);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE));
        dialog.showAndWait();
        //
    }

    private void addToIngredientList(int recipeID, List<String> ingredientList) {
        List<RecipeIngredient> recIng =  recipeManager.getRecipe(recipeID).getRecipeIngredients();
        for(RecipeIngredient recipeIngredient: recIng){
            int i = 0;
            boolean duplicate = false;
            for(String ingredientParse: ingredientList){
                if(recipeIngredient.getIngredient().getIngredientName().equals(ingredientParse.substring(0,ingredientParse.indexOf(":")))){
                    ingredientList.set(i,ingredientParse.concat(" + " + recipeIngredient.getQuantity() + " " + recipeIngredient.getMeasurementUnit()));
                    duplicate = true;
                    break;
                }
                i++;
            }
            if(!duplicate){
                ingredientList.add(recipeIngredient.parseIngredient());
            }
        }
    }

    public void setMenuLoader(FXMLLoader menuLoader) {
        this.menuLoader = menuLoader;
    }

    @FXML
    public void populateList(){
        recipes.clear();
        for (Recipe recipe : recipeManager.getRecipes()) {
            recipes.add(recipe.getRecipeName() + " (" + recipe.getRecipeID() + ")");
        }
        recipeListView.setItems(recipes);
        recipeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public void switchToSearch(){
        myLabel.getScene().setRoot(searchLoader.getRoot());
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;

        for (Recipe recipe : recipeManager.getRecipes()) {
            System.out.println(recipe);
        }
    }

    public void setCreateLoader(FXMLLoader createLoader) {
        this.createLoader = createLoader;
    }

    public void switchToViewRecipe(int recipeID){
        recipeListView.getScene().setRoot(viewLoader.getRoot());
        viewRecipeController controller = viewLoader.getController();
        controller.setRecipe(recipeID);
    }

    public void setListLoader(FXMLLoader listLoader) {
        this.listLoader = listLoader;
    }

    public void setViewLoader(FXMLLoader viewLoader){
        this.viewLoader = viewLoader;
    }

    public void setSearchLoader(FXMLLoader searchLoader) {
        this.searchLoader = searchLoader;
    }
}
