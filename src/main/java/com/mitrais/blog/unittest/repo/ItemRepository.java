package com.mitrais.blog.unittest.repo;


import com.mitrais.blog.unittest.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}

