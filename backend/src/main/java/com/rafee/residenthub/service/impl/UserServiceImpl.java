package com.rafee.residenthub.service.impl;

import com.rafee.residenthub.dto.enums.ActionType;
import com.rafee.residenthub.dto.enums.EntityType;
import com.rafee.residenthub.dto.request.AddUserRequest;
import com.rafee.residenthub.dto.request.ResetPasswordRequest;
import com.rafee.residenthub.dto.response.UserResponse;
import com.rafee.residenthub.entity.User;
import com.rafee.residenthub.event.model.ActivityEvent;
import com.rafee.residenthub.exception.ResourceConflictException;
import com.rafee.residenthub.exception.ResourceNotFoundException;
import com.rafee.residenthub.repository.UserRepository;
import com.rafee.residenthub.security.AuthenticatedUser;
import com.rafee.residenthub.mapper.UserMapper;
import com.rafee.residenthub.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticatedUser authenticatedUser;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponse addUser(@Valid AddUserRequest userRequest){

        log.info("addUser service called {}", userRequest);

        checkDuplicateUser(userRequest.userName(), userRequest.email());

        String encodedPassword = passwordEncoder.encode(userRequest.password());
        User user = new User();
        user.setUsername(userRequest.userName());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_ADMIN");
        user.setDeleted(false);
        user.setEmail(userRequest.email());

        User savedUser = userRepository.save(user);

        String description = String.format("User [%s] was added to the system",savedUser.getUsername());

        log.info(description);

        eventPublisher.publishEvent(ActivityEvent.userActivity(authenticatedUser.getCurrentUser().getId(), savedUser.getId(), EntityType.USER, ActionType.USER_CREATION, description));

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

        log.info("deleteUser called for {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found " + id));

        String description = String.format("User [%s] was removed from the system",user.getUsername());

        log.info(description);

        eventPublisher.publishEvent(ActivityEvent.userActivity(authenticatedUser.getCurrentUser().getId(), user.getId(), EntityType.USER, ActionType.USER_DELETION, description));

        userRepository.delete(user);
    }

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {

        log.info("getUsers called for page {} - {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<User> usersPage = userRepository.findAllByOrderByUsernameAsc(pageable);

        return usersPage.map(userMapper::toDTO);
    }

    @Transactional
    @Override
    public void resetPassword(Long userId, ResetPasswordRequest request, Authentication authentication) {

        log.info("resetPassword called for userId {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));

        if(!passwordEncoder.matches(request.currentPassword(), authenticatedUser.getCurrentUser().getPassword())) {
            throw new ResourceConflictException("Invalid admin credentials", "INVALID_CURRENT_PASSWORD");  // Need to change
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new ResourceConflictException("New password cannot be the same as the old password", "SAME_NEW_PASSWORD");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        String description = String.format("Password reset for User[%s]",user.getUsername());
        log.info(description);
        eventPublisher.publishEvent(ActivityEvent.userActivity(authenticatedUser.getCurrentUser().getId(), user.getId(), EntityType.USER, ActionType.PASSWORD_RESET, description));
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    private void checkDuplicateUser(String username, String email){

        userRepository.findByUsername(username).ifPresent(user -> {
            throw new ResourceConflictException("User with same name already exists", "DUPLICATE_USERNAME");
        });

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ResourceConflictException("User with same Email already exists", "DUPLICATE_EMAIL");
        });

    }

}
