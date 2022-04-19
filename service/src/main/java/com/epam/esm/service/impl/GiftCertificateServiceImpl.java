package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.NoSuchEntityException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.epam.esm.util.SqlQuery.FIND_ALL_CERTIFICATES;
import static com.epam.esm.util.SqlQuery.FIND_CERTIFICATE_BY_ID;
import static com.epam.esm.util.SqlQuery.SEARCH_BY_TEXT;
import static com.epam.esm.util.SqlQuery.SEARCH_BY_TAG;
import static com.epam.esm.util.SqlQuery.AND;
import static com.epam.esm.util.SqlQuery.WHERE;
import static com.epam.esm.util.SqlQuery.GROUP_BY_CERTIFICATE;
import static com.epam.esm.util.SqlQuery.ORDER_BY;
import static com.epam.esm.util.SqlQuery.OFFSET;
import static com.epam.esm.util.SqlQuery.CREATE_CERTIFICATE;
import static com.epam.esm.util.SqlQuery.CREATE_A_LOT_TAGS_PART_1;
import static com.epam.esm.util.SqlQuery.CREATE_A_LOT_TAGS_PART_2;
import static com.epam.esm.util.SqlQuery.CREATE_A_LOT_CERTIFICATE_TAGS_PART_1;
import static com.epam.esm.util.SqlQuery.FIND_ID_TAG;
import static com.epam.esm.util.SqlQuery.CREATE_A_LOT_CERTIFICATE_TAGS_PART_2;
import static com.epam.esm.util.SqlQuery.UPDATE_CERTIFICATE;
import static com.epam.esm.util.SqlQuery.SUFFIX;
import static com.epam.esm.util.SqlQuery.WHERE_ID;
import static com.epam.esm.util.SqlQuery.DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID;
import static com.epam.esm.util.SqlQuery.DELETE_CERTIFICATE;

/**
 * The class {@code GiftCertificateServiceImpl} is implementation of interface {@link GiftCertificateService}
 * and provides logic to work with {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String TAGS = "tags";
    private static final String TEXT = "text";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String LAST_UPDATE_DATE = "last_update_date=?";


    private final GiftCertificateDao certificateDao;
    private final GiftCertificateTagDao certificateTagDao;
    private final TagDao tagDao;
    private final DateHandler dateHandler;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, GiftCertificateTagDao certificateTagDao, TagDao tagDao, DateHandler dateHandler) {
        this.certificateDao = certificateDao;
        this.certificateTagDao = certificateTagDao;
        this.tagDao = tagDao;
        this.dateHandler = dateHandler;
    }

    @Override
    public GiftCertificate findCertificate(long id) {
        return certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, id).orElseThrow(
                () -> new NoSuchEntityException("ex.noSuchEntity", " (id = " + id + ")"));
    }

    @Override
    public List<GiftCertificate> findListCertificates(MultiValueMap<String, String> params, Object... pageParams) {
        StringBuilder query = new StringBuilder(FIND_ALL_CERTIFICATES);
        List<Object> queryParams = new LinkedList<>();
        if (params.get(TEXT) != null || params.get(TAG) != null) {
            query.append(params.keySet().stream().filter(k -> k.equals(TEXT) || k.equals(TAG))
                    .map(key -> {
                        if (key.equals(TEXT)) return SEARCH_BY_TEXT;
                        else return SEARCH_BY_TAG;
                    }).collect(Collectors.joining(AND, WHERE, " ")));
        }
        queryParams.addAll(createQueryParamsList(params.get(TEXT)));
        queryParams.addAll(createQueryParamsList(params.get(TEXT)));
        queryParams.addAll(createQueryParamsList(params.get(TAG)));

        query.append(GROUP_BY_CERTIFICATE);
        if (params.get(SORTING) != null) {
            query.append(params.get(SORTING).stream().collect(Collectors.joining(", ", ORDER_BY, "")));
        }

        query.append(OFFSET);
        queryParams.addAll(Arrays.asList(pageParams));
        return certificateDao.findListEntities(query.toString(), queryParams.toArray());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GiftCertificate createCertificate(GiftCertificate certificate) {
        long key = certificateDao.createEntity(CREATE_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), dateHandler.getCurrentDate(), dateHandler.getCurrentDate());

        Object[] tags = certificate.getTags().stream().map(Tag::getName).distinct().toArray(Object[]::new);
        tagDao.updateEntity(CREATE_A_LOT_TAGS_PART_1 + createQueryParams("?", tags.length) + CREATE_A_LOT_TAGS_PART_2, tags);
        certificateTagDao.updateEntity(CREATE_A_LOT_CERTIFICATE_TAGS_PART_1 + createQueryParams(FIND_ID_TAG, tags.length) + CREATE_A_LOT_CERTIFICATE_TAGS_PART_2,
                Stream.concat(Arrays.stream(new Object[]{key}), Arrays.stream(tags)).toArray(Object[]::new));
        return findCertificate(key);
    }

    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GiftCertificate updateCertificate(GiftCertificate certificate, long id) {
        findCertificate(id);
        List<Object> params = new LinkedList<>();
        StringBuilder query = new StringBuilder(UPDATE_CERTIFICATE);
        for (Field field : certificate.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(certificate) != null && !field.getName().equals(TAGS)) {
                query.append(field.getName()).append(SUFFIX);
                params.add(field.get(certificate));
            }
        }

        query.append(LAST_UPDATE_DATE).append(WHERE_ID);
        params.add(dateHandler.getCurrentDate());
        params.add(id);
        certificateDao.updateEntity(query.toString(), params.toArray());

        Object[] tags = certificate.getTags().stream().map(Tag::getName).distinct().toArray(Object[]::new);
        tagDao.updateEntity(CREATE_A_LOT_TAGS_PART_1 + createQueryParams("?", tags.length) + CREATE_A_LOT_TAGS_PART_2, tags);
        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID, id);
        certificateTagDao.updateEntity(CREATE_A_LOT_CERTIFICATE_TAGS_PART_1 + createQueryParams(FIND_ID_TAG, tags.length) + CREATE_A_LOT_CERTIFICATE_TAGS_PART_2,
                Stream.concat(Arrays.stream(new Object[]{id}), Arrays.stream(tags)).toArray(Object[]::new));

        return findCertificate(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCertificate(long id) {
        findCertificate(id);
        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID, id);
        certificateDao.updateEntity(DELETE_CERTIFICATE, id);
    }

    private String createQueryParams(String str, int length) {
        return IntStream.range(0, length).mapToObj(i -> str).collect(Collectors.joining(", "));
    }

    private List<String> createQueryParamsList(List<String> params) {
        return params == null ? new ArrayList<>() : params;
    }
}