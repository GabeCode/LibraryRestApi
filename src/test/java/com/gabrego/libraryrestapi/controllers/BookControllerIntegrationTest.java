package com.gabrego.libraryrestapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrego.libraryrestapi.TestDataUtil;
import com.gabrego.libraryrestapi.domain.entities.Book;
import com.gabrego.libraryrestapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {

        Book testBookA = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookA.getTitle())
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        Book testBook = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBook.getIsbn(), testBook);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook.getTitle())
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200OkWhenBookExist() throws Exception {
        Book testBook = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBook.getIsbn(), testBook);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404NotFoundWhenNoBookExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/123-43253-1232")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnsBookWhenBookExist() throws Exception {
        Book testBook = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBook.getIsbn(), testBook);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook.getTitle())
        );
    }

    @Test
    public void testThatFullUpdateBookReturnsHttpStatus200OkWhenBookExist() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBookA.getIsbn(),testBookA);

        Book testBookB = TestDataUtil.createTestBookB(null);
        testBookB.setIsbn(testBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateBookUpdatesBook() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBookA.getIsbn(),testBookA);

        Book testBookB = TestDataUtil.createTestBookB(null);
        testBookB.setIsbn(testBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookB.getTitle())
        );

    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200OkWhenBookExist() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBookA.getIsbn(),testBookA);

        Book testBookB = TestDataUtil.createTestBookB(null);
        testBookB.setIsbn(testBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus404NotFoundWhenNoBookExist() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);

        Book testBookB = TestDataUtil.createTestBookB(null);
        testBookB.setIsbn(testBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBookA.getIsbn(),testBookA);

        Book bookToUpdate = TestDataUtil.createTestBookB(null);
        bookToUpdate.setIsbn(testBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookToUpdate);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookToUpdate.getTitle())
        );

    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForNonExistingBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/123-123-123")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForExistingBook() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.saveBook(testBookA.getIsbn(),testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }


}
