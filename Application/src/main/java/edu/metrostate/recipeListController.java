package edu.metrostate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class recipeListController implements Initializable {
    @FXML
    private ListView<String> recipeListView;

    @FXML
    private Label myLabel;

    String[] food = {"Pizza", "Sushi", "Burger"};

    String currentFood;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recipeListView = new ListView<>();
        recipeListView.getItems().addAll(food);
        recipeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentFood = recipeListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentFood);
            }
        });

    }
    public void switchToViewRecipeList(javafx.event.ActionEvent e) throws IOException {

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipeListView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
