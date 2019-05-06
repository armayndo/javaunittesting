package com.mitrais.blog.unittest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mitrais.blog.unittest.domain.Book;
import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.service.IBookService;
import com.mitrais.blog.unittest.service.IShelfService;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {
	
	@Autowired
    private MockMvc mockMvc; // to mock http request
	
	@MockBean
	private IBookService bookService;
	
	@MockBean
	private IShelfService shelfService;
	
	@Test
    public void findAllBasic() throws Exception {
        // PREPARATION
    	Shelf shelf = new Shelf();
    	shelf.setId(1);
    	shelf.setCurrentCapacity(Long.parseLong("4"));
    	shelf.setMaxCapacity(Long.parseLong("10"));
        when(shelfService.findAll()).thenReturn(Arrays.asList(
        		shelf,
        		shelf,
        		shelf
        ));

        // ACTION AND ASSERTION AT ONCE
         mockMvc.perform(
                 MockMvcRequestBuilders.get("/api/lib/showall")) // GET /items as the request
                 .andExpect(status().isOk())                     // status expectation
                 .andExpect(content().json("[{}, {}, {}]"));   // content expectation as JSON
    }
	
	@Test
    public void findAllEmpty() throws Exception {
        // PREPARATION
        when(shelfService.findAll()).thenReturn(Collections.emptyList());

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/lib/showall")) // GET /items as the request
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("[]"));   // content expectation as JSON
    }
	
	@Test
	public void addBook_OK() throws Exception {
		//Prepatarion
		Book book = new Book(1, "isbn1","title1","aut1", false, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
		when(bookService.addBookIntoShelf(any(),any())).thenReturn(true);
		when(bookService.findOneBook(1)).thenReturn(book);
		
		// ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/lib/addBook/1/1");  // GET /items
        
        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("{id:1,isbn:isbn1,title:title1,author:aut1,shelf:{id:1,maxCapacity:10,currentCapacity:4},shelved:false}"));                    // status expectation
	}
	
	@Test
	public void addBook_NOTOK() throws Exception {
		//Prepatarion
		when(bookService.addBookIntoShelf(any(),any())).thenReturn(false);
		
		// ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/lib/addBook/1/1");  // GET /items
        
        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().is(400))
                //.andExpect(content().json("{id:null,isbn:null,title:null,author:null,shelf:null,shelved:false}"))                    // status expectation
                ;
	}
	
	@Test
	public void removeBook_OK() throws Exception {
		//Prepatarion
		Book book = new Book(1, "isbn1","title1","aut1", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
		when(bookService.removeBookFromShelf(any())).thenReturn(true);
		when(bookService.findOneBook(1)).thenReturn(book);
		
		// ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/lib/removeBook/1");  // GET /items
        
        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("{id:1,isbn:isbn1,title:title1,author:aut1,shelf:{id:1,maxCapacity:10,currentCapacity:4},shelved:true}"));                    // status expectation
	}
	
	@Test
	public void removeBook_NOTOK() throws Exception {
		//Prepatarion
		//when(bookService.removeBookFromShelf(any())).thenReturn(false);
		
		// ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/lib/removeBook/1");  // GET /items
        
        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().is(400))
                //.andExpect(content().json("{id:null,isbn:null,title:null,author:null,shelf:null,shelved:false}"))                    // status expectation
                ;
	}
	
	@Test
    public void addShelf_OK() throws Exception {
        // PREPARATION
    	RequestBuilder request = MockMvcRequestBuilders
                .post("/api/lib/addshelf")  // Post /items
                .param("maxcap", "3")
                ;

        // ACTION AND ASSERTION AT ONCE
         mockMvc.perform(request) // Post /items as the request
                 .andExpect(status().isCreated())                     // status expectation
                 ;
    }


}
