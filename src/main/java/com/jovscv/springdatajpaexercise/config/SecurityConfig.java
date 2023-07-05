package com.jovscv.springdatajpaexercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String[] authenticatedRoutes = {"/api/exercise-2/add", "/api/exercise-2/{id}", "/api/exercise-2/courses/{id}", "/api/exercise-2/course"};

        http.cors().and()
                .csrf()
                .disable().formLogin()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/exercise-2/sample").hasAnyRole("ADMIN")
                .antMatchers(authenticatedRoutes).hasRole("ADMIN")
                .antMatchers("/api/exercise-2/all").hasAnyAuthority("STUDENT", "ADMIN");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory user store
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("password")
                        .roles("STUDENT")
                        .build(),
                User.withUsername("admin")
                        .password("adminpassword")
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.build();
//    }
}
