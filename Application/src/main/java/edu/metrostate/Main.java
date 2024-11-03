package edu.metrostate;

import Controller.createRecipeController;
import Controller.recipeListController;
import Controller.viewRecipeController;
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
        FXMLLoader createLoader = new FXMLLoader(getClass().getResource("createRecipeView.fxml"));
        Parent root = createLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        createRecipeController controller = createLoader.getController();
        controller.setCreateLoader(createLoader);

        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
        viewLoader.load();
        controller.setViewLoader(viewLoader);

        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("recipeListView.fxml"));
        listLoader.load();
        controller.setListLoader(listLoader);


        viewRecipeController viewController = viewLoader.getController();
        viewController.setCreateLoader(createLoader);
        viewController.setListLoader(listLoader);

        recipeListController listController = listLoader.getController();
        listController.setCreateLoader(createLoader);
        listController.setViewLoader(viewLoader);
    }

}