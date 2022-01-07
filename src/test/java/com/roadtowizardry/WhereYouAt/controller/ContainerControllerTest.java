package com.roadtowizardry.WhereYouAt.controller;

import com.roadtowizardry.WhereYouAt.dao.ContainerRepository;
import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.model.Size;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContainerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ContainerRepository containerRepository;

    Containers testContainer1 = new Containers(1L, "holding space", "unallocated", "this is a holding space for new items", 100, 100, 100, null);
    Containers testContainer2 = new Containers(2L, "box", "master closet", "on the top shelf", 35.5, 17.5, 72, null);
    Containers testContainer3 = new Containers(3L, "bag of holding", "bedroom", "it is far bigger on the inside than on the outside", 10, 5, 10000, null);


    @Test
    @Transactional
    @Rollback
    void testCreateContainer() throws Exception {
        // Request
        MockHttpServletRequestBuilder mockContainer = post("/containers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"box\", " +
                        "\"room\":\"closet\", " +
                        "\"description\":\"Big black plastic container\", " +
                        "\"height\":\"38.0\", " +
                        "\"width\":\"14.0\", " +
                        "\"length\":\"12.0\"}");
        // Do the thing
        this.mvc.perform(mockContainer)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.type", is("box")))
                .andExpect(jsonPath("$.room", is("closet")))
                .andExpect(jsonPath("$.description", is("Big black plastic container")))
                .andExpect(jsonPath("$.height", is(38.0)))
                .andExpect(jsonPath("$.width", is(14.0)))
                .andExpect(jsonPath("$.length", is(12.0)))
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void testGetContainerById() throws Exception {
        // Make it
        Containers mockContainer = new Containers();
        mockContainer.setId(1);
        mockContainer.setType("box");
        mockContainer.setRoom("bathroom");
        mockContainer.setDescription("this is a container");
        mockContainer.setHeight(20.0);
        mockContainer.setLength(15.0);
        mockContainer.setWidth(10.0);

        //save it
        containerRepository.save(mockContainer);

        mvc.perform(get("/containers/{id}", 1))
                // Validates response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("box")))
                .andExpect(jsonPath("$.room", is("bathroom")))
                .andExpect(jsonPath("$.description", is("this is a container")))
                .andExpect(jsonPath("$.height", is(20.0)))
                .andExpect(jsonPath("$.length", is(15.0)))
                .andExpect(jsonPath("$.width", is(10.0)));
    }

    @Test
    @Transactional
    @Rollback
    void testPatchContainerById() throws Exception {
        // Make it once for an initial value
        Containers initialContainer = new Containers();
        initialContainer.setType("box");
        initialContainer.setRoom("bathroom");
        initialContainer.setDescription("this is a container");
        initialContainer.setHeight(20.0);
        initialContainer.setLength(15.0);
        initialContainer.setWidth(10.0);

        // save record to track id then update new record
        Containers updateContainer = containerRepository.save(initialContainer);

        // make a copy from initial container
        Long updatedId = updateContainer.getId();

        // make changes to initial container
        MockHttpServletRequestBuilder patchInitialContainer = patch("/containers/" + updatedId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"bucket\", " +
                        "\"room\":\"closet\", " +
                        "\"description\":\"nothing to see here\", " +
                        "\"height\":\"5.0\", " +
                        "\"width\":\"4.0\", " +
                        "\"length\":\"3.0\"}");

        // validate changes are made
        this.mvc.perform(patchInitialContainer)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedId.intValue())))
                .andExpect(jsonPath("$.type", is("bucket")))
                .andExpect(jsonPath("$.room", is("closet")))
                .andExpect(jsonPath("$.description", is("nothing to see here")))
                .andExpect(jsonPath("$.height", is(5.0)))
                .andExpect(jsonPath("$.width", is(4.0)))
                .andExpect(jsonPath("$.length", is(3.0)));
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteContainerById() throws Exception {
        // Make it
        Containers mockContainer = new Containers();
        mockContainer.setType("box");
        mockContainer.setRoom("bathroom");
        mockContainer.setDescription("this is a container");
        mockContainer.setHeight(20.0);
        mockContainer.setLength(15.0);
        mockContainer.setWidth(10.0);

        // add it to repository
        containerRepository.save(mockContainer);

        Long updatedId = mockContainer.getId();

        var deleteId = containerRepository.findById(updatedId);

        MockHttpServletRequestBuilder deleteMockContainer = delete("/containers/{id}", updatedId.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(deleteMockContainer)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", updatedId.toString()).doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    void testGetAllContainers() throws Exception {
        MockHttpServletRequestBuilder getAllContainers = get("/containers")
                .contentType(MediaType.APPLICATION_JSON);


        mvc.perform(getAllContainers)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void testGetAllContainersAlternateWay() throws Exception {
        List<Containers> containersList = new ArrayList<>(Arrays.asList(testContainer1, testContainer2, testContainer3));

        Mockito.when(containerRepository.findAll()).thenReturn(containersList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/containers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

}