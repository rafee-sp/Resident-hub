package com.rafee.residenthub.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rafee.residenthub.dto.response.ErrorResponse;
import com.rafee.residenthub.dto.request.UserLoginRequest;
import com.rafee.residenthub.entity.User;
import com.rafee.residenthub.exception.UnAuthorizedException;
import com.rafee.residenthub.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginRequest userLogin = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);

            log.debug("userLogin.getUserEmail() : {}", userLogin.getUserName());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {

                User user = (User) authentication.getPrincipal();

                log.debug("userLogin.getUserEmail() : {}", user.getUsername());

                String accessToken = jwtService.generateToken(authentication.getName(), true);

                String refreshToken = jwtService.generateToken(authentication.getName(), false);

                Cookie cookie = new Cookie("refreshToken", refreshToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(false);
                cookie.setPath("/refresh-token");
                cookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookie);

                Map<String, String> userMap = new HashMap<>();

                userMap.put("accessToken", accessToken);
                userMap.put("user", user.getUsername());

                response.setContentType("application/json");
                objectMapper.writeValue(response.getWriter(), userMap);

            } else {
                throw new UnAuthorizedException("Invalid credentials");
            }

        } catch (Exception ex) {

            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }

    }
}
