package com.epam.esm.controller;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Tag> getTagById(@PathVariable long id) {
        Tag tag = tagService.findTagById(id);
        if (tag == null) {
            throw new NoSuchEntityException("There is no tag with this ID = " + id);
        }
        return ResponseEntity.status(OK).body(tag);
    }


    @GetMapping("/allTags")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Tag>> getTagList() {
        return ResponseEntity.status(OK).body(tagService.findAllTags());
    }

    @PostMapping("/createTag")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tag> createTag(@Validated(Tag.OnCreate.class) @RequestBody Tag tag) {
        if (tagService.findTagByName(tag.getName()) != null) {
            throw new DuplicateEntityException("There is already a tag named " + tag.getName() + " in the database");
        }
        return ResponseEntity.status(OK).body(tagService.createTag(tag));
    }

    @PatchMapping("/updateTag/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Tag> updateTag(@PathVariable long id, @Validated(Tag.OnCreate.class) @RequestBody Tag tag) {
        if (tagService.findTagByName(tag.getName()) != null) {
            throw new DuplicateEntityException("There is already a tag named " + tag.getName() + " in the database");
        }
        if (tagService.findTagById(tag.getId()) == null) {
            throw new NoSuchEntityException("There is no tag with this ID = " + id);
        }
        return ResponseEntity.status(OK).body(tagService.updateTag(tag, id));
    }

    @DeleteMapping("/deleteTag/{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Object> deleteTag(@PathVariable long id) {
        if (tagService.findTagById(id) == null) {
            throw new NoSuchEntityException("There is no tag with this ID = " + id);
        }
        tagService.deleteTag(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
