package com.gabrego.libraryrestapi.services;

import com.gabrego.libraryrestapi.domain.entities.Author;
import org.springframework.stereotype.Service;


public interface AuthorService {
    Author createAuthor(Author author);
}
