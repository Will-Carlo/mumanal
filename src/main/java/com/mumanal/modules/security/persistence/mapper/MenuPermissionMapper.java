package com.mumanal.modules.security.persistence.mapper;

import com.mumanal.modules.security.domain.dto.response.MenuPermissionResponse;
import com.mumanal.modules.security.persistence.entity.SecMenuPermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuPermissionMapper {

    @Mapping(source = "menu.id", target = "menuId")
    @Mapping(source = "menu.name", target = "menuName")
    @Mapping(source = "permission.id", target = "permissionId")
    @Mapping(source = "permission.code", target = "permissionCode")
    @Mapping(source = "permission.name", target = "permissionName")
    MenuPermissionResponse toDto(SecMenuPermissionEntity entity);

    List<MenuPermissionResponse> toDto(List<SecMenuPermissionEntity> entities);
}