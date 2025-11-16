package com.rafee.residenthub.controller;

import com.rafee.residenthub.dto.request.AddUserRequest;
import com.rafee.residenthub.dto.request.ResetPasswordRequest;
import com.rafee.residenthub.dto.response.ApiResponse;
import com.rafee.residenthub.dto.response.UserResponse;
import com.rafee.residenthub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUsers(Pageable pageable) {

        log.info("getUsers controller called");

        Page<UserResponse> usersPage = userService.getUsers(pageable);;
        List<UserResponse> usersList = usersPage.getContent();

        if (usersList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("No residents found", usersList));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Residents fetched", usersList, usersPage));

    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@RequestBody @Valid AddUserRequest request) {

        log.info("add user controller called");

        UserResponse usersResponse = userService.addUser(request);

        return ResponseEntity.ok().body(new ApiResponse("User created successfully", usersResponse));
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword (@PathVariable Long id, @RequestBody @Valid ResetPasswordRequest request, Authentication authentication){

        log.info("reset password controller called");

        userService.resetPassword(id, request, authentication);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){

        log.info("Delete user controller");

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();

    }


}
