package com.epam.esm.service.dto.converter.impl;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.converter.DtoConverter;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

/**
 * The Class {@code TagDtoConverter} is implementation of interface {@link DtoConverter}
 * and intended to work with {@link Tag} and {@link TagDto} objects.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Component
public class TagDtoConverter implements DtoConverter<Tag, TagDto> {

    @Override
    public Tag convertToEntity(TagDto dto) {
        return new Tag(dto.getId(), dto.getName());
    }

    @Override
    public TagDto convertToDto(Tag entity) {
        return new TagDto(entity.getId(), entity.getName());
    }
}
