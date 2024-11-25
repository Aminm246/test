package Repository;
//test commit
import Model.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientsRepository {
    private final DatabaseConnection db;

    public IngredientsRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void createTable(){
        String createTable = "CREATE TABLE IF NOT EXISTS ingredients (" +
                "ingredientId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ingredientName TEXT" +
                ")";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertIngredient(Ingredient ingredient){
        Connection connection = null;
        try {
            connection = db.getConnection();
            String insert = "INSERT INTO ingredients (ingredientName) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert); {
                preparedStatement.setString(1, ingredient.getIngredientName());
                preparedStatement.executeUpdate();
                System.out.println("Ingredient Inserted");
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

    public Ingredient getIngredientsById(int ingredientId) {
        String query = "SELECT * FROM ingredients WHERE ingredientId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ingredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                ingredient.setIngredientName(resultSet.getString("ingredientName"));
                return ingredient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Ingredient> getAllIngredients() {
        String query = "SELECT * FROM ingredients";
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                ingredient.setIngredientName(resultSet.getString("ingredientName"));

                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public void updateIngredient(Ingredient ingredient) {
        String update = "UPDATE ingredients SET ingredientName = ? WHERE ingredientId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, ingredient.getIngredientName());
            preparedStatement.setInt(2,ingredient.getIngredientId());
            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();

            if (rowsUpdated == 0) {
                System.out.println("Update failed: No record found with ingredientId = " + ingredient.getIngredientId());
            } else {
                System.out.println("Update successful. Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIngredient(int ingredientId){
        String delete = "DELETE FROM ingredients WHERE ingredientId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, ingredientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
