package com.gabrego.libraryrestapi.controllers;

import com.gabrego.libraryrestapi.domain.dto.BookDto;
import com.gabrego.libraryrestapi.domain.entities.Book;
import com.gabrego.libraryrestapi.mappers.Mapper;
import com.gabrego.libraryrestapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        Book bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExist = bookService.isExist(isbn);
        bookEntity = bookService.saveBook(isbn, bookEntity);
        BookDto saveUpdateBookDto = bookMapper.mapTo(bookEntity);

        if (bookExist) {
            return new ResponseEntity<>(saveUpdateBookDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(saveUpdateBookDto, HttpStatus.CREATED);
        }


    }

    @GetMapping()
    public List<BookDto> listBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<Book> foundBook = bookService.findOne(isbn);
        return foundBook.map(book -> {
            BookDto bookDto = bookMapper.mapTo(book);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book book = bookMapper.mapFrom(bookDto);
        Book updatedBook = bookService.partialUpdate(isbn, book);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBook),HttpStatus.OK);

    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity deleteBook(@PathVariable String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
