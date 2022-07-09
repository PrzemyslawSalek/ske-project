package com.skeproject.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class Cart {
    private final Set<Integer> bookIds = new HashSet<>();
    private double price = 0;

    public void addBookId(Integer id) {
        bookIds.add(id);
    }

    public void deleteBookId(Integer id) {
        bookIds.remove(id);
    }

    public Set<Integer> getBookIds() {
        return bookIds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}