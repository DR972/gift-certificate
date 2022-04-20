package com.epam.esm.service.dto.converter.impl;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.converter.DtoConverter;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

/**
 * The Class {@code GiftCertificateDtoConverter} is implementation of interface {@link DtoConverter}
 * and intended to work with {@link GiftCertificate} and {@link GiftCertificateDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Component
public class GiftCertificateDtoConverter implements DtoConverter<GiftCertificate, GiftCertificateDto> {

    @Override
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {
        return new GiftCertificate(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(),
                dto.getDuration(), dto.getCreateDate(), dto.getLastUpdateDate(), dto.getTags());
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate entity) {
        return new GiftCertificateDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getDuration(),
                entity.getCreateDate(), entity.getLastUpdateDate(), entity.getTags());
    }
}
