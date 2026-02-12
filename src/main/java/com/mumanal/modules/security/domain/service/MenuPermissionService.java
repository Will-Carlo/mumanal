package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.AssignPermissionToMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuPermissionResponse;

import java.util.List;

public interface MenuPermissionService {
    List<MenuPermissionResponse> getPermissionsByMenu(Integer menuId);
    List<MenuPermissionResponse> assignPermissions(AssignPermissionToMenuRequest request);
    void revokePermission(Integer menuId, Integer permissionId);

    void delete(Integer id);
    void recover(Integer id);
}