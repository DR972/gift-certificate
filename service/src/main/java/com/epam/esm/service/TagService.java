package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {

    Tag findTagById(long id);

    Tag findTagByName(String name);

    List<Tag> findAllTags();

    Tag createTag(Tag tag);

    Tag updateTag(Tag tag, long id);

    void deleteTag(long id);
}
