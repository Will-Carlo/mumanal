package com.mumanal.modules.finance.persistence.mapper;

import com.mumanal.modules.finance.domain.dto.request.CreateBankRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateBankRequest;
import com.mumanal.modules.finance.domain.dto.response.BankResponse;
import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankMapper {

    // Entity -> DTO
    BankResponse toDto(FinBankEntity entity);
    List<BankResponse> toDto(List<FinBankEntity> entities);

    // DTO -> Entity (Create)
    FinBankEntity toEntity(CreateBankRequest request);

    // DTO -> Entity (Update)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateBankRequest request, @MappingTarget FinBankEntity entity);
}