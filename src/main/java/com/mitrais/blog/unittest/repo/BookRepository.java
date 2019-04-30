package com.mitrais.blog.unittest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.blog.unittest.domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findByIsShelved(boolean isShelved);
	
	List<Book> findByTitleAndIsShelved(String title, boolean isShelved);
	
	List<Book> findByTitleContaining(String title);

}
