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
            if (role.equals("ROLE_ADMIN")) {
                return "redirect:/admin/home";
            } else if (role.equals("ROLE_LIBRARIAN")) {
                return "redirect:/librarian/home";
            } else if (role.equals("ROLE_USER")) {
                return "redirect:/user/home";
            }
        }
        return "redirect:/login";  // Default to login if no role matched
    }


    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin-home";
    }

    @GetMapping("/librarian/home")
    public String librarianHome() {
        return "librarian-home";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "user-home";
    }
}
