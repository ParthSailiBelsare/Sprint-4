package com.example.library.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/redirectBasedOnRole")
    public String redirectBasedOnRole(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN":
                    return "redirect:/admin/dashboard";
                case "ROLE_LIBRARIAN":
                    return "redirect:/librarian/dashboard";
                case "ROLE_USER":
                    return "redirect:/user/dashboard";
            }
        }
        return "redirect:/login"; // Default redirect if no role is found
    }
}
