package edu.metrostate;

import Controller.createRecipeController;
import Controller.recipeListController;
import Controller.viewRecipeController;
import Controller.MenuBarController;
import Repository.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    private Stage stage;
    private FXMLLoader menuLoader;
    public static void main(String[] args) throws SQLException {
        launch(args);
        DatabaseConnection db = new DatabaseConnection();
        RecipeRepository recipeRepository = new RecipeRepository(db);
        IngredientsRepository ingredientRepository = new IngredientsRepository(db);
        RecipeIngRepository recipeIngRepository = new RecipeIngRepository(db);
        InstructionsRepository instructionRepository = new InstructionsRepository(db);
        TagRepository tagRepository = new TagRepository(db);
        RecipeTagRepository recipeTagRepository = new RecipeTagRepository(db);
        Connection connection = db.getConnection();
        if (connection != null) {
            try {
                recipeRepository.createTable();
                ingredientRepository.createTable();
                recipeIngRepository.createTable();
                instructionRepository.createTable();
                tagRepository.createTable();
                recipeTagRepository.createTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            } finally {
//                db.closeConnection();
//            }
        }
        else {
            System.out.println("Connection failed.");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cookbook v0.1");

        // Load all FXML files
        FXMLLoader createLoader = new FXMLLoader(getClass().getResource("createRecipeView.fxml"));
        Parent root = createLoader.load();

        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
        viewLoader.load();

        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("recipeListView.fxml"));
        listLoader.load();

        FXMLLoader updateLoader = new FXMLLoader(getClass().getResource("updateRecipeView.fxml"));
        updateLoader.load();


        // Initialize MenuBar
        // Initialize MenuBar
        menuLoader = new FXMLLoader(getClass().getResource("menuBar.fxml"));
        menuLoader.load();
        MenuBarController menuController = menuLoader.getController();
        menuController.setLoaders(createLoader, listLoader, viewLoader,updateLoader);

        // Set up scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Set up controllers
        createRecipeController createController = createLoader.getController();
        viewRecipeController viewController = viewLoader.getController();
        recipeListController listController = listLoader.getController();

        // Set MenuLoader for all controllers
        createController.setMenuLoader(menuLoader);
        viewController.setMenuLoader(menuLoader);
        listController.setMenuLoader(menuLoader);

        // Set up other loaders
        createController.setCreateLoader(createLoader);
        createController.setViewLoader(viewLoader);
        createController.setListLoader(listLoader);

        viewController.setCreateLoader(createLoader);
        viewController.setListLoader(listLoader);
        viewController.setUpdateLoader(updateLoader);

        listController.setCreateLoader(createLoader);
        listController.setViewLoader(viewLoader);

        stage.show();
    }
}