package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.converter.impl.TagDtoConverter;
import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.epam.esm.service.util.SqlQueryTest.FIND_TAG_BY_ID;
import static com.epam.esm.service.util.SqlQueryTest.NEW;
import static com.epam.esm.service.util.SqlQueryTest.FIND_TAG_BY_NAME;
import static com.epam.esm.service.util.SqlQueryTest.REST;
import static com.epam.esm.service.util.SqlQueryTest.FIND_ALL_TAGS;
import static com.epam.esm.service.util.SqlQueryTest.CREATE_TAG;
import static com.epam.esm.service.util.SqlQueryTest.UPDATE_TAG;
import static com.epam.esm.service.util.SqlQueryTest.DELETE_CERTIFICATE_TAG_BY_TAG_ID;
import static com.epam.esm.service.util.SqlQueryTest.DELETE_TAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private static final Tag TAG_1 = new Tag(1, "rest");
    private static final Tag TAG_2 = new Tag(2, "nature");
    private static final Tag TAG_3 = new Tag(3, "shopping");
    private static final Tag NEW_TAG = new Tag(0, NEW);
    private static final TagDto TAG_DTO_1 = new TagDto(1, "rest");
    private static final TagDto TAG_DTO_2 = new TagDto(2, "nature");
    private static final TagDto TAG_DTO_3 = new TagDto(3, "shopping");
    private static final TagDto NEW_DTO_TAG = new TagDto(0, NEW);

    @Mock
    private TagDao tagDao = mock(TagDao.class);
    @Mock
    private GiftCertificateTagDao certificateTagDao = mock(GiftCertificateTagDao.class);
    @Mock
    private TagDtoConverter tagDtoConverter = mock(TagDtoConverter.class);
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void findTagByIdShouldReturnResult() {
        when(tagDtoConverter.convertToDto(TAG_2)).thenReturn(TAG_DTO_2);
        when(tagDao.findEntity(FIND_TAG_BY_ID, 2L)).thenReturn(Optional.of(TAG_2));
        tagService.findTagById(2);
        verify(tagDao, times(1)).findEntity(FIND_TAG_BY_ID, 2L);
        assertEquals(TAG_DTO_2, tagService.findTagById(2));
    }

    @Test
    void findTagByIdShouldThrowException() {
        when(tagDao.findEntity(FIND_TAG_BY_ID, 2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> tagService.findTagById(2));
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void findTagByNameShouldReturnResult() {
        when(tagDao.findEntity(FIND_TAG_BY_NAME, REST)).thenReturn(Optional.of(TAG_1));
        tagService.findTagByName(REST);
        verify(tagDao, times(1)).findEntity(FIND_TAG_BY_NAME, REST);
        assertEquals(TAG_1, tagService.findTagByName(REST));
    }

    @Test
    void findAllTagsShouldReturnResult() {
        when(tagDtoConverter.convertToDto(TAG_1)).thenReturn(TAG_DTO_1);
        when(tagDtoConverter.convertToDto(TAG_2)).thenReturn(TAG_DTO_2);
        when(tagDtoConverter.convertToDto(TAG_3)).thenReturn(TAG_DTO_3);
        when(tagDao.findListEntities(FIND_ALL_TAGS)).thenReturn(Arrays.asList(TAG_1, TAG_2, TAG_3));
        tagService.findAllTags();
        verify(tagDao, times(1)).findListEntities(FIND_ALL_TAGS);
        assertEquals(Arrays.asList(TAG_DTO_1, TAG_DTO_2, TAG_DTO_3), tagService.findAllTags());
    }

    @Test
    void createTagShouldReturnResult() {
        when(tagDtoConverter.convertToEntity(NEW_DTO_TAG)).thenReturn(NEW_TAG);
        when(tagDtoConverter.convertToDto(NEW_TAG)).thenReturn(NEW_DTO_TAG);
        when(tagDao.findEntity(FIND_TAG_BY_NAME, NEW)).thenReturn(Optional.of(new Tag()));
        when(tagDao.findEntity(FIND_TAG_BY_ID, 0L)).thenReturn(Optional.of(NEW_TAG));
        tagService.createTag(NEW_DTO_TAG);
        verify(tagDao, times(1)).createEntity(CREATE_TAG, NEW);
        assertEquals(NEW_DTO_TAG, tagService.createTag(NEW_DTO_TAG));
    }

    @Test
    void createTagShouldThrowException() {
        when(tagDtoConverter.convertToEntity(TAG_DTO_1)).thenReturn(TAG_1);
        when(tagDao.findEntity(FIND_TAG_BY_NAME, REST)).thenReturn(Optional.of(TAG_1));
        Exception exception = assertThrows(DuplicateEntityException.class, () -> tagService.createTag(TAG_DTO_1));
        assertTrue(exception.getMessage().contains("ex.duplicate"));
    }

    @Test
    void updateTagShouldReturnResult() {
        when(tagDtoConverter.convertToEntity(NEW_DTO_TAG)).thenReturn(NEW_TAG);
        when(tagDtoConverter.convertToDto(TAG_3)).thenReturn(NEW_DTO_TAG);
        when(tagDao.findEntity(FIND_TAG_BY_NAME, NEW)).thenReturn(Optional.of(new Tag()));
        when(tagDao.findEntity(FIND_TAG_BY_ID, 3L)).thenReturn(Optional.of(TAG_3));
        tagService.updateTag(NEW_DTO_TAG, 3L);
        verify(tagDao, times(1)).updateEntity(UPDATE_TAG, NEW, 3L);
        assertEquals(NEW_DTO_TAG, tagService.updateTag(NEW_DTO_TAG, 3L));
    }

    @Test
    void updateTagShouldThrowDuplicateEntityException() {
        when(tagDtoConverter.convertToEntity(TAG_DTO_1)).thenReturn(TAG_1);
        when(tagDao.findEntity(FIND_TAG_BY_NAME, REST)).thenReturn(Optional.of(TAG_1));
        Exception exception = assertThrows(DuplicateEntityException.class, () -> tagService.updateTag(TAG_DTO_1, TAG_2.getId()));
        assertTrue(exception.getMessage().contains("ex.duplicate"));
    }

    @Test
    void updateTagShouldThrowNoSuchEntityException() {
        when(tagDtoConverter.convertToEntity(TAG_DTO_1)).thenReturn(TAG_1);
        when(tagDao.findEntity(FIND_TAG_BY_NAME, TAG_1.getName())).thenReturn(Optional.of(new Tag()));
        when(tagDao.findEntity(FIND_TAG_BY_ID, 2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> tagService.updateTag(TAG_DTO_1, TAG_2.getId()));
        assertTrue(exception.getMessage().contains("ex.noSuchEntity"));
    }

    @Test
    void deleteTagTest() {
        when(tagDao.findEntity(FIND_TAG_BY_ID, 3L)).thenReturn(Optional.of(TAG_3));
        tagService.deleteTag(3);
        verify(certificateTagDao, times(1)).updateEntity(DELETE_CERTIFICATE_TAG_BY_TAG_ID, 3L);
        verify(tagDao, times(1)).updateEntity(DELETE_TAG, 3L);
    }

    @Test
    void deleteTagShouldThrowNoSuchEntityException() {
        when(tagDao.findEntity(FIND_TAG_BY_ID, 2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> tagService.deleteTag(TAG_2.getId()));
        assertTrue(exception.getMessage().contains("noSuchEntity"));
    }
}
