package com.mumanal.shared.domain.service;

import com.mumanal.shared.domain.dto.request.*;
import com.mumanal.shared.domain.dto.response.*;

import java.util.List;

public interface SystemParameterService {
    List<ParameterCategoryResponse> getAllCategories();
    ParameterCategoryResponse createCategory(CreateCategoryRequest request);
    ParameterCategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);
    List<ParameterResponse> getParametersByCategory(Integer categoryId);
    List<ParameterResponse> getParametersByCategoryCode(String categoryName);
    ParameterResponse createParameter(CreateParameterRequest request);
    ParameterResponse updateParameter(Integer id, UpdateParameterRequest request);
    void deleteParameter(Integer id);
}