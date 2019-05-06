package com.mitrais.blog.unittest.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	@GetMapping("/addBook/{1}/{2}")
	public ResponseEntity<?> addBook(@PathVariable("1") String bookid, @PathVariable("2") String shelfid){
		Book theBook = new Book();
		
        try {
        	theBook = bookService.findOneBook(Integer.valueOf(bookid));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } 
        if(bookService.addBookIntoShelf(theBook, Integer.parseInt(shelfid))) {
        	return ResponseEntity.ok(theBook);
        }
        else {
        	return ResponseEntity.badRequest()
        			.body(Collections.singletonMap("error", "Data error, see console"));
        }
        
        
    }
	
	@GetMapping("/removeBook/{1}")
	public ResponseEntity<?> removeBookById(@PathVariable("1") String id){
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
        	return ResponseEntity.badRequest()
        			.body(Collections.singletonMap("error", "Sorry Book not available at the shelf"));
        }
    }
	
	@PostMapping("/addshelf")
	public ResponseEntity<?> addShelf(UriComponentsBuilder b ,
			@RequestParam(value="maxcap",required=true) Long maxcap) {
		Shelf newShelf = new Shelf();
		newShelf.setCurrentCapacity(0L);
		newShelf.setMaxCapacity(maxcap);
		newShelf = shelfService.addShelf(newShelf);
		
		UriComponents uriComponents = b.path("/showall").build();

		return ResponseEntity.created(uriComponents.toUri()).build();
	}

}
