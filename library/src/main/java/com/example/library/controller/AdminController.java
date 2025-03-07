package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.InMemoryBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final InMemoryBookService bookService;

    public AdminController(InMemoryBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/dashboard";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/remove-book")
    public String removeBook(@RequestParam String id) {
        bookService.remove(id);
        return "redirect:/admin/dashboard";
    }
}
