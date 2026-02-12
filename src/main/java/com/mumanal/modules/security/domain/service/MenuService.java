package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateMenuRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuResponse;

import java.util.List;

public interface MenuService {
    List<MenuResponse> getAll(Boolean status);
    MenuResponse getById(Integer id);
    MenuResponse create(CreateMenuRequest request);
    MenuResponse update(Integer id, UpdateMenuRequest request);
    void delete(Integer id);
    void recover(Integer id);

    List<MenuResponse> getTreeForCurrentUser();
}