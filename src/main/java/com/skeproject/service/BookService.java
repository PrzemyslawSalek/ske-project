package com.skeproject.service;

import com.skeproject.entity.Book;
import com.skeproject.exceptions.BookNotFoundException;
import com.skeproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {
    @Autowired private BookRepository bookRepository;

    public List<Book> listAll(){
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> getBooks(Set<Integer> ids){
        return (List<Book>) bookRepository.findAllById(ids);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book get(Integer id) throws BookNotFoundException {
        Optional<Book> result = bookRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new BookNotFoundException("Nie znaleziono książki o takim ID " + id);
    }

    public void delete(Integer id) throws BookNotFoundException {
        Long count = bookRepository.countById(id);
        if (count == null || count == 0) {
            throw new BookNotFoundException("Nie znaleziono książki o takim ID " + id);
        }
        bookRepository.deleteById(id);
    }


}
