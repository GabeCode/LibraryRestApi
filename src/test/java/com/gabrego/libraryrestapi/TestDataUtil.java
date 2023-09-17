package com.gabrego.libraryrestapi;

import com.gabrego.libraryrestapi.domain.entities.Author;
import com.gabrego.libraryrestapi.domain.entities.Book;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Gabriel")
                .age(25)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Andie")
                .age(23)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Mario")
                .age(21)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-1245-1")
                .title("Beyond the Horizon")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-8976-2")
                .title("The Last Ember")
                .author(author)
                .build();
    }
}
