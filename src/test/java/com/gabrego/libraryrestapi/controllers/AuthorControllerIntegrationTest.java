package com.gabrego.libraryrestapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrego.libraryrestapi.TestDataUtil;
import com.gabrego.libraryrestapi.domain.entities.Author;
import com.gabrego.libraryrestapi.services.AuthorService;
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
public class AuthorControllerIntegrationTest {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        Author testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        Author testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Gabriel")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(25)
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200OkWhenAuthorExist() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus404NotFoundWhenNoAuthorExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200OkWhenAuthorExist() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthor);
        testAuthor.setAge(80);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404NotFoundWhenNoAuthorExist() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(testAuthor);
        testAuthor.setAge(80);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorUpdatesExistingAuthor() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor = authorService.saveAuthor(testAuthor);

        Author authorToUpdate = TestDataUtil.createTestAuthorB();

        String authorJson = objectMapper.writeValueAsString(authorToUpdate);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorToUpdate.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorToUpdate.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200OkWhenAuthorExist() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor = authorService.saveAuthor(testAuthor);

        Author authorToUpdate = TestDataUtil.createTestAuthorB();

        String authorJson = objectMapper.writeValueAsString(authorToUpdate);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus404NotFoundWhenNoAuthorExist() throws Exception {
        Author authorToUpdate = TestDataUtil.createTestAuthorB();
        String authorJson = objectMapper.writeValueAsString(authorToUpdate);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+authorToUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor = authorService.saveAuthor(testAuthor);

        Author authorToUpdate = TestDataUtil.createTestAuthorB();

        String authorJson = objectMapper.writeValueAsString(authorToUpdate);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorToUpdate.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorToUpdate.getAge())
        );
    }
}
