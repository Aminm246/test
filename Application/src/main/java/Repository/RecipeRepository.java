package Repository;

import Model.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private final DatabaseConnection db;

    public RecipeRepository(DatabaseConnection db) {
        this.db = db;
    }

   public void createTable()  {
            String createTable = "CREATE TABLE IF NOT EXISTS recipes (" +
                    "recipeId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "recipeName TEXT, " +
                    "createdBy INTEGER, " +
                "servingSize INTEGER, " +
                "imagePath TEXT, " +
                "description TEXT, " +
                "duration INTEGER" +
                ")";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   }

    public int insertRecipe(Recipe recipe)  {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String insert = "INSERT INTO recipes (recipeName, createdBy, servingSize, imagePath, description, duration) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert); {
                preparedStatement.setString(1, recipe.getRecipeName());
                preparedStatement.setInt(2, recipe.getCreatedBy());
                preparedStatement.setInt(3, recipe.getServingSize());
                preparedStatement.setString(4, recipe.getImagePath());
                preparedStatement.setString(5, recipe.getDescription());
                preparedStatement.setInt(6, recipe.getDuration());
                preparedStatement.executeUpdate();
                System.out.println("Recipe Inserted");
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





    public Recipe getRecipeById(int recipeId)  {
        String query = "SELECT * FROM recipes WHERE recipeId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setRecipeId(resultSet.getInt("recipeId"));
                recipe.setRecipeName(resultSet.getString("recipeName"));
                recipe.setCreatedBy(resultSet.getInt("createdBy"));
                recipe.setServingSize(resultSet.getInt("servingSize"));
                recipe.setImagePath(resultSet.getString("imagePath"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setDuration(resultSet.getInt("duration"));

                return recipe;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Recipe> getAllRecipes()  {
        String query = "SELECT * FROM recipes";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setRecipeId(resultSet.getInt("recipeId"));
                recipe.setRecipeName(resultSet.getString("recipeName"));
                recipe.setCreatedBy(resultSet.getInt("createdBy"));
                recipe.setServingSize(resultSet.getInt("servingSize"));
                recipe.setImagePath(resultSet.getString("imagePath"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setDuration(resultSet.getInt("duration"));

                recipes.add(recipe);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }

    public void updateRecipe(Recipe recipe)  {
        String update = "UPDATE recipes SET recipeName = ?, createdBy = ?, servingSize = ?, imagePath = ?, description = ?, duration = ? WHERE recipeId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, recipe.getRecipeName());
            preparedStatement.setInt(2, recipe.getCreatedBy());
            preparedStatement.setInt(3, recipe.getServingSize());
            preparedStatement.setString(4, recipe.getImagePath());
            preparedStatement.setString(5, recipe.getDescription());
            preparedStatement.setInt(6, recipe.getDuration());
            preparedStatement.setInt(7, recipe.getRecipeId());

            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();

            if (rowsUpdated == 0) {
                System.out.println("Update failed: No record found with recipeId = " + recipe.getRecipeId());
            } else {
                System.out.println("Update successful. Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(int recipeId)  {
        String delete = "DELETE FROM recipes WHERE recipeId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
