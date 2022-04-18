package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * The interface {@code GiftCertificateService} describes abstract behavior for working with
 * {@link com.epam.esm.service.impl.GiftCertificateServiceImpl} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface GiftCertificateService {

    /**
     * The method finds GiftCertificate.
     *
     * @param id GiftCertificate id
     * @return GiftCertificate object
     */
    GiftCertificate findCertificate(long id);

    /**
     * The method finds all GiftCertificates.
     *
     * @param params      MultiValueMap<String, String> all request params
     * @param queryParams array of parameters for the query
     * @return list of GiftCertificate objects
     */
    List<GiftCertificate> findListCertificates(MultiValueMap<String, String> params, Object... queryParams);

    /**
     * The method performs the operation of saving GiftCertificate.
     *
     * @param certificate GiftCertificate
     * @return GiftCertificate object
     */
    GiftCertificate createCertificate(GiftCertificate certificate);

    /**
     * The method performs the operation of updating GiftCertificate.
     *
     * @param certificate new Gift Certificate parameters
     * @param id          GiftCertificate id
     * @return GiftCertificate object
     */
    GiftCertificate updateCertificate(GiftCertificate certificate, long id);

    /**
     * The method performs the operation of deleting GiftCertificate.
     *
     * @param id GiftCertificate id
     */
    void deleteCertificate(long id);
}
