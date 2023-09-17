package com.gabrego.libraryrestapi.controllers;

import com.gabrego.libraryrestapi.domain.dto.AuthorDto;
import com.gabrego.libraryrestapi.domain.entities.Author;
import com.gabrego.libraryrestapi.mappers.Mapper;
import com.gabrego.libraryrestapi.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping()
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        Author authorEntity = authorMapper.mapFrom(author);
        authorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(authorEntity), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<AuthorDto> listAuthors() {
        List<Author> authors = authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }
}
