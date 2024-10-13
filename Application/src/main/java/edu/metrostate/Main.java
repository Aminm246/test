package edu.metrostate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cookbook v0.1");

        //Setup main content
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipe.fxml"));
//        Parent root = loader.load();
//
//        Image image = new Image(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg"));
//        viewRecipeController controller = loader.getController();
//        controller.setImage(image);
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//        stage.setScene(scene);
//        stage.show();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipe.fxml"));
        Parent root = loader.load();

//        Image image = new Image(getClass().getResourceAsStream("/edu/metrostate/images/temp_photo.jpeg"));
        createRecipeController controller = loader.getController();
//        controller.setImage(image);

        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}