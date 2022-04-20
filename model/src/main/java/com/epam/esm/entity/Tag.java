package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
     * Tag name.
     */
    private String name;

    /**
     * The constructor creates a Tag object
     *
     * @param id   long id
     * @param name String name
     */
    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }
}
