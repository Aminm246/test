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

    private FXMLLoader viewLoader;

    @FXML
    private ListView<String> recipeListView;

    @FXML
    private Button groceryListButton,viewRecipeButton;
    private RecipeManager recipeManager;

    ObservableList<String> recipes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        recipeManager = new RecipeManager(databaseConnection,new RecipeTagManager(databaseConnection),
                new RecipeIngManager(databaseConnection),new InstructionsManager(databaseConnection));

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

    @FXML
    public void populateList(){
        recipes.clear();
        for (Recipe recipe : recipeManager.getRecipes()) {
            recipes.add(recipe.getRecipeName() + " (" + recipe.getRecipeID() + ")");
        }
        recipeListView.setItems(recipes);
        recipeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;

        for (Recipe recipe : recipeManager.getRecipes()) {
            System.out.println(recipe);
        }
    }

    public void switchToViewRecipe(int recipeID){
        recipeListView.getScene().setRoot(viewLoader.getRoot());
        viewRecipeController controller = viewLoader.getController();
        controller.setRecipe(recipeID);
    }

    public void setViewLoader(FXMLLoader viewLoader){
        this.viewLoader = viewLoader;
    }
}
