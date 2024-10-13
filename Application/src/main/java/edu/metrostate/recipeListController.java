package edu.metrostate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class recipeListController implements Initializable {

    private ListView<String> recipeListView;

    @FXML
    private Label myLabel;

    String[] food = {"Pizza", "Sushi", "Burger"};

    String currentFood;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //recipeListView.getItems().addAll(food);
    }
}
