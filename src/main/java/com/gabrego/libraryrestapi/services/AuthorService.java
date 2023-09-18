package com.gabrego.libraryrestapi.services;

import com.gabrego.libraryrestapi.domain.entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorService {
    Author saveAuthor(Author author);

    List<Author> findAll();

    Optional<Author> findOne(Long id);

    boolean isExist(Long id);

    Author partialUpdate(Long id, Author author);
}
