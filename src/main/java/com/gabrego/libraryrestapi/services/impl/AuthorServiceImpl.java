package com.gabrego.libraryrestapi.services.impl;

import com.gabrego.libraryrestapi.domain.entities.Author;
import com.gabrego.libraryrestapi.repositories.AuthorRepository;
import com.gabrego.libraryrestapi.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}
