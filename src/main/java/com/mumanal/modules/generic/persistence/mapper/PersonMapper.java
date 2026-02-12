package com.mumanal.modules.generic.persistence.mapper;

import com.mumanal.modules.generic.domain.dto.request.CreatePersonRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdatePersonRequest;
import com.mumanal.modules.generic.domain.dto.response.PersonResponse;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    // CREATE (DTO -> Entity)
    GenPersonEntity toEntity(CreatePersonRequest request);
    List<PersonResponse> toDto(List<GenPersonEntity> entities);

    // RESPONSE (Entity -> DTO)
    @Mapping(target = "fullName", expression = "java(buildFullName(entity))")
    PersonResponse toDto(GenPersonEntity entity);

    // UPDATE (DTO -> Entity existente)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdatePersonRequest request, @MappingTarget GenPersonEntity entity);

    default String buildFullName(GenPersonEntity entity) {
        if (entity == null) {
            return "";
        }

        StringBuilder fullName = new StringBuilder();

        if (entity.getFirstName() != null) {
            fullName.append(entity.getFirstName());
        }

        if (entity.getPaternalSurname() != null) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(entity.getPaternalSurname());
        }

        if (entity.getMaternalSurname() != null && !entity.getMaternalSurname().isEmpty()) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(entity.getMaternalSurname());
        }

        return fullName.toString();
    }
}