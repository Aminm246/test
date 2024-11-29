package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import java.sql.SQLException;

public class MenuBarController {
    @FXML
    private MenuBar menuBar;

    private static FXMLLoader createLoader, listLoader, searchLoader;

    public void setLoaders(FXMLLoader createLoader, FXMLLoader listLoader, FXMLLoader searchLoader) {
        MenuBarController.createLoader = createLoader;
        MenuBarController.listLoader = listLoader;
        MenuBarController.searchLoader = searchLoader;
    }

    @FXML
    public void switchToRecipeList() {
        if (listLoader != null && menuBar != null && menuBar.getScene() != null) {
            menuBar.getScene().setRoot(listLoader.getRoot());
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

}
