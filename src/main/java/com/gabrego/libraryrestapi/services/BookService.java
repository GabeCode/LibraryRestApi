package com.gabrego.libraryrestapi.services;

import com.gabrego.libraryrestapi.domain.entities.Book;

public interface BookService {

    Book createBook(String isbn, Book book);
}
