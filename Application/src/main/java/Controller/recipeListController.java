package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class recipeListController implements Initializable {

    private FXMLLoader createLoader;

    @FXML
    private ListView<String> recipeListView;

    @FXML
    private Label myLabel;

    RecipeManager recipeManager;
    public static final ObservableList recipes =
            FXCollections.observableArrayList();
    List recipeNames;
    String currentFood;
    String[] food = {"apple","Banana"};

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
    public void populateList(){
        recipeNames = new ArrayList<>();
        Object [] recipeIDs = recipeManager.getRecipes();
        recipeListView = new ListView<>();


        int recipeID;
        for (Object x : recipeIDs){
            recipeID = Integer.parseInt(x.toString());
            System.out.println(recipeManager.getRecipe(recipeID).getRecipeName());
            //recipeNames.add(recipeManager.getRecipe(recipeID).getRecipeName());
            recipes.add(recipeManager.getRecipe(recipeID).getRecipeName());
        }


        recipeListView.setItems(recipes);
        System.out.println(recipeListView.getItems());
        recipeListView.setCellFactory(ComboBoxListCell.forListView(recipes));
        /*
        recipeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentFood = recipeListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentFood);
            }
        });*/

    }
    public void switchToCreateRecipe(){
        myLabel.getScene().setRoot(createLoader.getRoot());
    }

    public void setRecipeManager(RecipeManager recipeManager){
        this.recipeManager = recipeManager;

        Object [] ids = this.recipeManager.getRecipes();
        for (int i = 0; i <ids.length;i++){
            System.out.println(ids[i]);
        }
    }

    public void setCreateLoader(FXMLLoader createLoader) {
        this.createLoader = createLoader;
    }
}
