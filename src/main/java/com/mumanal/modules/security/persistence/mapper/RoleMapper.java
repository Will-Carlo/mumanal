package com.mumanal.modules.security.persistence.mapper;

import com.mumanal.modules.security.domain.dto.request.CreateRoleRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateRoleRequest;
import com.mumanal.modules.security.domain.dto.response.RoleResponse;
import com.mumanal.modules.security.persistence.entity.SecAssignedPermissionEntity;
import com.mumanal.modules.security.persistence.entity.SecRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // Entity -> Response
    @Mapping(target = "permissions", expression = "java(mapPermissionsToCodes(entity.getPermissions()))")
    RoleResponse toDto(SecRoleEntity entity);

    List<RoleResponse> toDto(List<SecRoleEntity> entities);

    // CreateRequest -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    SecRoleEntity toEntity(CreateRoleRequest request);

    // UpdateRequest -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateEntityFromDto(UpdateRoleRequest request, @MappingTarget SecRoleEntity entity);

    default List<String> mapPermissionsToCodes(List<SecAssignedPermissionEntity> rolePermissions) {
        if (rolePermissions == null) return List.of();
        return rolePermissions.stream()
                .map(rp -> rp.getPermission().getCode()) // Extract the CODE
                .toList();
    }
}