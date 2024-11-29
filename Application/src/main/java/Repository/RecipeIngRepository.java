package Repository;

import Model.Ingredient;
import Model.RecipeIngredient;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngRepository {
    private final DatabaseConnection db;

    public RecipeIngRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void createTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS recipeIng (" +
                "recipeIngredientID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeID INTEGER, " +
                "ingredientID INTEGER, " +
                "measurementUnit TEXT, " +
                "quantity TEXT" +
                ")";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            statement.executeUpdate(createTable);
            System.out.println("RECIPE ING TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertIngredient(RecipeIngredient ingredient){
        Connection connection = null;
        try {
            connection = db.getConnection();
            String insert = "INSERT INTO recipeIng (ingredientID, recipeId, measurementUnit, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert); {
                preparedStatement.setInt(1, ingredient.getIngredientID());
                preparedStatement.setInt(2, ingredient.getRecipeID());
                preparedStatement.setString(3, ingredient.getMeasurementUnit());
                preparedStatement.setString(4, ingredient.getQuantity().toPlainString());
                preparedStatement.executeUpdate();
                System.out.println("RecipeIngredient Inserted");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public RecipeIngredient getIngredientById(int ingredientId){
        String query = "SELECT * FROM recipeIng WHERE recipeIngredientID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ingredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                RecipeIngredient ingredient = new RecipeIngredient();
                ingredient.setRecipeIngredientID(resultSet.getInt("recipeIngredientID"));
                ingredient.setRecipeID(resultSet.getInt("recipeID"));
                ingredient.setIngredientID(resultSet.getInt("ingredientID"));
                ingredient.setMeasurementUnit(resultSet.getString("measurementUnit"));
                ingredient.setQuantity(resultSet.getBigDecimal("quantity"));
                return ingredient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<RecipeIngredient> getAllIngredients(){
        String query = "SELECT * FROM recipeIng";
        List<RecipeIngredient> ingredients = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                RecipeIngredient ingredient = new RecipeIngredient();
                ingredient.setRecipeIngredientID(resultSet.getInt("recipeIngredientID"));
                ingredient.setRecipeID(resultSet.getInt("recipeID"));
                ingredient.setIngredientID(resultSet.getInt("ingredientID"));
                ingredient.setMeasurementUnit(resultSet.getString("measurementUnit"));
                ingredient.setQuantity(resultSet.getBigDecimal("quantity"));
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public List<RecipeIngredient> getIngredientsByRecipeId(int recipeId) {
        String query = "SELECT * FROM recipeIng WHERE recipeID = ?";
        List<RecipeIngredient> ingredients = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RecipeIngredient ingredient = new RecipeIngredient();
                ingredient.setRecipeIngredientID(resultSet.getInt("recipeIngredientID"));
                ingredient.setRecipeID(resultSet.getInt("recipeID"));
                ingredient.setIngredientID(resultSet.getInt("ingredientID"));
                ingredient.setMeasurementUnit(resultSet.getString("measurementUnit"));
                ingredient.setQuantity(resultSet.getBigDecimal("quantity"));
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredients;
    }


    public void updateIngredient(RecipeIngredient ingredient){
        String update = "UPDATE recipeIng SET measurementUnit = ?, quantity = ? WHERE recipeIngredientID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, ingredient.getMeasurementUnit());
            preparedStatement.setString(2, ingredient.getQuantity().toPlainString());
            preparedStatement.setInt(3,ingredient.getRecipeIngredientID());

            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();

            if (rowsUpdated == 0) {
                System.out.println("Update failed: No record found with recipeIngredientID = " + ingredient.getRecipeIngredientID());
            } else {
                System.out.println("Update successful. Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIngredient(int ingredientId) {
        String delete = "DELETE FROM recipeIng WHERE recipeIngredientID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, ingredientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
