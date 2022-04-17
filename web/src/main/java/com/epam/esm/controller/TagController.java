package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * Class {@code TagController} is an endpoint of the API which allows you to perform CRUD operations on tags.
 * Annotated by {@link RestController} without parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags".
 * Annotated by {@link Validated} without parameters  provides checking of constraints in method parameters.
 * So that {@code TagController} is accessed by sending request to /tags.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Method for getting Tag by ID.
     *
     * @param id Tag id
     * @return Tag
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(@PathVariable long id) {
        return tagService.findTagById(id);
    }


    /**
     * Method for getting list of all Tags.
     *
     * @return list of Tags
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTagList() {
        return tagService.findAllTags();
    }

    /**
     * Method for saving new Tag.
     * Annotated by {@link Validated} with parameters Tag.OnCreate.class provides validation of the fields of the Tag object when creating.
     *
     * @param tag Tag
     * @return created Tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@Validated(Tag.OnCreate.class) @RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    /**
     * Method for updating Tag.
     * Annotated by {@link Validated} with parameters Tag.OnCreate.class provides validation of the fields of the Tag object when updating.
     *
     * @param tag new Tag parameters
     * @param id  Tag id
     * @return updated Tag
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(@PathVariable long id, @Validated(Tag.OnCreate.class) @RequestBody Tag tag) {
        return tagService.updateTag(tag, id);
    }

    /**
     * Method for removing Tag by ID.
     *
     * @param id Tag id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }
}
