package com.rafee.residenthub.service;

import com.rafee.residenthub.dto.request.AddUserRequest;
import com.rafee.residenthub.dto.request.ResetPasswordRequest;
import com.rafee.residenthub.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse addUser(@Valid AddUserRequest userDto);

    void deleteUser(Long id);

    Page<UserResponse> getUsers(Pageable pageable);

    void resetPassword(Long id, ResetPasswordRequest request, Authentication authentication);
}
