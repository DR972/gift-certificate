package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;
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
     * @param id GiftCertificateDto id
     * @return GiftCertificateDto object
     */
    GiftCertificateDto findCertificate(long id);

    /**
     * The method finds all GiftCertificates.
     *
     * @param params      MultiValueMap<String, String> all request params
     * @param queryParams array of parameters for the query
     * @return list of GiftCertificateDto objects
     */
    List<GiftCertificateDto> findListCertificates(MultiValueMap<String, String> params, Object... queryParams);

    /**
     * The method performs the operation of saving GiftCertificate.
     *
     * @param certificateDto GiftCertificateDto
     * @return GiftCertificateDto object
     */
    GiftCertificateDto createCertificate(GiftCertificateDto certificateDto);

    /**
     * The method performs the operation of updating GiftCertificate.
     *
     * @param certificateDto new GiftCertificateDto parameters
     * @param id             GiftCertificateDto id
     * @return GiftCertificateDto object
     */
    GiftCertificateDto updateCertificate(GiftCertificateDto certificateDto, long id);

    /**
     * The method performs the operation of deleting GiftCertificate.
     *
     * @param id GiftCertificateDto id
     */
    void deleteCertificate(long id);
}
