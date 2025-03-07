package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminBookController {
    private final BookRepository bookRepository;

    public AdminBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/manage-books")
    public String showManageBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "admin/manage-books";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/admin/manage-books";
    }

    @PostMapping("/remove-book")
    public String removeBook(@RequestParam String id) {
        bookRepository.deleteById(id);
        return "redirect:/admin/manage-books";
    }
}
