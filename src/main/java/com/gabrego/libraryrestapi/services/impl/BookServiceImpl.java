package com.gabrego.libraryrestapi.services.impl;

import com.gabrego.libraryrestapi.domain.entities.Book;
import com.gabrego.libraryrestapi.repositories.BookRepository;
import com.gabrego.libraryrestapi.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Book> findAll() {
        return StreamSupport
                .stream(
                        bookRepository.findAll().spliterator(),
                        false
                ).collect(Collectors.toList());
    }
}
