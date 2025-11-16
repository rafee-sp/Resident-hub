package com.rafee.residenthub.config;

import com.rafee.residenthub.security.JwtAuthenticationProvider;
import com.rafee.residenthub.security.filters.JwtAuthenticationFilter;
import com.rafee.residenthub.security.filters.JwtRefreshTokenFilter;
import com.rafee.residenthub.security.filters.JwtValidationFilter;
import com.rafee.residenthub.service.UserService;
import com.rafee.residenthub.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserService userService;
    private final JwtService jwtService;
    private final AppConfig appConfig;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, JwtService jwtService, AppConfig appConfig, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.appConfig = appConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain loginChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authenticationManager, jwtService);

        return http.securityMatcher("/login")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();

    }

    @Bean
    public SecurityFilterChain refreshTokenChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{

        JwtRefreshTokenFilter jwtRefreshTokenFilter = new JwtRefreshTokenFilter(authenticationManager, jwtService);

        return http.securityMatcher("/refresh-token")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(jwtRefreshTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain logoutFilter(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        return http.securityMatcher("/logout")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .logout(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    SecurityFilterChain publicSecurity(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/callback/**", "/actuator/health") // can be used for all public endpoint
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .build();
    }

    @Bean
    public SecurityFilterChain apiChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{

        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager);

        return http.securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();

    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider(jwtService, userService);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Arrays.asList(
                daoAuthenticationProvider(),
                jwtAuthenticationProvider()
        ));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(appConfig.getFrontendUrl()));
        configuration.setAllowedMethods(List.of("POST","GET", "PUT", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
