package com.epam.esm.controller;

import com.epam.esm.service.dto.TagDto;
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
     * Method for getting TagDto by ID.
     *
     * @param id TagDto id
     * @return TagDto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable long id) {
        return tagService.findTagById(id);
    }


    /**
     * Method for getting list of all TagDto objects.
     *
     * @return list of TagDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTagList() {
        return tagService.findAllTags();
    }

    /**
     * Method for saving new Tag.
     * Annotated by {@link Validated} with parameters TagDto.OnCreate.class provides validation of the fields of the TagDto object when creating.
     *
     * @param tagDto TagDto
     * @return created TagDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@Validated(TagDto.OnCreate.class) @RequestBody TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    /**
     * Method for updating Tag.
     * Annotated by {@link Validated} with parameters TagDto.OnCreate.class provides validation of the fields of the TagDto object when updating.
     *
     * @param tagDto new TagDto parameters
     * @param id     TagDto id
     * @return updated TagDto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto updateTag(@PathVariable long id, @Validated(TagDto.OnCreate.class) @RequestBody TagDto tagDto) {
        return tagService.updateTag(tagDto, id);
    }

    /**
     * Method for removing Tag by ID.
     *
     * @param id TagDto id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }
}
