package com.mumanal.shared.persistence.mapper;

import com.mumanal.shared.domain.dto.request.*;
import com.mumanal.shared.domain.dto.response.*;
import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;
import com.mumanal.shared.persistence.entity.GenParameterEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParameterMapper {

    // Categories
    GenParameterCategoryEntity toCategoryEntity(CreateCategoryRequest request);
    ParameterCategoryResponse toCategoryDto(GenParameterCategoryEntity entity);
    List<ParameterCategoryResponse> toCategoryDto(List<GenParameterCategoryEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(UpdateCategoryRequest request, @MappingTarget GenParameterCategoryEntity entity);

    // Parameters
    @Mapping(target = "category", ignore = true)
    GenParameterEntity toParameterEntity(CreateParameterRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ParameterResponse toParameterDto(GenParameterEntity entity);

    List<ParameterResponse> toParameterDto(List<GenParameterEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateParameterFromDto(UpdateParameterRequest request, @MappingTarget GenParameterEntity entity);
}