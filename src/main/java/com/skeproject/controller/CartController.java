package com.skeproject.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.skeproject.entity.Cart;
import com.skeproject.entity.Order;
import com.skeproject.entity.User;
import com.skeproject.exceptions.BookNotFoundException;
import com.skeproject.service.BookService;
import com.skeproject.service.OrderService;
import com.skeproject.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private final PaypalService paypalService;

    public CartController(Cart cart, BookService bookService, OrderService orderService, PaypalService paypalService) {
        this.cart = cart;
        this.bookService = bookService;
        this.orderService = orderService;
        this.paypalService = paypalService;
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("books", bookService.getBooks(cart.getBookIds()));
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam(name = "bookId") int id, RedirectAttributes redirectAttributes) {
        try {
            cart.addBookId(id);
            cart.setPrice(cart.getPrice() + bookService.get(id).getPrice());
            redirectAttributes.addFlashAttribute("message",
                    "Dodano do koszyka - " + bookService.get(id).getName());
            return "redirect:/books";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/books";
        }
    }

    @PostMapping("/delete")
    public String deleteFromCart(@RequestParam(name = "bookId") int id, RedirectAttributes redirectAttributes) {
        try {
            cart.deleteBookId(id);
            cart.setPrice(cart.getPrice() - bookService.get(id).getPrice());
            redirectAttributes.addFlashAttribute("message",
                    "Usunięto z koszyka - " + bookService.get(id).getName());
            return "redirect:/books";
        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/books";
        }
    }

    @PostMapping("/save")
    public String saveOrder(Authentication authentication, RedirectAttributes redirectAttributes) {
        Order order = new Order();
        order.setStatus("złożone");
        order.setUser(new User(authentication.getName()));
        order.setBooks(new HashSet<>(bookService.getBooks(cart.getBookIds())));
        orderService.save(order);
        cart.getBookIds().clear();

        return "redirect:/order";
    }

    @PostMapping("/pay")
    public String payment(Authentication authentication) {
        try {
            Order order = new Order();
            order.setStatus("złożone");
            order.setPrice(cart.getPrice());
            order.setCurrency("PLN");
            order.setMethod("paypal");
            order.setIntent("SALE");
            order.setDescription("Order ID: " + order.getId());
            order.setUser(new User(authentication.getName()));
            order.setBooks(new HashSet<>(bookService.getBooks(cart.getBookIds())));
            orderService.save(order);
            cart.getBookIds().clear();
            cart.setPrice(0);

            Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/order/cancel/" + order.getId(),
                    "http://localhost:8080/order/success/" + order.getId());
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    System.out.println(payment.getLinks() + " +  " + link.getHref() + " links");
                    return "redirect:" + link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
