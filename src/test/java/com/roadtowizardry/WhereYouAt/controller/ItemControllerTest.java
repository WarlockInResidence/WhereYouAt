package com.roadtowizardry.WhereYouAt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadtowizardry.WhereYouAt.dao.ItemRepository;
import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.model.Item;
import com.roadtowizardry.WhereYouAt.model.Size;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    MockMvc mvc;

    // used to make a json into a string in a much cleaner way
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ItemRepository itemRepository;
//    ContainerRepository containerRepository; // not sure if I need this

    // Must Mock because of ManyToOne
    Containers testContainer = new Containers(1L, "bin", "closet", "clear plastic", 38.5, 14.5, 12.5, null);

    // Mock some entity items for later use.
    Item testItem1 = new Item(1L, "clothing", "bowtie");
    Item testItem2 = new Item(2L, "clothing", "robe");
    Item testItem3 = new Item(3L, "clothing", "shirt");
    Item testItem4 = new Item(4L, "clothing", "bowtie", "work", "butterfly", "n/a", "brown", testContainer);

    public Item getTestItem4() {
        return testItem4;
    }

    @Test
    void testCreateItem() throws Exception {
        // Builds a mock item
        // this needs the @builder lombok
        Item buildItem = Item.builder().category("tools").type("wrench").build();

        // saves to mock repo
        Mockito.when(itemRepository.save(buildItem)).thenReturn(buildItem);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(buildItem));

        mvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(0)))
                .andExpect(jsonPath("$.type", is("tool")));
    }

    @Test
    void testGetItemById() throws Exception {
        Mockito.when(itemRepository.findById(testItem1.getId())).thenReturn(java.util.Optional.of(testItem1));

        mvc.perform(MockMvcRequestBuilders
                        .get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.type", Matchers.is("bowtie")));
    }

    @Test
    void testUpdateItemWithPatchById() throws Exception {
        // Call one made up top for initial values
        Mockito.when(itemRepository.findById(testItem4.getId())).thenReturn(java.util.Optional.of(testItem4));

        // make changes to the item
        Item updatedTestItem4 = new Item(4L, "clothing", "bowtie", "work", "butterfly", "n/a", "silver", testContainer);

        // mock save, showing patch returns the thing
        Mockito.when(itemRepository.save(testItem4)).thenReturn(updatedTestItem4);

        // make changes to initial container
        MockHttpServletRequestBuilder patchTestItem4 = patch("/items/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedTestItem4));

        // validate changes are made
        mvc.perform(patchTestItem4)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.category", is("clothing")))
                .andExpect(jsonPath("$.type", is("bowtie")))
                .andExpect(jsonPath("$.occasion", is("work")))
                .andExpect(jsonPath("$.style", is("butterfly")))
                .andExpect(jsonPath("$.size", is("n/a")))
                .andExpect(jsonPath("$.color", is("silver"))); // this one is what we changed
    }
    // I think the above one is a little bit better, below is a long way of doing it

//    @Test
//    void testPatchItemById() throws Exception {
//        // Make it once for an initial value
//        Item initialTestItem = new Item();
//        initialTestItem.setCategory("clothing");
//        initialTestItem.setType("bowtie");
//        initialTestItem.setOccasion("work");
//        initialTestItem.setStyle("butterfly");
//        initialTestItem.setSize("n/a");
//        initialTestItem.setColor("brown");
//        initialTestItem.setContainers(testContainer);
//
//
//        // save record to track id then update new record
//        Item updatedTestItem = itemRepository.save(initialTestItem);
//
//        // make a copy from initial item
//        Long patchedId = updatedTestItem.getId();
//
//        // make changes to initial item
//        MockHttpServletRequestBuilder patchTestItem = patch("/containers/" + patchedId.toString())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("" +
////                        "{\"category\":\"clothing\"," +
////                        "\"type\":\"basket\", " +
////                        "\"occasion\":\"work\", " +
////                        "\"style\":\"butterfly\", " +
////                        "\"size\":\"n/a\", " +
//                        "\"color\":\"silver\"}");
//
//        // validate changes are made
//        mvc.perform(patchTestItem)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(patchedId.intValue())))
//                .andExpect(jsonPath("$.category", is("clothing")))
//                .andExpect(jsonPath("$.type", is("bowtie")))
//                .andExpect(jsonPath("$.occasion", is("work")))
//                .andExpect(jsonPath("$.style", is("butterfly")))
//                .andExpect(jsonPath("$.size", is("n/a")))
//                .andExpect(jsonPath("$.color", is("silver"))); // this one is what we changed
//    }


    @Test
    void testDeleteItem() throws Exception {
        // Call one made up top for initial values
        Mockito.when(itemRepository.findById(testItem1.getId())).thenReturn(java.util.Optional.of(testItem1));

        // make changes to initial container
        MockHttpServletRequestBuilder deleteTestItem1 = delete("/items/1")
                .contentType(MediaType.APPLICATION_JSON);

        // validate changes are made
        mvc.perform(deleteTestItem1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
//                .andExpect(jsonPath("$.category", is("clothing")))
//                .andExpect(jsonPath("$.type", is("bowtie")))
//                .andExpect(jsonPath("$.occasion", is("work")))
//                .andExpect(jsonPath("$.style", is("butterfly")))
//                .andExpect(jsonPath("$.size", is("n/a")))
//                .andExpect(jsonPath("$.color", is("silver"))); // this one is what we changed
//

    }

    @Test
    void testGetAllItems() throws Exception {
        List<Item> itemList = new ArrayList<>(Arrays.asList(testItem1, testItem2, testItem3, testItem4));

        Mockito.when(itemRepository.findAll()).thenReturn(itemList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }
}