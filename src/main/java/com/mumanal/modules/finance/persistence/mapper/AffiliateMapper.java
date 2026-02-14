package com.mumanal.modules.finance.persistence.mapper;

import com.mumanal.modules.finance.domain.dto.request.CreateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.response.AffiliateResponse;
import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.modules.generic.persistence.mapper.PersonMapper; // Importante
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface AffiliateMapper {

    @Mapping(source = "person.firstName", target = "firstName")
    @Mapping(source = "person.secondName", target = "secondName")
    @Mapping(source = "person.paternalSurname", target = "paternalSurname")
    @Mapping(source = "person.maternalSurname", target = "maternalSurname")
    @Mapping(source = "person.identityCard", target = "identityCard")
    @Mapping(target = "fullName", expression = "java(buildFullName(entity.getPerson()))")
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

    default String buildFullName(GenPersonEntity person) {
        if (person == null) {
            return "";
        }

        StringBuilder fullName = new StringBuilder();

        if (person.getFirstName() != null) {
            fullName.append(person.getFirstName());
        }

        if (person.getSecondName() != null && !person.getSecondName().isEmpty()) {
            fullName.append(" ").append(person.getSecondName());
        }

        if (person.getPaternalSurname() != null) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(person.getPaternalSurname());
        }

        if (person.getMaternalSurname() != null && !person.getMaternalSurname().isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(person.getMaternalSurname());
        }

        return fullName.toString();
    }
}