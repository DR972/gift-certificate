package com.epam.esm.service.dto.converter;

/**
 * The interface {@code DtoConverter} describes abstract behavior for converting objects.
 *
 * @param <E> Entity type
 * @param <D> DtoEntity type
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
public interface DtoConverter<E, D> {

    /**
     * The Method for converting dto to entity.
     *
     * @param dto dto entity to convert
     * @return converted entity
     */

    E convertToEntity(D dto);

    /**
     * The Method for converting entity to dto.
     *
     * @param entity source entity to convert
     * @return converted dto entity
     */
    D convertToDto(E entity);
}
