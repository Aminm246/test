package Controller;

import Model.RecipeTag;
import Model.Tag;
import Repository.RecipeTagRepository;
import Repository.TagRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeTagManager {
    private final RecipeTagRepository recipeTagRepository;
    private TagRepository tagRepository;

    public RecipeTagManager(RecipeTagRepository recipeTagRepository, TagRepository tagRepository) {
        this.recipeTagRepository = recipeTagRepository;
        this.tagRepository = tagRepository;
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

    public List<RecipeTag> getTagsByRecipeId(int recipeID) throws SQLException {
        List<RecipeTag> tags = recipeTagRepository.getTagsByRecipeId(recipeID);
        for (RecipeTag recipeTag : tags) {
            Tag tag = tagRepository.getTagById(recipeTag.getTagID());
            recipeTag.setTag(tag);
        }
        return tags;
    }


    public RecipeTag getTagById(int tagId) throws SQLException {
        RecipeTag recipeTag = recipeTagRepository.getTagById(tagId);
        recipeTag.setTag(tagRepository.getTagById(recipeTag.getTagID()));
        return recipeTag;
    }

    public List<RecipeTag> getAllTags() throws SQLException {
        List<RecipeTag> tags = recipeTagRepository.getAllTags();
        for (RecipeTag tag : tags) {
            tag.setTag(tagRepository.getTagById(tag.getTagID()));
        }
        return tags;
    }
}
