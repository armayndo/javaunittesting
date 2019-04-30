package com.mitrais.blog.unittest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.repo.ShelfRepository;

@Service
public class ShelfService implements IShelfService {
	
	private ShelfRepository shelfRepository;


	@Autowired
	public ShelfService(ShelfRepository shelfRepository) {
		this.shelfRepository = shelfRepository;
	}

	@Override
	public List<Shelf> findAll() {
		return shelfRepository.findAll();
	}

}
