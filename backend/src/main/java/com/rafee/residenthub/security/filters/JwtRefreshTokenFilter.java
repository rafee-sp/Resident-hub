package com.rafee.residenthub.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rafee.residenthub.dto.response.ErrorResponse;
import com.rafee.residenthub.entity.User;
import com.rafee.residenthub.exception.UnAuthorizedException;
import com.rafee.residenthub.security.JwtAuthenticationToken;
import com.rafee.residenthub.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtRefreshTokenFilter(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String refreshToken = extractRefreshTokenFromJWT(request);

            log.info("refreshToken {}", refreshToken);

            if (refreshToken == null) {
                throw new UnAuthorizedException("Refresh token is null");
            }

            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(refreshToken);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if (authentication.isAuthenticated()) {

                User user = (User) authentication.getPrincipal();

                String accessToken = jwtService.generateToken(authentication.getName(), true);

                Map<String, String> userMap = new HashMap<>();

                userMap.put("accessToken", accessToken);
                userMap.put("user", user.getUsername());

                response.setContentType("application/json");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), userMap);

            } else {
                throw new UnAuthorizedException("Refresh token is Invalid");
            }

        } catch (Exception ex) {

            ErrorResponse errorResponse = new ErrorResponse(401, "Refresh Token is Invalid");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }

    }

    private String extractRefreshTokenFromJWT(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if(cookies == null) return null;

        String refreshToken = null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        return refreshToken;
    }
}
