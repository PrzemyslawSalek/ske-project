package com.skeproject.controller;

import com.skeproject.entity.Cart;
import com.skeproject.entity.Order;
import com.skeproject.entity.User;
import com.skeproject.service.BookService;
import com.skeproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final Cart cart;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final OrderService orderService;

    public CartController(Cart cart, BookService bookService, OrderService orderService) {
        this.cart = cart;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("books", bookService.getBooks(cart.getBookIds()));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam(name = "bookId") int id) {
        cart.addBookId(id);
        return "redirect:/cart";
    }

    @PostMapping("/delete")
    public String deleteFromCart(@RequestParam(name = "bookId") int id) {
        cart.deleteBookId(id);
        return "redirect:/books";
    }

    @PostMapping("/save")
    public String saveOrder(Authentication authentication, RedirectAttributes redirectAttributes) {
        Order order = new Order();
        order.setStatus("złożone");
        order.setUser(new User(authentication.getName()));
        order.setBooks(new HashSet<>(bookService.getBooks(cart.getBookIds())));
        orderService.save(order);
        redirectAttributes.addFlashAttribute("orderID", order.getId());
        System.out.println(order + "order");
        cart.getBookIds().clear();

        return "redirect:/order";
    }
}
