package com.mumanal.modules.generic.domain.service;

import com.mumanal.modules.generic.domain.dto.request.CreateCityRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdateCityRequest;
import com.mumanal.modules.generic.domain.dto.response.CityResponse;
import com.mumanal.modules.generic.domain.repository.CityRepository;
import com.mumanal.modules.generic.persistence.entity.GenCityEntity;
import com.mumanal.modules.generic.persistence.mapper.CityMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final String RESOURCE = "City";

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityResponse> getAll() {
        return cityMapper.toDto(cityRepository.findAll());
    }

    @Override
    public CityResponse getById(Integer id) {
        GenCityEntity entity = findEntityById(id);
        return cityMapper.toDto(entity);
    }

    @Override
    @Transactional
    public CityResponse create(CreateCityRequest request) {
        // Validamos que no exista la misma ciudad en el mismo país
        if (cityRepository.existsByNameAndCountry(request.name(), request.country())) {
            throw new ResourceAlreadyExistsException(RESOURCE, "name", request.name() + " in " + request.country());
        }

        GenCityEntity entity = cityMapper.toEntity(request);
        return cityMapper.toDto(cityRepository.save(entity));
    }

    @Override
    @Transactional
    public CityResponse update(Integer id, UpdateCityRequest request) {
        GenCityEntity entity = findEntityById(id);

        // Si cambia nombre o país, verificamos duplicados
        String newName = request.name() != null ? request.name() : entity.getName();
        String newCountry = request.country() != null ? request.country() : entity.getCountry();

        // Solo validamos si hubo algún cambio real
        if (!newName.equals(entity.getName()) || !newCountry.equals(entity.getCountry())) {
            if (cityRepository.existsByNameAndCountryAndIdNot(newName, newCountry, id)) {
                throw new ResourceAlreadyExistsException(RESOURCE, "name + country", newName + " in " + newCountry);
            }
        }

        cityMapper.updateEntityFromDto(request, entity);
        return cityMapper.toDto(cityRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        GenCityEntity entity = findEntityById(id);
        entity.softDelete();
        cityRepository.save(entity);
    }

    private GenCityEntity findEntityById(Integer id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
    }
}