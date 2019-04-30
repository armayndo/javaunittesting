package com.mitrais.blog.unittest.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mitrais.blog.unittest.domain.Shelf;
import com.mitrais.blog.unittest.repo.ShelfRepository;

@RunWith(MockitoJUnitRunner.class)
public class ShelfServiceTest {
	@InjectMocks
    private ShelfService shelfService; // System Under Test
    @Mock
    private ShelfRepository shelfRepository; // Dependencies will be mocked
    
    @Test
    public void findAllItemTest(){
        // PREPARATION
        Mockito.when(shelfRepository.findAll()).thenReturn(Arrays.asList(
            new Shelf(1, Long.valueOf(10), Long.valueOf(4)),
            new Shelf(2, Long.valueOf(10), Long.valueOf(4)),
            new Shelf(3, Long.valueOf(10), Long.valueOf(4))
        ));

        // ACTION
        List<Shelf> shelfs = shelfService.findAll();

        // ASSERTION
        //assertEquals(expected, actual);
        assertEquals(3, shelfs.size());
    }
}
