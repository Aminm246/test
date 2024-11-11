package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import java.sql.SQLException;

public class MenuBarController {
    @FXML
    private MenuBar menuBar;

    private static FXMLLoader createLoader;
    private static FXMLLoader listLoader;
    private static FXMLLoader viewLoader;
    private static FXMLLoader updateLoader;

    public void setLoaders(FXMLLoader createLoader, FXMLLoader listLoader,
                           FXMLLoader viewLoader, FXMLLoader updateLoader) {
        MenuBarController.createLoader = createLoader;
        MenuBarController.listLoader = listLoader;
        MenuBarController.viewLoader = viewLoader;
        MenuBarController.updateLoader = updateLoader;
    }

    @FXML
    public void switchToRecipeList() throws SQLException {
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

    public void switchToViewRecipe(int recipeID) throws SQLException {
        if (viewLoader != null && menuBar != null && menuBar.getScene() != null) {
            menuBar.getScene().setRoot(viewLoader.getRoot());
            viewRecipeController controller = viewLoader.getController();
            controller.setRecipe(recipeID);
        }
    }
}
