package Controller;

import Model.Tag;
import Repository.DatabaseConnection;
import Repository.TagRepository;

import java.sql.SQLException;
import java.util.List;

public class TagManager {
    private final TagRepository tagRepository;

    public TagManager(DatabaseConnection databaseConnection) {
        this.tagRepository = new TagRepository(databaseConnection);
    }

    public int addTag(String tagName) throws SQLException {
        if (tagRepository.tagExists(tagName)) {
            Tag existingTag = tagRepository.getTagByName(tagName);
            System.out.println("Using existing tag with ID: " + existingTag.getTagId());
            return existingTag.getTagId();
        }

        Tag tag = new Tag(tagName);
        int tagId = tagRepository.insertTag(tag);
        System.out.println("Created new tag with ID: " + tagId);
        return tagId;
    }

    public Tag getTagById(int tagId)  {
        return tagRepository.getTagById(tagId);
    }

    public List<Tag> getAllTags()  {
        return tagRepository.getAllTags();
    }
}
