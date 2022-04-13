package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate findCertificate(long id);

    List<GiftCertificate> findListCertificates(MultiValueMap<String, String> params, Object... queryParams);

    GiftCertificate createCertificate(GiftCertificate certificate);

    GiftCertificate updateCertificate(GiftCertificate certificate, long id);

    void deleteCertificate(long id);
}
