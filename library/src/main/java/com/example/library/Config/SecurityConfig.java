package com.example.library.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Configure HTTP security with lambda-style configuration
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for simplicity in this example
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()  // Allow login and register pages to be accessed by everyone
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Only admins can access /admin/**
                        .requestMatchers("/librarian/**").hasRole("LIBRARIAN")  // Only librarians can access /librarian/**
                        .requestMatchers("/user/**").hasRole("USER")  // Only users can access /user/**
                        .anyRequest().authenticated()  // All other requests need authentication
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")  // Custom login page
                        .permitAll()  // Allow everyone to access login page
                        .defaultSuccessUrl("/redirectBasedOnRole", true)  // Redirect to the appropriate role-based page on successful login
                )
                .logout(LogoutConfigurer::permitAll);  // Allow everyone to log out
        return http.build();
    }

    // Define in-memory users for testing purposes
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))  // Encode password with BCrypt
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("librarian")
                .password(passwordEncoder().encode("librarian123"))
                .roles("LIBRARIAN")
                .build());
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build());
        return manager;
    }

    // Password encoder bean (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
