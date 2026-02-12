package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateUserRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateUserRequest;
import com.mumanal.modules.security.domain.dto.response.UserProfileResponse;
import com.mumanal.modules.security.domain.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse getById(Integer id);
    UserResponse create(CreateUserRequest request);
    UserResponse update(Integer id, UpdateUserRequest request);

    UserProfileResponse getProfileByUsername(String username);
}
