package com.mumanal.modules.security.persistence.mapper;

import com.mumanal.modules.security.domain.dto.request.CreateMenuRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuResponse;
import com.mumanal.modules.security.persistence.entity.SecMenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {
    // 1. Entity -> Response
    @Mapping(target = "children", source = "subMenus")
    MenuResponse toDto(SecMenuEntity entity);
    List<MenuResponse> toDto(List<SecMenuEntity> entities);

    // 2. Create -> Entity
    @Mapping(target = "subMenus", ignore = true)
    @Mapping(target = "requiredPermissions", ignore = true)
    @Mapping(target = "sortOrder", ignore = true)
    @Mapping(target = "isPublic", ignore = true)
    @Mapping(target = "parentMenu", ignore = true)
    SecMenuEntity toEntity(CreateMenuRequest dto);

    // 3. Update -> Entity
    @Mapping(target = "parentMenu", ignore = true)
    void updateEntityFromDto(UpdateMenuRequest dto, @MappingTarget SecMenuEntity entity);
}