package edu.metrostate;

import Controller.createRecipeController;
import Controller.recipeListController;
import Controller.viewRecipeController;
import Model.InstructionStep;
import Model.Recipe;
import Model.RecipeIngredient;
import Repository.DatabaseConnection;
import Repository.RecipeRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    private Stage stage;
    public static void main(String[] args) throws SQLException {
        launch(args);
        System.out.println("Hello World");
        DatabaseConnection db = new DatabaseConnection();
        RecipeRepository recipeRepository = new RecipeRepository(db);
//        Recipe burger = new Recipe("Burger", 1, null, 40, 2, "A tasty burger recipe", "/fakeImagePath", null, null);
        Connection connection = db.getConnection();
        if (connection != null) {
            try {
                recipeRepository.createTable();
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

//        if (connection != null) {
//
//            try {
//
//                Statement statement = connection.createStatement();
//
//                String createTable = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT)";
//
//                // Create table if not exists
//
////                Operation:C
//
//                statement.executeUpdate(createTable);
//
//
//                // Insert data
//
//                statement.executeUpdate("INSERT INTO students (name) VALUES ('John')");
//
//                statement.executeUpdate("INSERT INTO students (name) VALUES ('Alice')");
//
//                System.out.println("Data Inserted");
//
//
////                Operation:R
//
//                // Display data
//
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
//
//                System.out.println("ID\tName");
//
//                while (resultSet.next()) {
//
//                    int id = resultSet.getInt("id");
//
//                    String name = resultSet.getString("name");
//
//                    System.out.println(id + "\t" + name);
//
//                }
//
//
////                Operation:U
//
//                // Update data
//
//                statement.executeUpdate("UPDATE students SET name = 'Johnny' WHERE id = 1");
//
//                System.out.println("Data Updated");
//
//                // Display updated data
//
//                resultSet = statement.executeQuery("SELECT * FROM students");
//
//                System.out.println("ID\tName");
//
//                while (resultSet.next()) {
//
//                    int id = resultSet.getInt("id");
//
//                    String name = resultSet.getString("name");
//
//                    System.out.println(id + "\t" + name);
//
//                }
//
//
////                Operation:D
//
////                 Delete data
//
//                statement.executeUpdate("DELETE FROM students WHERE id = 2");
//                // Close resources
//                resultSet.close();
//                statement.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//
//            } finally {
//                db.closeConnection();
//            }
//
//        } else {
//            System.out.println("Connection failed.");
//        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cookbook v0.1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createRecipeView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        createRecipeController controller = loader.getController();
        controller.setCreateLoader(loader);

        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("viewRecipe.fxml"));
        viewLoader.load();
        controller.setViewLoader(viewLoader);

        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("recipeListView.fxml"));
        listLoader.load();
        controller.setListLoader(listLoader);


        viewRecipeController viewController = viewLoader.getController();
        viewController.setCreateLoader(loader);
        viewController.setListLoader(listLoader);

        recipeListController listController = listLoader.getController();
        listController.setCreateLoader(loader);
    }

}