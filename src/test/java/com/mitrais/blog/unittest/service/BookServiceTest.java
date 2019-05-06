package com.mitrais.blog.unittest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import com.mitrais.blog.unittest.domain.Book;
import com.mitrais.blog.unittest.domain.Item;
import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.repo.BookRepository;
import com.mitrais.blog.unittest.repo.ShelfRepository;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
	
	 	@InjectMocks
	    private BookService bookService; // System Under Test
	 	@InjectMocks
	 	private ShelfService shelfService;
	    @Mock
	    private BookRepository bookRepository; // Dependencies will be mocked
	    @Mock
	    private ShelfRepository shelfRepository;
	    
	    @Test
	    public void findAllBookTest(){
	        // PREPARATION
	        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(
	            new Book(1, "isbn1","title1","aut1", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4))),
	            new Book(2, "isbn2","title2","aut2", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4))),
	            new Book(3, "isbn3","title3","aut3", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4)))
	        ));

	        // ACTION
	        List<Book> allBooks = bookService.findAllBooks();

	        // ASSERTION
	        //assertEquals(1000, allBooks.get(0).getValue(), 0.00000001);
	        //assertEquals(2000, allBooks.get(1).getValue(), 0.00000001);
	        //assertEquals(3000, allBooks.get(2).getValue(), 0.00000001);
	        //assertEquals(expected, actual);
	        assertEquals(3, allBooks.size());
	    }
	    
	    @Test
	    public void findOneBookBasic() throws NotFoundException {
	        // PREPARATION
	    	Book bookExp = new Book(1, "isbn1","title1","aut1", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
	        Mockito.when(bookRepository.findById(any())).thenReturn(
	                Optional.of(bookExp)
	        );

	        // ACTION
	        Book book = bookService.findOneBook(1);

	        // ASSERTION
	        assertEquals(bookExp, book);
	    }
	    
	    @Test(expected = NotFoundException.class)
	    public void findOneBookNotFound() throws NotFoundException {
	        // PREPARATION
	        Mockito.when(bookRepository.findById(1)).thenReturn(
	                Optional.empty()
	        );

	        // ACTION
	        Book book = bookService.findOneBook(1);

	        // ASSERTION never happen because exception thrown

	    }
	    
	    @Test
	    public void addBookIntoShelf_OK(){
	    	Book book = new Book(0, "isbn1","title1","aut1", false, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
	        Mockito.when(bookRepository.save(any())).thenReturn(book);
	        //Shelf shelf = new Shelf(1,2L,2L);
	        //Mockito.when(shelfRepository.findById(1)).thenReturn(shelf);

	        boolean result = bookService.addBookIntoShelf(book,1);
	        assertTrue(result);
	        //assertEquals(5L, book.getShelf().getCurrentCapacity().longValue());
	    }
	    
//	    @Test(expected=DataIntegrityViolationException.class)
//	    public void addBookIntoShelf_NOTOK(){
//	    	Book book = new Book(null, "isbn1","title1","aut1", false, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
//	        Mockito.when(bookRepository.save(any()))
//	                .thenThrow(new DataIntegrityViolationException("Data Error"));
//
//	        // action
//	        boolean result = bookService.addBookIntoShelf(book);
//	        assertFalse(result);
//	    }
	    
	    @Test
	    public void addBookIntoShelf_Already() {
	    	Book book = new Book(0, "isbn1","title1","aut1", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
	    	boolean rst = bookService.addBookIntoShelf(book,1);
	    	
	    	assertFalse(rst);
	    }
	    
	    @Test
	    public void addBookIntoShelf_MaxHit() {
	    	Book book = new Book(1, "isbn1","title1","aut1", false, null);
	    	boolean rst = bookService.addBookIntoShelf(book,3);
	    	
	    	assertFalse(rst);
	    }
	    
	    @Test
	    public void removeBookfromShelf_OK(){
	    	Book book = new Book(1, "isbn1","title1","aut1", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
	        Mockito.when(bookRepository.save(any())).thenReturn(book);

	        boolean result = bookService.removeBookFromShelf(book);
	        assertTrue(result);
	    }
	    
	    @Test //(expected=DataIntegrityViolationException.class)
	    public void removeBookFromShelf_NOTOK(){
	    	Book book = new Book(1, "isbn1","title1","aut1", false, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
	        //Mockito.when(bookRepository.save(any())).thenThrow(new DataIntegrityViolationException("Data Error"));

	        // action
	        boolean result = bookService.removeBookFromShelf(book);
	        assertFalse(result);
	    }
	    
	    @Test
	    public void removeBookFromShelf_NOTOK2(){
	    	Book book = new Book(1, "isbn1","title1","aut1", true, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));
	        //Mockito.when(bookRepository.save(any())).thenReturn(book);

	        // action
	        boolean result = bookService.addBookIntoShelf(book,1);
	        assertFalse(result);
	    }
	    
	    @Test
	    public void verifyFindByTitleAndIsShelved() {
	    	String status = "shelved";
	        String title = "title1";
	        
	    	bookService.findBooks(title, status);
	    	
	    	Mockito.verify(bookRepository).findByTitleAndIsShelved(title, "shelved".equals(status.toLowerCase())?true:false);
	    }
	    
	    @Test
	    public void verifyFindByIsShelved() {
	    	String status = "shelved";
	        String title = null;
	        
	    	bookService.findBooks(title, status);
	    	
	    	Mockito.verify(bookRepository).findByIsShelved("shelved".equals(status.toLowerCase())?true:false);
	    }
	    
	    @Test
	    public void verifyFindByTitleContaining() {
	    	String status = null;
	        String title = "title1";
	        
	    	bookService.findBooks(title, status);
	    	
	    	Mockito.verify(bookRepository).findByTitleContaining(title);
	    }
	    
	    @Test
	    public void verifyFindAll() {
	    	String status = null;
	        String title = null;
	        
	    	bookService.findBooks(title, status);
	    	
	    	Mockito.verify(bookRepository).findAll();
	    }
	    
	    @Test
	    public void addBook_OK(){
	    	//Book book = new Book(11, "isbn1","title1","aut1", false, null);
	    	Book book = new Book(1, "isbn1","title1","aut1", false, new Shelf(1, Long.valueOf(10), Long.valueOf(4)));

	        // action
	        Book result = bookService.addBook(book);
	    	//Book result = bookRepository.save(book);
	        
	        Mockito.verify(bookRepository).save(book);
	        //assertEquals(book, result);
	    }

}
