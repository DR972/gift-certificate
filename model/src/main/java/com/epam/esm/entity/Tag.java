package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The class {@code Tag} represents tag entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends Entity<Long> {

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.entity.Tag} class
     * fields when creating and updating a new GiftCertificate object
     */
    public interface OnCreate {
    }

    @Size(groups = {Tag.OnCreate.class, GiftCertificate.OnCreate.class, GiftCertificate.OnUpdate.class}, min = 2, max = 30, message = "ex.tagNameSize")
    @NotNull(groups = Tag.OnCreate.class, message = "ex.tagNameNotNull")
    private String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }
}
