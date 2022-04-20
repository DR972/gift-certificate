package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The class {@code Entity} represents the base class for all entities.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity<ID> implements Serializable {
    /**
     * Entity id.
     */
    protected ID id;
}
