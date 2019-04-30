package com.mitrais.blog.unittest.service;

import com.mitrais.blog.unittest.domain.Item;
import com.mitrais.blog.unittest.repo.ItemRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService implements IItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAllItems() {
        List<Item> items = itemRepository.findAll();
        items.forEach(item -> item.setValue(item.getPrice() * item.getQuantity()));
        return items;
    }

    @Override
    public Item findOneItem(Integer id) throws NotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isPresent()){
            Item item = optionalItem.get();
            item.setValue(item.getPrice() * item.getQuantity());
            return item;
        }
        throw new NotFoundException("Not Found item with id " + id);
    }

    public boolean saveItem(Item item){
        try{
            itemRepository.save(item);
            return true;
        } catch (DataAccessException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
