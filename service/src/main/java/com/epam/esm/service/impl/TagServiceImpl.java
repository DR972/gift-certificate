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

import java.util.*;

import static com.epam.esm.util.SqlQuery.*;

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
        return tagDao.findEntity(FIND_TAG_BY_NAME, name).orElseThrow(() -> new DuplicateEntityException("There is no tag with this name = " + name));
    }

    @Override
    public List<Tag> findAllTags() {
        return tagDao.findListEntities(FIND_ALL_TAGS);
    }

    @Override
    public Tag createTag(Tag tag) {
        findTagByName(tag.getName());
        long key = tagDao.createEntity(CREATE_TAG, tag.getName());
        return findTagById(key);
    }

    @Override
    public Tag updateTag(Tag tag, long id) {
        findTagByName(tag.getName());
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
