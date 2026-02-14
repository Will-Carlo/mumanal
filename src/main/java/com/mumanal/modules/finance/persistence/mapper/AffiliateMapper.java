package com.mumanal.modules.finance.persistence.mapper;

import com.mumanal.modules.finance.domain.dto.request.CreateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.response.AffiliateResponse;
import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import com.mumanal.modules.generic.persistence.mapper.PersonMapper; // Importante
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface AffiliateMapper {

    // Entity -> DTO Response (Automáticamente usa PersonMapper para la persona)
    AffiliateResponse toDto(FinAffiliateEntity entity);
    List<AffiliateResponse> toDto(List<FinAffiliateEntity> entities);

    // CreateRequest -> Entity (Ignoramos persona aquí, la manejamos manualmente en el servicio)
    @Mapping(target = "person", ignore = true)
//    @Mapping(target = "status", ignore = true) // Mapeamos string a enum manualmente o con qualifiedByName
    FinAffiliateEntity toEntity(CreateAffiliateRequest request);

    // UpdateRequest -> Entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "person", ignore = true) // La persona se actualiza via PersonService/Repository
    void updateEntityFromDto(UpdateAffiliateRequest request, @MappingTarget FinAffiliateEntity entity);
}