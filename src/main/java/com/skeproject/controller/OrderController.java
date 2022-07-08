package com.skeproject.controller;

import com.skeproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String order(Model model) {
        System.out.println("model" + model.getAttribute("orderID"));
        model.addAttribute("books", orderService.getBooks((Integer) model.getAttribute("orderID")));
        return "order";
    }
}