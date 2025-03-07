package com.example.library.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll() // Allow login/register
                        .requestMatchers("/admin/**").hasRole("ADMIN")      // Admin access
                        .requestMatchers("/librarian/**").hasRole("LIBRARIAN") // Librarian access
                        .requestMatchers("/user/**").hasRole("USER")         // User access
                        .anyRequest().authenticated()                        // Authenticate others
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")        // Custom login page
                        .defaultSuccessUrl("/redirectBasedOnRole", true) // Redirect by role
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll); // Allow logout for everyone
        return http.build();
    }

    // ✅ Ensure the InMemoryUserDetailsManager is registered as a bean
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123")) // Use encoded password
                .roles("ADMIN")
                .build();

        UserDetails librarian = User.builder()
                .username("librarian")
                .password(passwordEncoder().encode("librarian123"))
                .roles("LIBRARIAN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, librarian, user);
    }

    // ✅ Define the password encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
