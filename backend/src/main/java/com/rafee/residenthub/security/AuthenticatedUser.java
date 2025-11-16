package com.rafee.residenthub.security;

import com.rafee.residenthub.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new IllegalStateException("No authenticated user found");
        }

        return (User) authentication.getPrincipal();

    }

}
