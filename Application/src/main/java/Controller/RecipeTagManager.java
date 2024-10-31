package Controller;

import Model.RecipeTag;
import Repository.RecipeTagRepository;

import java.sql.SQLException;
import java.util.List;

public class RecipeTagManager {
    private final RecipeTagRepository recipeTagRepository;

    public RecipeTagManager(RecipeTagRepository recipeTagRepository) {
        this.recipeTagRepository = recipeTagRepository;
    }

    public int addTag(int recipeID, int tagID) throws SQLException {
        RecipeTag recipeTag = new RecipeTag(recipeID, tagID);
        int tagId = recipeTagRepository.insertTag(recipeTag);
        if (tagId != -1) {
            System.out.println("Tag created with ID: " + tagId);
        } else {
            System.out.println("Failed to create tag.");
        }
        return tagId;
    }

    public RecipeTag getTagById(int tagId) throws SQLException {
        return recipeTagRepository.getTagById(tagId);
    }

    public List<RecipeTag> getAllTags() throws SQLException {
        return recipeTagRepository.getAllTags();
    }
}
