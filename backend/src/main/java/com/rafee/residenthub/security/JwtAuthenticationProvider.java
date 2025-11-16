package com.rafee.residenthub.security;


import com.rafee.residenthub.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationProvider(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = ((JwtAuthenticationToken) authentication).getToken();

        String userId = jwtService.validateAndExtractUserEmail(token);

        if(!StringUtils.hasText(userId)){
            throw new BadCredentialsException("Invalid JWT token");
        }

        UserDetails userDetails = userService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
