package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.InMemoryBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final InMemoryBookService bookService;

    public UserController(InMemoryBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "user/dashboard";
    }

    @PostMapping("/borrow-book")
    public String borrowBook(@RequestParam String bookId) {
        Book book = bookService.findById(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/return-book")
    public String returnBook(@RequestParam String bookId) {
        Book book = bookService.findById(bookId);
        if (book != null) {
            book.setAvailable(true);
        }
        return "redirect:/user/dashboard";
    }
}
