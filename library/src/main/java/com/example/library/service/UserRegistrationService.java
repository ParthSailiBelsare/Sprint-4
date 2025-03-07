package com.example.library.service;

import com.example.library.model.RegistrationForm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final InMemoryUserDetailsManager userDetailsManager;

    // ✅ Ensure we inject the correct bean
    public UserRegistrationService(InMemoryUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public void registerUser(RegistrationForm form) {
        String role = form.getRole().equalsIgnoreCase("librarian") ? "LIBRARIAN" : "USER";

        UserDetails newUser = User.builder()
                .username(form.getUsername())
                .password("{noop}" + form.getPassword()) // Use {noop} for plaintext (for dev only)
                .roles(role)
                .build();

        userDetailsManager.createUser(newUser);
    }
}
