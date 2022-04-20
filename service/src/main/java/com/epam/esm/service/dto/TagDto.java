package com.epam.esm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class {@code TagDto} represents TagDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.service.dto.TagDto} class
     * fields when creating and updating a new TagDto object
     */
    public interface OnCreate {
    }

    /**
     * TagDto id.
     */
    private long id;
    /**
     * TagDto name.
     */
    @Size(groups = {TagDto.OnCreate.class, GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, min = 2, max = 30, message = "ex.tagNameSize")
    @NotNull(groups = TagDto.OnCreate.class, message = "ex.tagNameNotNull")
    private String name;
}
