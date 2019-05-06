package com.mitrais.blog.unittest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.blog.unittest.domain.Book;
import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.repo.ShelfRepository;

import javassist.NotFoundException;

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

	@Override
	public Shelf addShelf(Shelf shelf) {
		return shelfRepository.save(shelf); 
	}

	@Override
	public Shelf findShelfById(Integer id) throws NotFoundException {
		Optional<Shelf> optionalShelf = shelfRepository.findById(id);
		if(optionalShelf.isPresent()) {
			Shelf shelf = optionalShelf.get();
			return shelf;
		}
		throw new NotFoundException("Not Found shelf with id " + id);
	}

}
