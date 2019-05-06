package com.mitrais.blog.unittest.service;

import java.util.List;

import com.mitrais.blog.unittest.domain.Book;

import javassist.NotFoundException;

public interface IBookService {
	
	List<Book> findAllBooks();
	
	List<Book> findBooks(String title, String status);
	
	Book findOneBook(Integer id) throws NotFoundException;
	
	boolean addBookIntoShelf (Book book, Integer shelfId);
	
	boolean removeBookFromShelf(Book book);
	
	Book addBook(Book book);

}
