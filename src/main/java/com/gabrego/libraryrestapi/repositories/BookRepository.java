package com.gabrego.libraryrestapi.repositories;

import com.gabrego.libraryrestapi.domain.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
