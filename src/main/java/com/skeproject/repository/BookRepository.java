package com.skeproject.repository;

import com.skeproject.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
    public Long countById(Integer id);
}
