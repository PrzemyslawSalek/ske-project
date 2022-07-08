package com.skeproject.service;

import com.skeproject.entity.Book;
import com.skeproject.entity.Order;
import com.skeproject.exceptions.BookNotFoundException;
import com.skeproject.exceptions.OrderNotFoundException;
import com.skeproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return (List<Order>) orderRepository.findAll();
    }

    public List<Order> getOrders(Set<Integer> ids){
        return (List<Order>) orderRepository.findAllById(ids);
    }

    public Set<Book> getBooks(Integer id){
        Optional<Order> result = orderRepository.findById(id);
        if(result.isPresent()) {
            Order order = result.get();
            return order.getBooks();
        }
        return null;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }


    public void completeOrder(Integer id) {
        Order order = orderRepository.findById(id).orElse(new Order());;
        order.setCompleted(true);
        orderRepository.save(order);
    }

    public void changeStatus(Integer id, String status) {
        Order order = orderRepository.findById(id).orElse(new Order());;
        order.setStatus(status);
        orderRepository.save(order);
    }
}
