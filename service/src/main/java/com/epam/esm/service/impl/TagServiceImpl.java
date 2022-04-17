package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.SqlQuery.FIND_TAG_BY_ID;
import static com.epam.esm.util.SqlQuery.FIND_TAG_BY_NAME;
import static com.epam.esm.util.SqlQuery.FIND_ALL_TAGS;
import static com.epam.esm.util.SqlQuery.CREATE_TAG;
import static com.epam.esm.util.SqlQuery.UPDATE_TAG;
import static com.epam.esm.util.SqlQuery.DELETE_CERTIFICATE_TAG_BY_TAG_ID;
import static com.epam.esm.util.SqlQuery.DELETE_TAG;

/**
 * The class {@code TagServiceImpl} is implementation of interface {@link TagService} and provides logic to work with {@link com.epam.esm.entity.Tag}.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final GiftCertificateTagDao certificateTagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateTagDao certificateTagDao) {
        this.tagDao = tagDao;
        this.certificateTagDao = certificateTagDao;
    }

    @Override
    public Tag findTagById(long id) {
        return tagDao.findEntity(FIND_TAG_BY_ID, id).orElseThrow(() -> new NoSuchEntityException("There is no tag with this ID = " + id));
    }

    @Override
    public Tag findTagByName(String name) {
        return tagDao.findEntity(FIND_TAG_BY_NAME, name).orElse(new Tag());
    }

    @Override
    public List<Tag> findAllTags() {
        return tagDao.findListEntities(FIND_ALL_TAGS);
    }

    @Override
    public Tag createTag(Tag tag) {
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("A tag named '" + tag.getName() + "' already exists");
        }
        long key = tagDao.createEntity(CREATE_TAG, tag.getName());
        return findTagById(key);
    }

    @Override
    public Tag updateTag(Tag tag, long id) {
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("A tag named '" + tag.getName() + "' already exists");
        }
        findTagById(id);
        tagDao.updateEntity(UPDATE_TAG, tag.getName(), id);
        return findTagById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(long id) {
        findTagById(id);
        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_TAG_ID, id);
        tagDao.updateEntity(DELETE_TAG, id);
    }
}
