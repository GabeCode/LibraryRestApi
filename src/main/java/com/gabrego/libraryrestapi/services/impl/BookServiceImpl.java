package com.gabrego.libraryrestapi.services.impl;

import com.gabrego.libraryrestapi.domain.entities.Book;
import com.gabrego.libraryrestapi.repositories.BookRepository;
import com.gabrego.libraryrestapi.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
