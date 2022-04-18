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
     * The method finds Tag by id.
     *
     * @param id Tag id
     * @return Tag object
     */
    Tag findTagById(long id);

    /**
     * The method finds Tag by name.
     *
     * @param name Tag name
     * @return Tag object
     */
    Tag findTagByName(String name);

    /**
     * The method finds all Tags.
     *
     * @return list of Tag objects
     */
    List<Tag> findAllTags();

    /**
     * The method performs the operation of saving Tag.
     *
     * @param tag Tag
     * @return Tag object
     */
    Tag createTag(Tag tag);

    /**
     * The method performs the operation of updating Tag.
     *
     * @param tag new Tag parameters
     * @param id  Tag id
     * @return Tag object
     */
    Tag updateTag(Tag tag, long id);

    /**
     * The method performs the operation of deleting Tag.
     *
     * @param id Tag id
     */
    void deleteTag(long id);
}
