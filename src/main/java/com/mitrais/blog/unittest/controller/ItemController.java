package com.mitrais.blog.unittest.controller;

import com.mitrais.blog.unittest.domain.Item;
import com.mitrais.blog.unittest.service.IItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private IItemService itemService;

    @Autowired
    public void setItemService(IItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public List<Item> findAll(){
        return itemService.findAllItems();
    }

    @GetMapping("/item/{1}")
    public ResponseEntity<Item> findItem(@PathVariable("1") String id){
        try {
            return ResponseEntity.ok(itemService.findOneItem(Integer.valueOf(id)));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
