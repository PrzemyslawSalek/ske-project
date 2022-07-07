package com.skeproject.controller;

import com.skeproject.entity.Book;
import com.skeproject.exceptions.BookNotFoundException;
import com.skeproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String showBookList(Model model) {
        List<Book> listBooks = bookService.listAll();
        model.addAttribute("listBooks", listBooks);
        return "books";
    }

    @GetMapping("/books/add")
    public String showNewForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("pageTitle", "Dodaj nową książkę!");
        return "books_form";
    }

    @PostMapping("/books/save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes) {
        bookService.save(book);
        redirectAttributes.addFlashAttribute("message", "Książka dodana poprawnie!");
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.get(id);
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Edytuj książkę (ID: " + id + ")");
            return "books_form";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Książka o numerze " + id + " została usunięta.");
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/books";
    }
}
