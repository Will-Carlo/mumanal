package com.mumanal.shared.domain.service;

import com.mumanal.shared.domain.dto.request.*;
import com.mumanal.shared.domain.dto.response.*;
import com.mumanal.shared.domain.repository.SystemParameterRepository;
import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;
import com.mumanal.shared.persistence.entity.GenParameterEntity;
import com.mumanal.shared.persistence.mapper.ParameterMapper;
import com.mumanal.modules.security.domain.util.SecurityUtils;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

    private final SystemParameterRepository repository;
    private final ParameterMapper mapper;
    private final SecurityUtils securityUtils; // Para auditoría (createdBy)

    public SystemParameterServiceImpl(SystemParameterRepository repository, ParameterMapper mapper, SecurityUtils securityUtils) {
        this.repository = repository;
        this.mapper = mapper;
        this.securityUtils = securityUtils;
    }

    // ==========================================
    // CATEGORÍAS (MASTERS)
    // ==========================================

    @Override
    public List<ParameterCategoryResponse> getAllCategories() {
        return mapper.toCategoryDto(repository.findAllCategories());
    }

    @Override
    @Transactional
    public ParameterCategoryResponse createCategory(CreateCategoryRequest request) {
        if (repository.existsCategoryByCode(request.code())) {
            throw new ResourceAlreadyExistsException("Category", "code", request.code());
        }
        GenParameterCategoryEntity entity = mapper.toCategoryEntity(request);
        entity.setCreatedBy(securityUtils.getCurrentUsername()); // Auditoría
        return mapper.toCategoryDto(repository.saveCategory(entity));
    }

    @Override
    @Transactional
    public ParameterCategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        GenParameterCategoryEntity entity = repository.findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));

        mapper.updateCategoryFromDto(request, entity);
        return mapper.toCategoryDto(repository.saveCategory(entity));
    }

    // ==========================================
    // PARÁMETROS (DETAILS)
    // ==========================================

    @Override
    public List<ParameterResponse> getParametersByCategory(Integer categoryId) {
        // Validamos que exista la categoría? No es estricto para listar, pero buena práctica
        return mapper.toParameterDto(repository.findParametersByCategoryId(categoryId));
    }

    @Override
    public List<ParameterResponse> getParametersByCategoryCode(String categoryCode) {
        GenParameterCategoryEntity category = repository.findCategoryByCode(categoryCode)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryCode));

        return mapper.toParameterDto(repository.findParametersByCategoryId(category.getId()));
    }

    @Override
    @Transactional
    public ParameterResponse createParameter(CreateParameterRequest request) {
        // 1. Validar Categoría
        GenParameterCategoryEntity category = repository.findCategoryById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.categoryId().toString()));

        // 2. Validar Duplicidad de Código Numérico en esa Categoría
        if (repository.existsParameterByNumericCode(request.numericCode(), request.categoryId())) {
            throw new ResourceAlreadyExistsException("Parameter", "numericCode", request.numericCode().toString());
        }

        // 3. Crear
        GenParameterEntity entity = mapper.toParameterEntity(request);
        entity.setCategory(category);
        entity.setCreatedBy(securityUtils.getCurrentUsername());

        return mapper.toParameterDto(repository.saveParameter(entity));
    }

    @Override
    @Transactional
    public ParameterResponse updateParameter(Integer id, UpdateParameterRequest request) {
        GenParameterEntity entity = repository.findParameterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter", "id", id.toString()));

        mapper.updateParameterFromDto(request, entity);
        return mapper.toParameterDto(repository.saveParameter(entity));
    }

    @Override
    @Transactional
    public void deleteParameter(Integer id) {
        GenParameterEntity entity = repository.findParameterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter", "id", id.toString()));

        entity.softDelete(); // Método de AuditableEntity
        repository.deleteParameter(entity); // Realmente hace un update status=false
    }
}