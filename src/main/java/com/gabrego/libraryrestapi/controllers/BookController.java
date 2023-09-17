package com.gabrego.libraryrestapi.controllers;

import com.gabrego.libraryrestapi.domain.dto.BookDto;
import com.gabrego.libraryrestapi.domain.entities.Book;
import com.gabrego.libraryrestapi.mappers.Mapper;
import com.gabrego.libraryrestapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    private Mapper<Book, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {

        Book bookEntity = bookMapper.mapFrom(bookDto);
        bookEntity = bookService.createBook(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(bookEntity), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<BookDto> listBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }
}
