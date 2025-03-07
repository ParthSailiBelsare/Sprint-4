package com.example.library.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/manage-books")
    public String manageBooks() {
        return "admin/manage-books";  // This refers to src/main/resources/templates/admin/manage-books.html
    }
}
