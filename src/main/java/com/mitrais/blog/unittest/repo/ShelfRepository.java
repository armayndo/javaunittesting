package com.mitrais.blog.unittest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.blog.unittest.domain.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

}
