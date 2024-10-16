package edu.metrostate;

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

        //createRecipeViewController controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}