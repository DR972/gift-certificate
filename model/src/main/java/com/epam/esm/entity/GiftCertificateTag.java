package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The class {@code GiftCertificateTag} represents GiftCertificateTag entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateTag extends Entity<Long> {
    private long certificateId;
    private long tagId;
}
