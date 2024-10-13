package edu.metrostate;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {
    @FXML
    private ImageView imageView;

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void viewRecipe(){

    }
}
