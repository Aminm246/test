package edu.metrostate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cookbook v0.1");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        BorderPane root = loader.load();

        //Toolbar setup
        MainToolbar toolbar = new MainToolbar();
        root.setTop(toolbar);
        Scene scene = new Scene(root, 1920, 1080);
        //Add css to scene
        //Expand later to deal with null exception
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        //Setup main content
        loader = new FXMLLoader(getClass().getResource("recipe.fxml"));
        Pane appContent = loader.load();
        root.setCenter(appContent);


        stage.setScene(scene);
        stage.show();
    }
}