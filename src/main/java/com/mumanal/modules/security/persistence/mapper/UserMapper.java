package com.mumanal.modules.security.persistence.mapper;

import com.mumanal.modules.generic.persistence.mapper.PersonMapper;
import com.mumanal.modules.security.domain.dto.request.CreateUserRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateUserRequest;
import com.mumanal.modules.security.domain.dto.response.UserResponse;
import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;
import com.mumanal.modules.security.persistence.entity.SecUserEntity;
import com.mumanal.shared.persistence.mapper.ParameterMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {PersonMapper.class, ParameterMapperHelper.class})
public interface UserMapper {

    // 1. Entity → Response (Read)
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person", target = "personName")
    // parameter
    @Mapping(source = "statusType", target = "statusName", qualifiedByName = "getParameterName")
    @Mapping(source = "statusType", target = "statusCode")
    @Mapping(source = "assignedRoles", target = "roles", qualifiedByName = "mapRolesToString")
    UserResponse toDto(SecUserEntity entity);
    List<UserResponse> toDto(List<SecUserEntity> entities);

    // 2. Request (Create) → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true) // Lo buscamos en el Service
    @Mapping(target = "assignedRoles", ignore = true) // Lo gestionamos manual
    @Mapping(target = "passwordHash", ignore = true) // Lo hasheamos manual
    @Mapping(target = "locked", constant = "false")
    @Mapping(target = "disabled", constant = "false")
    SecUserEntity toEntity(CreateUserRequest request);

    // 3. Request (Update) → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true) // Username no se edita
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "assignedRoles", ignore = true)
    void updateEntityFromDto(UpdateUserRequest request, @MappingTarget SecUserEntity entity);

    @Named("mapRolesToString")
    default List<String> mapRolesToString(List<SecAssignedRoleEntity> assignedRoles) {
        if (assignedRoles == null) return List.of();
        return assignedRoles.stream()
                .map(ar -> ar.getRole().getName())
                .collect(Collectors.toList());
    }
}