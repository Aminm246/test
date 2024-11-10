package Controller;

import Model.Recipe;
import Repository.DatabaseConnection;
import Repository.RecipeRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class recipeListController implements Initializable {

    private FXMLLoader createLoader;
    private FXMLLoader viewLoader;

    @FXML
    private ListView<String> recipeListView;

    @FXML
    private Label myLabel;

    private RecipeManager recipeManager;
    private DatabaseConnection databaseConnection;
    private RecipeRepository recipeRepository;

    ObservableList<String> recipes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseConnection = new DatabaseConnection();
        recipeRepository = new RecipeRepository(databaseConnection);
        recipeManager = new RecipeManager(recipeRepository);
        recipes = FXCollections.observableArrayList();
        recipeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String recipeName = recipeListView.getSelectionModel().getSelectedItem();
                if(!recipeName.isEmpty()){
                    int recipeID = Integer.parseInt(recipeName.substring(recipeName.indexOf("(") + 1,recipeName.indexOf(")")));

                    try {
                        switchToViewRecipe(recipeID);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }

    @FXML
    public void populateList() throws SQLException {
        recipes.clear();
        for (Recipe recipe : recipeManager.getRecipes()) {
            recipes.add(recipe.getRecipeName() + " (" + recipe.getRecipeID() + ")");
        }
        recipeListView.setItems(recipes);


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

    public void switchToViewRecipe(int recipeID) throws SQLException {
        myLabel.getScene().setRoot(viewLoader.getRoot());
        viewRecipeController controller = viewLoader.getController();
        controller.setRecipe(recipeID);
    }

    public void setViewLoader(FXMLLoader viewLoader){
        this.viewLoader = viewLoader;
    }
}
