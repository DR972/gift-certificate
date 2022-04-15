package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(@PathVariable long id) {
        return tagService.findTagById(id);
    }


    @GetMapping("/allTags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTagList() {
        return tagService.findAllTags();
    }

    @PostMapping("/createTag")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@Validated(Tag.OnCreate.class) @RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @PatchMapping("/updateTag/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(@PathVariable long id, @Validated(Tag.OnCreate.class) @RequestBody Tag tag) {
        return tagService.updateTag(tag, id);
    }

    @DeleteMapping("/deleteTag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }
}
