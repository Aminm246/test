package Repository;

import Model.Ingredient;
import Model.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagRepository {
    private final DatabaseConnection db;

    public TagRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void createTable()  {
        String createTable = "CREATE TABLE IF NOT EXISTS tags (" +
                "tagId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tagName TEXT" +
                ")";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertTag(Tag tag)  {
        Connection connection = null;
        try {
            connection = db.getConnection();
            String insert = "INSERT INTO tags (tagName) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert); {
                preparedStatement.setString(1, tag.getTagName());
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

    public Tag getTagById(int tagId)  {
        String query = "SELECT * FROM tags WHERE tagId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tagId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Tag tag = new Tag();
                tag.setTagId(resultSet.getInt("tagId"));
                tag.setTagName(resultSet.getString("tagName"));
                return tag;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Tag> getAllTags()  {
        String query = "SELECT * FROM tags";
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setTagId(resultSet.getInt("tagId"));
                tag.setTagName(resultSet.getString("tagName"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }

    public void updateTag(Tag tag)  {
        String update = "UPDATE tags SET tagName = ? WHERE tagId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, tag.getTagName());
            preparedStatement.setInt(2,tag.getTagId());
            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();

            if (rowsUpdated == 0) {
                System.out.println("Update failed: No record found with tagId = " + tag.getTagId());
            } else {
                System.out.println("Update successful. Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIngredient(int tagId)  {
        String delete = "DELETE FROM tags WHERE tagId = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, tagId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
