package com.mumanal.modules.security.persistence.mapper;

import com.mumanal.modules.security.domain.dto.request.CreatePermissionRequest;
import com.mumanal.modules.security.domain.dto.request.UpdatePermissionRequest;
import com.mumanal.modules.security.domain.dto.response.PermissionResponse;
import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    // 1. Entity → Response (Read)
    PermissionResponse toDto(SecPermissionEntity entity);
    List<PermissionResponse> toDto(List<SecPermissionEntity> entities);

    // 2. Request (Create) → Entity
    @Mapping(target = "id", ignore = true)
    SecPermissionEntity toEntity(CreatePermissionRequest request);

    // 3. Request (Update) → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    void updateEntityFromDto(UpdatePermissionRequest request, @MappingTarget SecPermissionEntity entity);
}
