package com.skeproject.controller;

import com.skeproject.service.OrderService;
import com.skeproject.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final PaypalService paypalService;

    public OrderController(OrderService orderService, PaypalService paypalService) {
        this.orderService = orderService;
        this.paypalService = paypalService;
    }

    @GetMapping("/{status}/{id}")
    public String order(@PathVariable("status") String status, @PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("status " + status + " paymentId " + id);
        if (status.equals("success")) {
            orderService.changeStatus(id, "opłacone");
            model.addAttribute("message", "Poprawnie złożone zamówienie!");
            model.addAttribute("books", orderService.getBooks(id));
            return "order";
        } else {
            orderService.changeStatus(id, "nieopłacone");
            redirectAttributes.addFlashAttribute("message_fail", "Twoje zamówienie nie zostało opłacone!");
            return "redirect:/orders";
        }
    }
}