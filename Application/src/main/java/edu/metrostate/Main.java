package edu.metrostate;

import Controller.createRecipeController;
import Controller.recipeListController;
import Controller.viewRecipeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cookbook v0.1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipeView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        createRecipeController controller = loader.getController();
        controller.setCreateLoader(loader);

        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
        viewLoader.load();
        controller.setViewLoader(viewLoader);

        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("recipeListView.fxml"));
        listLoader.load();
        controller.setListLoader(listLoader);


        viewRecipeController viewController = viewLoader.getController();
        viewController.setCreateLoader(loader);
        viewController.setListLoader(listLoader);

        recipeListController listController = listLoader.getController();
        listController.setCreateLoader(loader);
    }

}