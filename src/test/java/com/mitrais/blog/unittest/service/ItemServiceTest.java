package com.mitrais.blog.unittest.service;

import com.mitrais.blog.unittest.domain.Item;
import com.mitrais.blog.unittest.repo.ItemRepository;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService itemService; // System Under Test
    @Mock
    private ItemRepository itemRepository; // Dependencies will be mocked

    @Test
    public void findAllItemTest(){
        // PREPARATION
        Mockito.when(itemRepository.findAll()).thenReturn(Arrays.asList(
            new Item(1, "Ball 1", 100, 10),
            new Item(2, "Ball 2", 100, 20),
            new Item(3, "Ball 3", 100, 30)
        ));

        // ACTION
        List<Item> allItems = itemService.findAllItems();

        // ASSERTION
        assertEquals(1000, allItems.get(0).getValue(), 0.00000001);
        assertEquals(2000, allItems.get(1).getValue(), 0.00000001);
        assertEquals(3000, allItems.get(2).getValue(), 0.00000001);
    }

    @Test
    public void findItemBasic() throws NotFoundException {
        // PREPARATION
        Mockito.when(itemRepository.findById(any())).thenReturn(
                Optional.of(new Item(1, "Ball 1", 10, 1000))
        );

        // ACTION
        Item item = itemService.findOneItem(1);

        // ASSERTION
        assertEquals(10000, item.getValue(), 0.0000001);
    }

    @Test(expected = NotFoundException.class)
    public void findItemNotFound() throws NotFoundException {
        // PREPARATION
        Mockito.when(itemRepository.findById(1)).thenReturn(
                Optional.empty()
        );

        // ACTION
        Item item = itemService.findOneItem(1);

        // ASSERTION never happen because exception thrown

    }

    @Test
    public void save_Basic_OK(){
        Item item = new Item(1, "Ball 1", 100, 10);
        Mockito.when(itemRepository.save(any())).thenReturn(item);

        boolean result = itemService.saveItem(item);
        assertTrue(result);
    }

    @Test
    public void save_Basic_NOTOK(){
        Item item = new Item("Ball 1", 100, 10);
        Mockito.when(itemRepository.save(any()))
                .thenThrow(new DataIntegrityViolationException("Data Error"));

        // action
        boolean result = itemService.saveItem(item);
        assertFalse(result);
    }
}