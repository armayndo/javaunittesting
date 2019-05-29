package com.mitrais.blog.unittest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mitrais.blog.unittest.domain.Book;
import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.service.IBookService;
import com.mitrais.blog.unittest.service.IShelfService;

import javassist.NotFoundException;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
	
	@Autowired
    private MockMvc mockMvc; // to mock http request

    @MockBean
    private IBookService bookService;
    
    @MockBean
	private IShelfService shelfService;

    @Test
    public void findAllBasic() throws Exception {
        // PREPARATION
    	Book book = new Book();
    	book.setId(2);
    	book.setIsbn("isbn2");
    	book.setTitle("title2");
    	book.setAuthor("aut2");
    	book.setShelved(true);
    	Shelf shelf = new Shelf();
    	shelf.setId(1);
    	shelf.setCurrentCapacity(Long.parseLong("4"));
    	shelf.setMaxCapacity(Long.parseLong("10"));
    	book.setShelf(shelf);
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(
        		book,
        		book,
        		book
        ));

        // ACTION AND ASSERTION AT ONCE
         mockMvc.perform(
                 MockMvcRequestBuilders.get("/api/book/showall")) // GET /items as the request
                 .andExpect(status().isOk())                     // status expectation
                 .andExpect(content().json("[{}, {}, {}]"));   // content expectation as JSON
    }

    @Test
    public void findAllEmpty() throws Exception {
        // PREPARATION
        when(bookService.findAllBooks()).thenReturn(Collections.emptyList());

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/book/showall")) // GET /items as the request
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("[]"));   // content expectation as JSON
    }

    @Test
    public void findById_Basic() throws Exception {
        // PREPARATION
    	Book book = new Book();
    	book.setId(2);
    	book.setIsbn("isbn2");
    	book.setTitle("title2");
    	book.setAuthor("aut2");
    	book.setShelved(true);
    	Shelf shelf = new Shelf();
    	shelf.setId(1);
    	shelf.setCurrentCapacity(Long.parseLong("4"));
    	shelf.setMaxCapacity(Long.parseLong("10"));
    	book.setShelf(shelf);
       when(bookService.findOneBook(2)).thenReturn(book);

        // ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/book/findbyid/2");  // GET /items

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("{id:2,isbn:isbn2,title:title2,author:aut2,shelf:{id:1,maxCapacity:10,currentCapacity:4},shelved:true}"))             // content expectation as JSON
                ;
    }

    @Test
    public void findById_NotFound() throws Exception {
        // PREPARATION
        when(bookService.findOneBook(0)).thenThrow(NotFoundException.class);

        // ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/book/findbyid/0");  // GET /items

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().isNotFound());                    // status expectation
    }
    
    @Test
    public void regBook_OK() throws Exception {
        // PREPARATION
        when(bookService.addBook(any())).thenReturn(new Book());
    	RequestBuilder request = MockMvcRequestBuilders
                .post("/api/book/regbook")  // Post /items
                .param("title", "testTitle")
                .param("isbn", "123")
                .param("aut", "testAut")
                ;

        // ACTION AND ASSERTION AT ONCE
         mockMvc.perform(request) // Post /items as the request
                 .andExpect(status().isCreated())                     // status expectation
//                 .andExpect(header().string("location", matcher))
                 ;
    }

}
