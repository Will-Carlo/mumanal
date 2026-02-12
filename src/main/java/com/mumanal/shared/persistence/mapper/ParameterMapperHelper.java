package com.mumanal.shared.persistence.mapper;

import com.mumanal.shared.domain.repository.SystemParameterRepository;
import com.mumanal.shared.persistence.entity.GenParameterEntity;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class ParameterMapperHelper {

    private final SystemParameterRepository parameterRepository;

    public ParameterMapperHelper(SystemParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    // Nombre Genérico: Sirve para CategoryType, PaymentMethod, Gender, etc.
    @Named("getParameterName")
    public String getParameterName(Integer numericCode) {
        if (numericCode == null) return null;

        return parameterRepository.findParameterByNumericCode(numericCode)
                .map(GenParameterEntity::getName)
                .orElse("Desconocido (" + numericCode + ")");
    }
}