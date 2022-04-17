package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * The interface {@code TagService} describes abstract behavior for working with {@link com.epam.esm.service.impl.TagServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface TagService {

    /**
     * Provides logic for searching Tag by id.
     *
     * @param id Tag id
     * @return Tag object
     */
    Tag findTagById(long id);

    /**
     * Provides logic for searching Tag by name.
     *
     * @param name Tag name
     * @return Tag object
     */
    Tag findTagByName(String name);

    /**
     * Provides logic for searching for all Tags objects.
     *
     * @return list of Tag objects
     */
    List<Tag> findAllTags();

    /**
     * Provides logic for saving Tag in the database.
     *
     * @param tag Tag
     * @return Tag object
     */
    Tag createTag(Tag tag);

    /**
     * Provides logic for updating Tag in the database.
     *
     * @param tag new Tag parameters
     * @param id  Tag id
     * @return Tag object
     */
    Tag updateTag(Tag tag, long id);

    /**
     * Provides logic for deleting Tag in the database.
     *
     * @param id Tag id
     */
    void deleteTag(long id);
}
