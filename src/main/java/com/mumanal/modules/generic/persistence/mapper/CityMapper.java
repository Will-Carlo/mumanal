package com.mumanal.modules.generic.persistence.mapper;

import com.mumanal.modules.generic.domain.dto.request.CreateCityRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdateCityRequest;
import com.mumanal.modules.generic.domain.dto.response.CityResponse;
import com.mumanal.modules.generic.persistence.entity.GenCityEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {

    // CREATE
    GenCityEntity toEntity(CreateCityRequest request);

    // READ
    CityResponse toDto(GenCityEntity entity);
    List<CityResponse> toDto(List<GenCityEntity> entities);

    // UPDATE
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCityRequest request, @MappingTarget GenCityEntity entity);
}