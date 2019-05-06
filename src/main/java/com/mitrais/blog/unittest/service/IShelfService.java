package com.mitrais.blog.unittest.service;

import java.util.List;

import com.mitrais.blog.unittest.domain.Shelf;

import javassist.NotFoundException;

public interface IShelfService {

	List<Shelf> findAll();
	
	Shelf addShelf(Shelf shelf);
	
	Shelf findShelfById(Integer id) throws NotFoundException;
}
