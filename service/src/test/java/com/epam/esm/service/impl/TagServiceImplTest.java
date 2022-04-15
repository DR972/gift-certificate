package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.epam.esm.service.util.SqlQueryTest.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @Mock
    private TagDao tagDao = mock(TagDao.class);
    @Mock
    private GiftCertificateTagDao certificateTagDao = mock(GiftCertificateTagDao.class);

    @InjectMocks
    private TagServiceImpl tagService;

    private static final Tag TAG_1 = new Tag(12, "rest");
    private static final Tag TAG_2 = new Tag(2, "nature");
    private static final Tag TAG_3 = new Tag(3, "shopping");
    private static final Tag NEW_TAG = new Tag(0, NEW);

    @Test
    void findTagById() {
        when(tagDao.findEntity(FIND_TAG_BY_ID, 2L)).thenReturn(Optional.of(TAG_2));
        tagService.findTagById(2L);
        verify(tagDao, times(1)).findEntity(FIND_TAG_BY_ID, 2L);
    }

    @Test
    void findTagByName() {
        when(tagDao.findEntity(FIND_TAG_BY_NAME, REST)).thenReturn(Optional.of(TAG_1));
        tagService.findTagByName(REST);
        verify(tagDao, times(1)).findEntity(FIND_TAG_BY_NAME, REST);
    }

    @Test
    void findAllTags() {
        tagService.findAllTags();
        verify(tagDao, times(1)).findListEntities(FIND_ALL_TAGS);
    }

    @Test
    void createTag() {
        when(tagDao.findEntity(FIND_TAG_BY_NAME, NEW)).thenReturn(Optional.of(new Tag()));
        when(tagDao.findEntity(FIND_TAG_BY_ID, 0L)).thenReturn(Optional.of(NEW_TAG));
        tagService.createTag(NEW_TAG);
        verify(tagDao, times(1)).createEntity(CREATE_TAG, NEW);
    }

    @Test
    void updateTag() {
        when(tagDao.findEntity(FIND_TAG_BY_NAME, NEW)).thenReturn(Optional.of(new Tag()));
        when(tagDao.findEntity(FIND_TAG_BY_ID, 3L)).thenReturn(Optional.of(TAG_3));
        tagService.updateTag(NEW_TAG, 3L);
        verify(tagDao, times(1)).updateEntity(UPDATE_TAG, NEW, 3L);
    }

    @Test
    void deleteTag() {
        when(tagDao.findEntity(FIND_TAG_BY_ID, 3L)).thenReturn(Optional.of(TAG_3));
        tagService.deleteTag(3);
        verify(certificateTagDao, times(1)).updateEntity(DELETE_CERTIFICATE_TAG_BY_TAG_ID, 3L);
        verify(tagDao, times(1)).updateEntity(DELETE_TAG, 3L);
    }
}
