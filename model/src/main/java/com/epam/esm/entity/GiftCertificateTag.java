package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class {@code GiftCertificateTag} represents GiftCertificateTag entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GiftCertificateTag extends Entity<Long> {
    /**
     * GiftCertificateTag certificateId.
     */
    private long certificateId;
    /**
     * GiftCertificateTag tagId.
     */
    private long tagId;
}
