package com.mitrais.blog.unittest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.blog.unittest.domain.Book;
import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.service.IBookService;
import com.mitrais.blog.unittest.service.IShelfService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/lib")
public class LibraryController {
	
	private IBookService bookService;
	private IShelfService shelfService;

	//@Autowired
	public LibraryController(IBookService bookService, IShelfService shelfService) {
		this.bookService = bookService;
		this.shelfService = shelfService;
	}
	
	@GetMapping("/showall")
	public List<Shelf> findAll(){
		return shelfService.findAll();
	}
	
	@GetMapping("/addBook/{1}")
	public ResponseEntity<Book> addBookById(@PathVariable("1") String id){
		Book theBook = new Book();
		
        try {
        	theBook = bookService.findOneBook(Integer.valueOf(id));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } 
        if(bookService.addBookIntoShelf(theBook)) {
        	return ResponseEntity.ok(theBook);
        }
        else {
        	return ResponseEntity.ok(new Book());
        }
        
        
    }
	
	@GetMapping("/removeBook/{1}")
	public ResponseEntity<Book> removeBookById(@PathVariable("1") String id){
		Book theBook = new Book();
		
        try {
        	theBook = bookService.findOneBook(Integer.valueOf(id));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } 
        
        if(bookService.removeBookFromShelf(theBook)) {
        	return ResponseEntity.ok(theBook);
        }
        else {
        	return ResponseEntity.ok(new Book());
        }
    }

}
