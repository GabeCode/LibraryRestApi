package com.gabrego.libraryrestapi.services;

import com.gabrego.libraryrestapi.domain.entities.Author;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AuthorService {
    Author createAuthor(Author author);

    List<Author> findAll();
}
