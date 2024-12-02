package edu.metrostate;

import Controller.createRecipeController;
import Controller.recipeListController;
import Controller.searchRecipeController;
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

    public static void main(String[] args)  {
        launch(args);
        DatabaseConnection db = new DatabaseConnection();
        RecipeRepository recipeRepository = new RecipeRepository(db);
        IngredientsRepository ingredientRepository = new IngredientsRepository(db);
        RecipeIngRepository recipeIngRepository = new RecipeIngRepository(db);
        InstructionsRepository instructionRepository = new InstructionsRepository(db);
        TagRepository tagRepository = new TagRepository(db);
        RecipeTagRepository recipeTagRepository = new RecipeTagRepository(db);

        Connection connection = null;
        connection = db.getConnection();

        if (connection != null) {
                recipeRepository.createTable();
                ingredientRepository.createTable();
                recipeIngRepository.createTable();
                instructionRepository.createTable();
                tagRepository.createTable();
                recipeTagRepository.createTable();
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

        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("searchRecipeView.fxml"));
        searchLoader.load();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuBar.fxml"));
        menuLoader.load();
        MenuBarController menuController = menuLoader.getController();
        menuController.setLoaders(createLoader, listLoader,searchLoader,viewLoader);

        // Set up scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Set up controllers
        ((createRecipeController)createLoader.getController()).setViewLoader(viewLoader);
        ((recipeListController)listLoader.getController()).setViewLoader(viewLoader);
        ((searchRecipeController)searchLoader.getController()).setViewLoader(viewLoader);

        viewRecipeController viewController = viewLoader.getController();
        viewController.setMenuLoader(menuLoader);
        viewController.setUpdateLoader(updateLoader);

        stage.show();
    }
}