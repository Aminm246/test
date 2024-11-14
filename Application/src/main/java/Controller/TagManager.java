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

    public int addTag(String tagName)  {
        Tag tag = new Tag(tagName);
        int tagId = tagRepository.insertTag(tag);
        if (tagId != -1) {
            System.out.println("Tag created with ID: " + tagId);
        } else {
            System.out.println("Failed to create tag.");
        }
        return tagId;
    }

    public Tag getTagById(int tagId)  {
        return tagRepository.getTagById(tagId);
    }

    public List<Tag> getAllTags()  {
        return tagRepository.getAllTags();
    }
}
