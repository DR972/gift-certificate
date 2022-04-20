package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.converter.impl.TagDtoConverter;
import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    /**
     * TagDao tagDao.
     */
    private final TagDao tagDao;
    /**
     * GiftCertificateTagDao certificateTagDao.
     */
    private final GiftCertificateTagDao certificateTagDao;
    /**
     * TagDtoConverter tagDtoConverter.
     */
    private final TagDtoConverter tagDtoConverter;

    /**
     * The constructor creates a TagServiceImpl object
     *
     * @param tagDao            TagDao tagDao
     * @param certificateTagDao GiftCertificateTagDao certificateTagDao
     * @param tagDtoConverter   TagDtoConverter tagDtoConverter
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateTagDao certificateTagDao, TagDtoConverter tagDtoConverter) {
        this.tagDao = tagDao;
        this.certificateTagDao = certificateTagDao;
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    public TagDto findTagById(long id) {
        return tagDtoConverter.convertToDto(tagDao.findEntity(FIND_TAG_BY_ID, id).orElseThrow(() ->
                new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")")));
    }

    @Override
    public Tag findTagByName(String name) {
        return tagDao.findEntity(FIND_TAG_BY_NAME, name).orElse(new Tag());
    }

    @Override
    public List<TagDto> findAllTags() {
        return tagDao.findListEntities(FIND_ALL_TAGS).stream().map(tagDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = tagDtoConverter.convertToEntity(tagDto);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName() + ")");
        }
        long key = tagDao.createEntity(CREATE_TAG, tag.getName());
        return findTagById(key);
    }

    @Override
    public TagDto updateTag(TagDto tagDto, long id) {
        Tag tag = tagDtoConverter.convertToEntity(tagDto);
        if (findTagByName(tag.getName()).getName() != null) {
            throw new DuplicateEntityException("ex.duplicate", tag.getName() + ")");
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
