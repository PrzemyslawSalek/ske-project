package com.skeproject.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.skeproject.entity.Book;
import com.skeproject.entity.Order;
import com.skeproject.entity.User;
import com.skeproject.service.OrderService;
import com.skeproject.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

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
    public String order(@PathVariable("status") String status, @PathVariable("id") Integer id, Model model) {
        System.out.println("status " + status + " paymentId " + id);
        if(status.equals("success")) {
            orderService.changeStatus(id, "opłacone");
            model.addAttribute("books", orderService.getBooks((Integer) id));
            return "order";
        } else {
            return "cancel";
        }
    }

//    @PostMapping("/pay")
//    public String payment(@ModelAttribute("order") Order order) {
//        try {
//            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
//                    order.getIntent(), order.getDescription(), "http://localhost:9090/" + CANCEL_URL,
//                    "http://localhost:9090/" + SUCCESS_URL);
//            for(Links link:payment.getLinks()) {
//                if(link.getRel().equals("approval_url")) {
//                    return "redirect:"+link.getHref();
//                }
//            }
//
//        } catch (PayPalRESTException e) {
//
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping(value = CANCEL_URL)
//    public String cancelPay() {
//        return "cancel";
//    }
//
//    @GetMapping(value = SUCCESS_URL)
//    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//        try {
//            Payment payment = service.executePayment(paymentId, payerId);
//            System.out.println(payment.toJSON());
//            if (payment.getState().equals("approved")) {
//                return "success";
//            }
//        } catch (PayPalRESTException e) {
//            System.out.println(e.getMessage());
//        }
//        return "redirect:/";
//    }
}