package com.gabrego.libraryrestapi.services;

import com.gabrego.libraryrestapi.domain.entities.Book;

import java.util.List;

public interface BookService {

    Book createBook(String isbn, Book book);

    List<Book> findAll();
}
