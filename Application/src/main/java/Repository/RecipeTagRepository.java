package Repository;

import Model.RecipeTag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeTagRepository {
    private final DatabaseConnection db;

    public RecipeTagRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void createTable()  {
        String createTable = "CREATE TABLE IF NOT EXISTS recipeTags (" +
                "recipeTagID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeID INTEGER, " +
                "tagID INTEGER " +
                ")";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertTag(RecipeTag recipeTag)  {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String insert = "INSERT INTO recipeTags (recipeID, tagID) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert); {
                preparedStatement.setInt(1, recipeTag.getRecipeID());
                preparedStatement.setInt(2, recipeTag.getTagID());
                preparedStatement.executeUpdate();
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

    public RecipeTag getTagById(int tagId)  {
        String query = "SELECT * FROM recipeTags WHERE recipeTagID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tagId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                RecipeTag tag = new RecipeTag();
                tag.setRecipeTagID(resultSet.getInt("recipeTagID"));
                tag.setRecipeID(resultSet.getInt("recipeID"));
                tag.setTagID(resultSet.getInt("tagID"));
                return tag;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<RecipeTag> getTagsByRecipeId(int recipeID)  {
        String query = "SELECT * FROM recipeTags WHERE recipeID = ?";
        List<RecipeTag> tags = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RecipeTag tag = new RecipeTag();
                tag.setRecipeTagID(resultSet.getInt("recipeTagID"));
                tag.setRecipeID(resultSet.getInt("recipeID"));
                tag.setTagID(resultSet.getInt("tagID"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tags;
    }

    public List<RecipeTag> getAllTags()  {
        String query = "SELECT * FROM recipeTags";
        List<RecipeTag> tags = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                RecipeTag tag = new RecipeTag();
                tag.setRecipeTagID(resultSet.getInt("recipeTagID"));
                tag.setRecipeID(resultSet.getInt("recipeID"));
                tag.setTagID(resultSet.getInt("tagID"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }

    public void updateTag(RecipeTag tag)  {
        String update = "UPDATE recipeTags SET recipeID = ?, tagID = ? WHERE recipeTagID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, tag.getRecipeID());
            preparedStatement.setInt(2, tag.getTagID());

            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();

            if (rowsUpdated == 0) {
                System.out.println("Update failed: No record found with tagId = " + tag.getRecipeTagID());
            } else {
                System.out.println("Update successful. Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTag(int tagId)  {
        String delete = "DELETE FROM recipeTags WHERE recipeTagID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, tagId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
