package com.mitrais.blog.unittest.repo;

import com.mitrais.blog.unittest.domain.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        List<Item> items = Arrays.asList(
                new Item(4, "Ball 1", 100, 10),
                new Item(5, "Ball 2", 100, 20),
                new Item(6, "Ball 3", 100, 30)
        );
        itemRepository.saveAll(items);
    }

    @After
    public void tearDown() throws Exception {
        itemRepository.deleteAll();
    }

    @Test
    public void testFindAll(){
        // ACTION
        List<Item> all = itemRepository.findAll();

        // ASSERTION
        assertEquals(3, all.size());
    }

    @Test
    public void testFindById_Basic(){
        // ACTION
        Optional<Item> optItem = itemRepository.findById(1);

        // ASSERTION
        assertTrue(optItem.isPresent());
    }

    @Test
    public void testFindById_NotFound(){
        // ACTION
        Optional<Item> optItem = itemRepository.findById(10);

        // ASSERTION
        assertFalse(optItem.isPresent());
    }
}