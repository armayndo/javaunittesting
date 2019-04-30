package com.mitrais.blog.unittest.service;

import com.mitrais.blog.unittest.domain.Item;
import javassist.NotFoundException;

import java.util.List;

public interface IItemService {
    List<Item> findAllItems();
    Item findOneItem(Integer id) throws NotFoundException;
}

