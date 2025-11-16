package com.rafee.residenthub.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rafee.residenthub.dto.response.ErrorResponse;
import com.rafee.residenthub.exception.UnAuthorizedException;
import com.rafee.residenthub.security.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtValidationFilter extends OncePerRequestFilter {
  
    private final AuthenticationManager authenticationManager;
    
    public JwtValidationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = extractTokenFromRequest(request);

            if (token == null) {
                throw new UnAuthorizedException("Access token is null");
            }
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String username = authentication.getName();
                if (username != null && !username.isBlank()) {
                    MDC.put("userId", username);
                }

                filterChain.doFilter(request, response);
            } else {
                throw new UnAuthorizedException("Access token is Invalid");
            }

        } catch (Exception ex) {

            ErrorResponse errorResponse = new ErrorResponse(401, "Access Token is Invalid");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } finally {
            MDC.remove("userId");
        }

    }

    private String extractTokenFromRequest(HttpServletRequest request) {

        String accessToken = request.getHeader("Authorization");

        if(accessToken != null && accessToken.startsWith("Bearer")){
           return accessToken.substring(7);
        }

        return null;
    }
}
