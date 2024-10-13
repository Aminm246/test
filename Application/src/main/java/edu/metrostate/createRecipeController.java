package edu.metrostate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class createRecipeController {
    @FXML private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private javafx.scene.control.TextField recipeName;
    @FXML private TextField recipeTags;
    @FXML private TextField ingredientQty;
    @FXML private TextField ingredientName;
    @FXML private TextArea instructionStepDesc;
    @FXML private TextField recipeImagePath;
    @FXML private TextField recipeDuration;
    @FXML private TextField recipeServingSize;
    @FXML private TextField instructionStepNum;
    @FXML private TextArea recipeDescription;



    public void switchToViewRecipeList(javafx.event.ActionEvent e) throws IOException {
        /*Parent root = FXMLLoader.load(getClass().getResource("recipeList.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        stage = (Stage) recipeName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipeList.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void createButton(){
        if(recipeName.getCharacters().toString() ==""){
            System.out.println("empty");
        }
        else{
            System.out.println(recipeName.getCharacters().toString());
        }
    }

    public void cancelButton(){

    }

    public void ingredientAddButton(){

    }

    public void instructionStepAddButton(){

    }


}
