package com.gabrego.libraryrestapi.services;

import com.gabrego.libraryrestapi.domain.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book saveBook(String isbn, Book book);

    List<Book> findAll();

    Page<Book> findAll(Pageable pageable);

    Optional<Book> findOne(String isbn);

    boolean isExist(String isbn);

    Book partialUpdate(String isbn, Book book);

    void delete(String isbn);
}
