package com.skeproject.controller;

import com.skeproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @PostMapping("/complete")
    public String completeOrder(@RequestParam(name = "orderId") int id, RedirectAttributes redirectAttributes) {
        orderService.completeOrder(id);
        redirectAttributes.addFlashAttribute("message", "Książka o numerze " + id + " została usunięta.");
        return "redirect:/orders";
    }
}