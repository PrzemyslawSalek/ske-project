package com.skeproject.repository;

import com.skeproject.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Long countById(Integer id);
}
