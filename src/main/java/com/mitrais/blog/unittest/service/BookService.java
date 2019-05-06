package com.mitrais.blog.unittest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.blog.unittest.domain.Book;
import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.repo.BookRepository;
import com.mitrais.blog.unittest.repo.ShelfRepository;

import javassist.NotFoundException;

@Service
public class BookService implements IBookService {
	
	private BookRepository bookRepository;
	
	private ShelfRepository shelfRepository;

	@Autowired
	public BookService(BookRepository bookRepository, ShelfRepository shelfRepository) {
		this.bookRepository = bookRepository;
		this.shelfRepository = shelfRepository;
	}

	@Override
	public boolean addBookIntoShelf(Book book, Integer shelfId) {
		if(book.isShelved()) {
			System.out.println(">>> Sorry Book alerady at the shelf");
			return false;
		} else {
			Optional<Shelf> optionalShelf = shelfRepository.findById(shelfId);
			if(optionalShelf.isPresent()) {
				Shelf shelf = optionalShelf.get();
				
				if(shelf.getMaxCapacity() > shelf.getCurrentCapacity()) {
					book.setShelved(true);
					
					Long curCap = shelf.getCurrentCapacity();
					shelf.setCurrentCapacity(curCap+1);
					book.setShelf(shelf);
					bookRepository.save(book);
					return true;
				} else {
					System.out.println(">>> Sorry shelf is full");
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeBookFromShelf(Book book) {
		if(!book.isShelved()) {
			System.out.println(">>> Sorry Book not available at the shelf");
			return false;
		} else {
			Shelf shelf = book.getShelf();
			
			book.setShelved(false);
			
			Long curCap = shelf.getCurrentCapacity();
			shelf.setCurrentCapacity(curCap-1);
			book.setShelf(shelf);
			bookRepository.save(book);	
			book.setShelf(null);
			bookRepository.save(book);
			return true;
		}
	}

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book findOneBook(Integer id) throws NotFoundException {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if(optionalBook.isPresent()) {
			Book book = optionalBook.get();
			return book;
		}
		throw new NotFoundException("Not Found item with id " + id);
	}

	@Override
	public List<Book> findBooks(String title, String status) {
		List<Book> rstBook = new ArrayList<Book>();
		if(title != null && status != null) {
			if("shelved".equals(status.toLowerCase()) || "not_shelved".equals(status.toLowerCase())) {
				rstBook = bookRepository.findByTitleAndIsShelved(title, "shelved".equals(status.toLowerCase())?true:false);
			} 
		} else if(status != null){
			if("shelved".equals(status.toLowerCase()) || "not_shelved".equals(status.toLowerCase())) {
				rstBook = bookRepository.findByIsShelved("shelved".equals(status.toLowerCase())?true:false);
			}
		} else if(title != null){
			rstBook = bookRepository.findByTitleContaining(title);
		} else {
			rstBook = bookRepository.findAll();
		}
		
		return rstBook;
	}

	@Override
	public Book addBook(Book book) {
		Book Result = bookRepository.save(book); 
		return Result;
	}

}
