package Controller;

import Repository.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;

import java.util.Arrays;
import java.util.Optional;

public class MenuBarController {
    @FXML
    private MenuBar menuBar;

    private static FXMLLoader createLoader, listLoader, searchLoader,viewLoader;


    public void setLoaders(FXMLLoader createLoader, FXMLLoader listLoader, FXMLLoader searchLoader,FXMLLoader viewLoader) {
        MenuBarController.createLoader = createLoader;
        MenuBarController.listLoader = listLoader;
        MenuBarController.searchLoader = searchLoader;
        MenuBarController.viewLoader = viewLoader;
    }

    @FXML
    public void switchToRecipeList() {
        if (listLoader != null && menuBar != null && menuBar.getScene() != null) {
            menuBar.getScene().setRoot(listLoader.getRoot());
            recipeListController listController = listLoader.getController();
            listController.populateList();
        }
    }

    public void switchToRecipeList(Control x) {
        if (listLoader != null && menuBar != null && x.getScene() != null) {
            x.getScene().setRoot(listLoader.getRoot());
            recipeListController listController = listLoader.getController();
            listController.populateList();
        }
    }

    @FXML
    public void switchToCreateRecipe() {
        if (createLoader != null && menuBar != null && menuBar.getScene() != null) {
            menuBar.getScene().setRoot(createLoader.getRoot());
        }
    }

    @FXML
    private void switchToSearch(){
        menuBar.getScene().setRoot(searchLoader.getRoot());
    }

    public void exportRecipe() {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        RecipeManager recipeManager = new RecipeManager(databaseConnection,new RecipeTagManager(databaseConnection),
                new RecipeIngManager(databaseConnection),new InstructionsManager(databaseConnection));
        int recipeID = ((viewRecipeController) viewLoader.getController()).getRecipeID();
        if(recipeID != 0){
            System.out.println(recipeManager.getRecipe(recipeID).toCleanString());
            TextInputDialog prompt = new TextInputDialog(recipeManager.getRecipe(recipeID).toCleanString());
            prompt.setHeaderText("Recipe Export");
            prompt.setHeight(200);
            prompt.setWidth(500);
            prompt.showAndWait();
        }

        databaseConnection.closeConnection();
    }

    public void importRecipe() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        RecipeManager recipeManager = new RecipeManager(databaseConnection,new RecipeTagManager(databaseConnection),
                new RecipeIngManager(databaseConnection),new InstructionsManager(databaseConnection));

        TextInputDialog prompt = new TextInputDialog("Paste recipe here to import");
        prompt.setHeaderText("Recipe Import");
        prompt.setHeight(200);
        prompt.setWidth(500);
        Optional<String> result = prompt.showAndWait();
        if (result.isPresent()) {
            System.out.println(result.get());
            recipeManager.importRecipe(result.get());
        }
    }
}
