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
     * Provides logic for searching GiftCertificate.
     *
     * @param id GiftCertificate id
     * @return GiftCertificate object
     */
    GiftCertificate findCertificate(long id);

    /**
     * Provides logic for searching for all GiftCertificate objects.
     *
     * @param params      MultiValueMap<String, String> all request params
     * @param queryParams array of parameters for the query
     * @return list of GiftCertificate objects
     */
    List<GiftCertificate> findListCertificates(MultiValueMap<String, String> params, Object... queryParams);

    /**
     * Provides logic for saving GiftCertificate in the database.
     *
     * @param certificate GiftCertificate
     * @return GiftCertificate object
     */
    GiftCertificate createCertificate(GiftCertificate certificate);

    /**
     * Provides logic for updating GiftCertificate in the database.
     *
     * @param certificate new Gift Certificate parameters
     * @param id          GiftCertificate id
     * @return GiftCertificate object
     */
    GiftCertificate updateCertificate(GiftCertificate certificate, long id);

    /**
     * Provides logic for deleting GiftCertificate in the database.
     *
     * @param id GiftCertificate id
     */
    void deleteCertificate(long id);
}
