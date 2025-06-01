package com.author.shortener;

import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateUrlMapper {
    default String unwrap(Optional<String> value) {
        System.out.println("value: " + value.isEmpty());
        if (value.isEmpty()) {
            return null;
        }

        return value.get();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateUserFromDto(UpdateUrlDTO dto, @MappingTarget UrlEntity entity);

    @AfterMapping
    default void handleNullFields(UpdateUrlDTO dto, @MappingTarget UrlEntity entity) {
        System.out.println("dto.getUpdatedAt(): " + dto.getUpdatedAt().isEmpty());
        if (dto.getUpdatedAt() != null && dto.getUpdatedAt().isEmpty()) {
            entity.setUpdatedAt(null);
        }
    }
}
