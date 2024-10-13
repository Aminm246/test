package edu.metrostate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class createRecipeController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToViewRecipeList(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("recipeList.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
