package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.BorrowHistory;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowHistoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final BookRepository bookRepository;
    private final BorrowHistoryRepository historyRepository;

    public UserController(BookRepository bookRepository, BorrowHistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/home")
    public String userHome(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "user-home";
    }

    @PostMapping("/borrow-book")
    public String borrowBook(@RequestParam String userId, @RequestParam String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);

            BorrowHistory history = new BorrowHistory();
            history.setUserId(userId);
            history.setBookId(bookId);
            history.setBorrowedAt(LocalDateTime.now());
            historyRepository.save(history);
        }
        return "redirect:/user/home";
    }

    @PostMapping("/return-book")
    public String returnBook(@RequestParam String userId, @RequestParam String bookId) {
        BorrowHistory history = historyRepository.findByUserId(userId).stream()
                .filter(h -> h.getBookId().equals(bookId) && h.getReturnedAt() == null)
                .findFirst().orElseThrow();

        history.setReturnedAt(LocalDateTime.now());
        historyRepository.save(history);

        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setAvailable(true);
        bookRepository.save(book);

        return "redirect:/user/home";
    }
}
