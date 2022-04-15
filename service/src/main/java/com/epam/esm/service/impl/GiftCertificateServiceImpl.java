package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.NoSuchEntityException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.util.SqlQuery.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String TAGS = "tags";
    private static final String TEXT = "text";
    private static final String TAG = "tag";
    private static final String SORTING = "sorting";
    private static final String LAST_UPDATE_DATE = "last_update_date=?";


    private final GiftCertificateDao certificateDao;
    private final GiftCertificateTagDao certificateTagDao;
    private final TagService tagService;
    private final Clock clock;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, GiftCertificateTagDao certificateTagDao, TagService tagService, Clock clock) {
        this.certificateDao = certificateDao;
        this.certificateTagDao = certificateTagDao;
        this.tagService = tagService;
        this.clock = clock;
    }

    @Override
    public GiftCertificate findCertificate(long id) {
        return certificateDao.findEntity(FIND_CERTIFICATE_BY_ID, id).orElseThrow(
                () -> new NoSuchEntityException("There is no Gift Certificate with this ID = " + id));
    }

    @Override
    public List<GiftCertificate> findListCertificates(MultiValueMap<String, String> params, Object... pageParams) {
        StringBuilder query = new StringBuilder(FIND_ALL_CERTIFICATES);
        List<Object> queryParams = new LinkedList<>();
        if (params.get(TEXT) != null || params.get(TAG) != null) {
            query.append(params.keySet().stream()
                    .filter(k -> k.equals(TEXT) || k.equals(TAG))
                    .map(key -> {
                        if (key.equals(TEXT)) return SEARCH_BY_TEXT;
                        else return SEARCH_BY_TAG;
                    })
                    .collect(Collectors.joining(AND, WHERE, " ")));
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
        LocalDateTime dateTime = LocalDateTime.now(clock);

        long key = certificateDao.createEntity(CREATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                dateTime,
                dateTime);

        List<String> allTags = tagService.findAllTags().stream().map(Tag::getName).collect(Collectors.toList());
        certificate.getTags().stream().filter(tag -> !allTags.contains(tag.getName())).forEachOrdered(tagService::createTag);
        certificate.getTags().forEach(t -> certificateTagDao.updateEntity(CREATE_CERTIFICATE_TAG_BY_TAG_NAME, key, t.getName()));
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
        params.add(LocalDateTime.now(clock));
        params.add(id);
        certificateDao.updateEntity(query.toString(), params.toArray());
        List<String> allTags = tagService.findAllTags().stream().map(Tag::getName).collect(Collectors.toList());
        certificate.getTags().stream().filter(tag -> !allTags.contains(tag.getName())).forEachOrdered(tagService::createTag);

        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID, id);
        certificate.getTags().forEach(t -> certificateTagDao.updateEntity(CREATE_CERTIFICATE_TAG_BY_TAG_NAME, id, t.getName()));

        return findCertificate(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCertificate(long id) {
        findCertificate(id);
        certificateTagDao.updateEntity(DELETE_CERTIFICATE_TAG_BY_CERTIFICATE_ID, id);
        certificateDao.updateEntity(DELETE_CERTIFICATE, id);
    }

    private List<String> createQueryParamsList(List<String> params) {
        return params == null ? new ArrayList<>() : params;
    }
}