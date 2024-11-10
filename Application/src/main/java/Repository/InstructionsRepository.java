package Repository;

import Model.Ingredient;
import Model.InstructionStep;
import Model.RecipeIngredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionsRepository {
    private final DatabaseConnection db;

    public InstructionsRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void createTable() throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS instructions (" +
                "instructionStepID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeID INTEGER, " +
                "stepNum INTEGER, " +
                "stepDescription TEXT" +
                ")";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        }
    }

    public int insertInstruction(InstructionStep instructionStep) throws SQLException {
        Connection connection = db.getConnection();
        String insert = "INSERT INTO instructions (recipeID, stepNum, stepDescription, instructionStepID) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert); {
            preparedStatement.setInt(1, instructionStep.getRecipeID());
            preparedStatement.setInt(2, instructionStep.getStepNum());
            preparedStatement.setString(3, instructionStep.getStepDescription());
            preparedStatement.setInt(4,instructionStep.getInstructionStepID());
            preparedStatement.executeUpdate();
        }
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }
        return -1;
    }

    public InstructionStep getInstructionById(int instructionId) throws SQLException {
        String query = "SELECT * FROM instructions WHERE instructionStepID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, instructionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                InstructionStep instructionStep = new InstructionStep();
                instructionStep.setRecipeID(resultSet.getInt("recipeID"));
                instructionStep.setStepNum(resultSet.getInt("stepNum"));
                instructionStep.setStepDescription(resultSet.getString("stepDescription"));
                return instructionStep;
            }
        }
        return null;
    }

    public List<InstructionStep> getInstructionsByRecipeId(int recipeId) throws SQLException {
        String query = "SELECT * FROM instructions WHERE recipeID = ?";
        List<InstructionStep> instructions = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InstructionStep instructionStep = new InstructionStep();
                instructionStep.setRecipeID(resultSet.getInt("recipeID"));
                instructionStep.setStepNum(resultSet.getInt("stepNum"));
                instructionStep.setStepDescription(resultSet.getString("stepDescription"));
                instructions.add(instructionStep);
            }
        }
        return instructions;
    }

    public List<InstructionStep> getAllInstructions() throws SQLException {
        String query = "SELECT * FROM instructions";
        List<InstructionStep> instructions = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                InstructionStep instructionStep = new InstructionStep();
                instructionStep.setRecipeID(resultSet.getInt("recipeID"));
                instructionStep.setStepNum(resultSet.getInt("stepNum"));
                instructionStep.setStepDescription(resultSet.getString("stepDescription"));
                instructions.add(instructionStep);
            }
        }
        return instructions;
    }

    public void updateInstruction(InstructionStep instructionStep) throws SQLException {
        String update = "UPDATE instructions SET stepNum = ?, stepDescription = ?, recipeID = ? WHERE instructionStepID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, instructionStep.getStepNum());
            preparedStatement.setString(2, instructionStep.getStepDescription());
            preparedStatement.setInt(3, instructionStep.getRecipeID());

            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();

            if (rowsUpdated == 0) {
                System.out.println("Update failed: No record found with instructionId = " + instructionStep.getInstructionStepID());
            } else {
                System.out.println("Update successful. Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInstruction(int instructionID) throws SQLException {
        String delete = "DELETE FROM instructions WHERE instructionStepID = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setInt(1, instructionID);
            preparedStatement.executeUpdate();
        }
    }
}
