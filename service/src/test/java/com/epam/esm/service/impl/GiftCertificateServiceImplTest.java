package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.service.util.SqlQueryTest.FIND_CERTIFICATE_BY_ID;
import static com.epam.esm.service.util.SqlQueryTest.FIND_ALL_CERTIFICATES_ORDER_BY_DATE_DESC;
import static com.epam.esm.service.util.SqlQueryTest.VISIT;
import static com.epam.esm.service.util.SqlQueryTest.FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_ORDER_BY_DATE_DESC;
import static com.epam.esm.service.util.SqlQueryTest.REST;
import static com.epam.esm.service.util.SqlQueryTest.FIND_ALL_CERTIFICATES_WITH_SPECIFIC_TAG_ORDER_BY_DATE_DESC;
import static com.epam.esm.service.util.SqlQueryTest.RIDING;
import static com.epam.esm.service.util.SqlQueryTest.FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_WITH_SPECIFIC_TAG_ORDER_BY_DATE_AND_NAME;
import static com.epam.esm.service.util.SqlQueryTest.CREATE_CERTIFICATE;
import static com.epam.esm.service.util.SqlQueryTest.UPDATE_CERTIFICATE_FIELDS_DESCRIPTION_DURATION;
import static com.epam.esm.service.util.SqlQueryTest.DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID;
import static com.epam.esm.service.util.SqlQueryTest.DELETE_CERTIFICATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static com.epam.esm.service.util.ColumnName.LAST_UPDATE_DATE;
import static com.epam.esm.service.util.ColumnName.DESC;
import static com.epam.esm.service.util.ColumnName.NAME;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final String TEXT = "text";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final LocalDateTime DATE_TIME = LocalDateTime.parse("2022-05-01T00:00:00.001");
    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "ATV riding",
            "Description ATV riding", new BigDecimal("100"), 10, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-07T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(4, "atv")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "Horse riding",
            "Horse riding description", new BigDecimal("80"), 8, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-05T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(5, "horse")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "Visiting a restaurant",
            "Visiting the Plaza restaurant", new BigDecimal("50"), 7, LocalDateTime.parse("2022-04-02T10:12:45.123"),
            LocalDateTime.parse("2022-04-02T14:15:13.257"), Arrays.asList(new Tag(8, "food"), new Tag(10, "restaurant"), new Tag(12, "visit")));

    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(4, "Visit to the drama theater",
            "Description visit to the drama theater", new BigDecimal("45"), 2, LocalDateTime.parse("2022-03-30T10:12:45.123"),
            LocalDateTime.parse("2022-04-08T14:15:13.257"), Arrays.asList(new Tag(6, "theater"), new Tag(12, "visit")));

    private static final GiftCertificate GIFT_CERTIFICATE_5 = new GiftCertificate(5, "Shopping at the tool store",
            "Description shopping at the tool store", new BigDecimal("30"), 10, LocalDateTime.parse("2022-03-25T10:12:45.123"),
            LocalDateTime.parse("2022-04-01T14:15:13.257"), Arrays.asList(new Tag(3, "shopping"), new Tag(7, "tool")));

    private static final GiftCertificate GIFT_CERTIFICATE_6 = new GiftCertificate(6, "Shopping at the supermarket",
            "Shopping at Lidl supermarket chain", new BigDecimal("80"), 12, LocalDateTime.parse("2022-04-01T10:12:45.123"),
            LocalDateTime.parse("2022-04-14T14:15:13.257"), Arrays.asList(new Tag(6, "shopping"), new Tag(8, "food"), new Tag(9, "supermarket")));

    private static final GiftCertificate GIFT_CERTIFICATE_7 = new GiftCertificate(7, "Hot air balloon flight",
            "An unforgettable hot air balloon flight", new BigDecimal("150"), 12, LocalDateTime.parse("2022-03-01T10:12:45.123"),
            LocalDateTime.parse("2022-03-14T14:15:13.257"), Arrays.asList(new Tag(1, "rest"), new Tag(2, "nature"), new Tag(11, "flight")));

    private static final GiftCertificate GIFT_CERTIFICATE_8 = new GiftCertificate("new GiftCertificate",
            "new description", new BigDecimal("10"), 10, Arrays.asList(new Tag("rest"), new Tag("nature"), new Tag("new")));

    private static final GiftCertificate GIFT_CERTIFICATE_9 = new GiftCertificate(5, null,
            "Description shopping at the tool store", null, 10, null,
            null, Arrays.asList(new Tag("shopping"), new Tag("tool"), new Tag("new")));

    @Mock
    private GiftCertificateDao certificateDao = mock(GiftCertificateDao.class);
    @Mock
    private TagDao tagDao = mock(TagDao.class);
    @Mock
    private GiftCertificateTagDao certificateTagDao = mock(GiftCertificateTagDao.class);
    @Mock
    private DateHandler dateHandler = mock(DateHandler.class);
    @InjectMocks
    private GiftCertificateServiceImpl certificateServiceImpl;

    @Test
    void findCertificateShouldReturnResult() {
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 2L)).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        certificateServiceImpl.findCertificate(2);
        verify(certificateDao, times(1)).findEntity(FIND_CERTIFICATE_BY_ID, 2L);
        assertEquals(GIFT_CERTIFICATE_2, certificateServiceImpl.findCertificate(2));
    }

    @Test
    void findCertificateShouldThrowException() {
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> certificateServiceImpl.findCertificate(2));
        assertTrue(exception.getMessage().contains("There is no Gift Certificate with this ID = " + 2));
    }

    @Test
    void findListCertificatesShouldReturnResult() {
        testFindListCertificatesShouldReturnResult(Arrays.asList(null, null, Collections.singletonList(LAST_UPDATE_DATE + DESC)),
                Arrays.asList(GIFT_CERTIFICATE_6, GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_5, GIFT_CERTIFICATE_7),
                FIND_ALL_CERTIFICATES_ORDER_BY_DATE_DESC, 5, 0, 5);

        testFindListCertificatesShouldReturnResult(Arrays.asList(Collections.singletonList(VISIT), null, Collections.singletonList(LAST_UPDATE_DATE + DESC)),
                Arrays.asList(GIFT_CERTIFICATE_4, GIFT_CERTIFICATE_3),
                FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_ORDER_BY_DATE_DESC, VISIT, VISIT, 5, 0, 5);

        testFindListCertificatesShouldReturnResult(Arrays.asList(null, Collections.singletonList(REST), Collections.singletonList(LAST_UPDATE_DATE + DESC)),
                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_7),
                FIND_ALL_CERTIFICATES_WITH_SPECIFIC_TAG_ORDER_BY_DATE_DESC, REST, 5, 0, 5);

        testFindListCertificatesShouldReturnResult(Arrays.asList(Collections.singletonList(RIDING), Collections.singletonList(REST),
                        Arrays.asList(LAST_UPDATE_DATE + DESC, NAME)),
                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2),
                FIND_ALL_CERTIFICATES_BY_PART_NAME_OR_DESCRIPTION_WITH_SPECIFIC_TAG_ORDER_BY_DATE_AND_NAME,
                RIDING, RIDING, REST, 5, 0, 5);
    }

    private void testFindListCertificatesShouldReturnResult(List<List<String>> list, List<GiftCertificate> certificates, String query, Object... queryParams) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (list.get(0) != null) {
            params.put(TEXT, list.get(0));
        }
        if (list.get(1) != null) {
            params.put(TAG, list.get(1));
        }
        if (list.get(2) != null) {
            params.put(SORTING, list.get(2));
        }
        when(certificateDao.findListEntities(query, queryParams)).thenReturn(certificates);
        assertEquals(certificates, certificateServiceImpl.findListCertificates(params, 5, 0, 5));
    }

    @Test
    void createCertificateTest() {
        when(dateHandler.getCurrentDate()).thenReturn(DATE_TIME);
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 0L)).thenReturn(Optional.of(GIFT_CERTIFICATE_8));
        certificateServiceImpl.createCertificate(GIFT_CERTIFICATE_8);

        verify(certificateDao, times(1)).createEntity(CREATE_CERTIFICATE,
                GIFT_CERTIFICATE_8.getName(),
                GIFT_CERTIFICATE_8.getDescription(),
                GIFT_CERTIFICATE_8.getPrice(),
                GIFT_CERTIFICATE_8.getDuration(),
                DATE_TIME,
                DATE_TIME);

        verify(tagDao, times(1)).updateEntity(anyString(), any());
        verify(certificateTagDao, times(1)).updateEntity(anyString(), any());
        assertEquals(GIFT_CERTIFICATE_8, certificateServiceImpl.createCertificate(GIFT_CERTIFICATE_8));
    }

    @Test
    void updateCertificateTest() {
        when(dateHandler.getCurrentDate()).thenReturn(DATE_TIME);
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 5L)).thenReturn(Optional.of(GIFT_CERTIFICATE_9));
        certificateServiceImpl.updateCertificate(GIFT_CERTIFICATE_9, 5);

        verify(certificateDao, times(1)).updateEntity(UPDATE_CERTIFICATE_FIELDS_DESCRIPTION_DURATION,
                GIFT_CERTIFICATE_9.getDescription(), GIFT_CERTIFICATE_9.getDuration(), DATE_TIME, 5L);

        verify(tagDao, times(1)).updateEntity(anyString(), any());
        verify(certificateTagDao, times(2)).updateEntity(anyString(), any());
        assertEquals(GIFT_CERTIFICATE_9, certificateServiceImpl.updateCertificate(GIFT_CERTIFICATE_9, 5));
    }

    @Test
    void updateCertificateShouldThrowException() {
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> certificateServiceImpl.updateCertificate(GIFT_CERTIFICATE_5, 2));
        assertTrue(exception.getMessage().contains("There is no Gift Certificate with this ID = " + 2));
    }

    @Test
    void deleteCertificateTest() {
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 3L)).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        certificateServiceImpl.deleteCertificate(3);
        verify(certificateTagDao, times(1)).updateEntity(DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID, 3L);
        verify(certificateDao, times(1)).updateEntity(DELETE_CERTIFICATE, 3L);
    }

    @Test
    void deleteCertificateShouldThrowException() {
        when(certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, 2L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchEntityException.class, () -> certificateServiceImpl.deleteCertificate(2));
        assertTrue(exception.getMessage().contains("There is no Gift Certificate with this ID = " + 2));
    }
}
