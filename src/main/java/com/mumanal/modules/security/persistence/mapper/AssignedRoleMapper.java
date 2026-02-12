package com.mumanal.modules.security.persistence.mapper;

import com.mumanal.modules.security.domain.dto.request.CreateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.response.AssignmentResponse;
import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignedRoleMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    AssignmentResponse toDto(SecAssignedRoleEntity entity);

    List<AssignmentResponse> toDto(List<SecAssignedRoleEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "grantedBy", ignore = true)
    @Mapping(target = "grantedDate", ignore = true)
    SecAssignedRoleEntity toEntity(CreateAssignmentRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "grantedBy", ignore = true)
    @Mapping(target = "grantedDate", ignore = true)
    void updateEntityFromDto(UpdateAssignmentRequest request, @MappingTarget SecAssignedRoleEntity entity);
}