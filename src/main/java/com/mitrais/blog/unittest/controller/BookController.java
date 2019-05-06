package com.mitrais.blog.unittest.controller;

import java.util.List;

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
import com.mitrais.blog.unittest.domain.Item;
import com.mitrais.blog.unittest.service.IBookService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/book")
public class BookController {
	
	private IBookService bookService;

	public BookController(IBookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/showall")
	public List<Book> findAll(){
		return bookService.findAllBooks();
	}
	
	@GetMapping("/findbyid/{1}")
	public ResponseEntity<Book> findBookById(@PathVariable("1") String id){
        try {
            return ResponseEntity.ok(bookService.findOneBook(Integer.valueOf(id)));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } 
    }
	
	@GetMapping("/find")
	public ResponseEntity<List<Book>> findBook(@RequestParam(value="title",required=false) String title,
			@RequestParam(value="status",required=false) String status) {
		List<Book> rstBook = bookService.findBooks(title, status);
		
		//ResponseEntity<List<Book>> responseEntity = new ResponseEntity<>(rstBook, HttpStatus.OK);
		
		return ResponseEntity.ok(rstBook);
	}
	
	@PostMapping("/regbook")
	public ResponseEntity<?> regBook(UriComponentsBuilder b ,
			@RequestParam(value="title",required=true) String title ,
			@RequestParam(value="isbn",required=true) String isbn ,
			@RequestParam(value="aut",required=true) String aut) {
		Book newBook = new Book();
		newBook.setAuthor(aut);
		newBook.setIsbn(isbn);
		newBook.setTitle(title);
		newBook.setShelf(null);
		newBook = bookService.addBook(newBook);
		
		//UriComponents uriComponents = b.path("/findbyid/{id}").buildAndExpand(newBook.getId());
		UriComponents uriComponents = b.path("/showall").build();

		return ResponseEntity.created(uriComponents.toUri()).build();
	}
	
	

}
