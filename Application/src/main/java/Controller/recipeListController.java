package Controller;

import Model.Recipe;
import Model.RecipeManager;
import Repository.DatabaseConnection;
import Repository.RecipeRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class recipeListController implements Initializable {

    private FXMLLoader createLoader;

    @FXML
    private ListView<String> recipeListView;

    @FXML
    private Label myLabel;

    private DatabaseConnection databaseConnection;
    private RecipeRepository recipeRepository;

    RecipeManager recipeManager;

    List<String> recipes;
    String currentFood;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipes = new ArrayList<>();
        /*recipeListView = new ListView<>();

        recipeListView.getItems().addAll(food);
        recipeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentFood = recipeListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentFood);
            }
        });
        */

    }

    public void populateList() throws SQLException {
        for (Recipe recipe : recipeManager.getRecipes()) {
            System.out.println(recipe);
            recipes.add(recipe.getRecipeName());
        }
        recipeListView = new ListView<>();
        recipeListView.getItems().addAll(recipes);


//        Object [] recipeIDs = recipeManager.getRecipes();
//        int recipeID;
//        for (Object x : recipeIDs){
//            recipeID = Integer.parseInt(x.toString());
//            System.out.println(recipeManager.getRecipe(recipeID).getRecipeName());
//            recipes.add(recipeManager.getRecipe(recipeID).getRecipeName());
//        }
//
//        recipeListView = new ListView<>();
//        recipeListView.getItems().addAll(recipes);
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

    public void setRecipeManager(RecipeManager recipeManager) throws SQLException {
        this.recipeManager = recipeManager;

        for (Recipe recipe : recipeManager.getRecipes()) {
            System.out.println(recipe);
        }

//        Object [] ids = this.recipeManager.getRecipes();
//        for (int i = 0; i <ids.length;i++){
//            System.out.println(ids[i]);
//        }
    }

    public void setCreateLoader(FXMLLoader createLoader) {
        this.createLoader = createLoader;
    }
}
