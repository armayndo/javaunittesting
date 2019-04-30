package com.mitrais.blog.unittest.controller;

import com.mitrais.blog.unittest.domain.Item;
import com.mitrais.blog.unittest.service.ItemService;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc; // to mock http request

    @MockBean
    private ItemService itemService;

    @Test
    public void findAllBasic() throws Exception {
        // PREPARATION
        when(itemService.findAllItems()).thenReturn(Arrays.asList(
                new Item(4, "Ball 1", 100, 10),
                new Item(5, "Ball 2", 100, 20),
                new Item(6, "Ball 3", 100, 30)
        ));

        // ACTION AND ASSERTION AT ONCE
         mockMvc.perform(
                 MockMvcRequestBuilders.get("/items")) // GET /items as the request
                 .andExpect(status().isOk())                     // status expectation
                 .andExpect(content().json("[{}, {}, {}]"));   // content expectation as JSON
    }

    @Test
    public void findAllEmpty() throws Exception {
        // PREPARATION
        when(itemService.findAllItems()).thenReturn(Collections.emptyList());

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(
                MockMvcRequestBuilders.get("/items")) // GET /items as the request
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("[]"));   // content expectation as JSON
    }

    @Test
    public void findById_Basic() throws Exception {
        // PREPARATION
        when(itemService.findOneItem(1)).thenReturn(new Item(1, "Ball 1", 100, 10));

        // ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/item/1");  // GET /items

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().isOk())                    // status expectation
                .andExpect(content().json("{}"));   // content expectation as JSON
    }

    @Test
    public void findById_NotFound() throws Exception {
        // PREPARATION
        when(itemService.findOneItem(1)).thenThrow(NotFoundException.class);

        // ACTION
        // call GET /items will return application/json and JSON Array
        RequestBuilder request = MockMvcRequestBuilders
                .get("/item/1");  // GET /items

        // ACTION AND ASSERTION AT ONCE
        mockMvc.perform(request)
                .andExpect(status().isNotFound());                    // status expectation
    }
}