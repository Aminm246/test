package edu.metrostate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
}
