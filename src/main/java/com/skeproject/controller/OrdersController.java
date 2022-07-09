package com.skeproject.controller;

import com.skeproject.entity.User;
import com.skeproject.service.OrderService;
import com.skeproject.service.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final SecurityUserDetailsService userService;

    public OrdersController(OrderService orderService, SecurityUserDetailsService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String orders(HttpServletRequest request, Model model) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("orders", orderService.getAllOrders());
        } else {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = null;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }
            model.addAttribute("orders", orderService.getOrdersByUser((User) userService.loadUserByUsername(username)));
        }
        return "orders";
    }

    @PostMapping("/complete")
    public String completeOrder(@RequestParam(name = "orderId") int id, RedirectAttributes redirectAttributes) {
        orderService.completeOrder(id);
        redirectAttributes.addFlashAttribute("message", "Skompletowano zamówienie o numerze " + id + ".");
        return "redirect:/orders";
    }
}