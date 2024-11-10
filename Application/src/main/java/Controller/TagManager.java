package Controller;

import Model.Tag;
import Repository.TagRepository;

import java.sql.SQLException;
import java.util.List;

public class TagManager {
    private final TagRepository tagRepository;

    public TagManager(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public int addTag(String tagName) throws SQLException {
        Tag tag = new Tag(tagName);
        int tagId = tagRepository.insertTag(tag);
        if (tagId != -1) {
            System.out.println("Tag created with ID: " + tagId);
        } else {
            System.out.println("Failed to create tag.");
        }
        return tagId;
    }

    public Tag getTagById(int tagId) throws SQLException {
        return tagRepository.getTagById(tagId);
    }

    public List<Tag> getAllTags() throws SQLException {
        return tagRepository.getAllTags();
    }
}
