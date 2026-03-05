package com.mumanal.modules.finance.persistence.mapper;

import com.mumanal.modules.finance.domain.dto.request.UpdateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.response.VoucherResponse;
import com.mumanal.modules.finance.persistence.entity.FinVoucherEntity;
import com.mumanal.modules.generic.persistence.mapper.PersonMapper; // Necesario para mapear Person
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {BankMapper.class, AffiliateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoucherMapper {

    VoucherResponse toDto(FinVoucherEntity entity);
    List<VoucherResponse> toDto(List<FinVoucherEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateVoucherRequest request, @MappingTarget FinVoucherEntity entity);
}