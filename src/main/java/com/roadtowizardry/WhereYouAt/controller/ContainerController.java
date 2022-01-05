package com.roadtowizardry.WhereYouAt.controller;

import com.roadtowizardry.WhereYouAt.dao.ItemRepository;
import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.dao.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/containers")
public class ContainerController {
    private final ContainerRepository containerRepository;
    private final ItemRepository itemRepository;


    @Autowired
    public ContainerController(ContainerRepository containerRepository, ItemRepository itemRepository) {
        this.containerRepository = containerRepository;
        this.itemRepository = itemRepository;
    }

    // Create Team - Post
    @PostMapping
    public ResponseEntity<Containers> create(@RequestBody Containers containers) {
        Containers savedContainers = containerRepository.save(containers);
        URI containerName = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedContainers.getId()).toUri();

        return ResponseEntity.created(containerName).body(savedContainers);
    }

    // Read Team - Get
    @GetMapping("/{id}")
    public Object containerById(@PathVariable Long id) {
        return this.containerRepository.findById(id);
    }

    // List - Get all the items
    @GetMapping
    public Iterable<Containers> getAll() {
        return this.containerRepository.findAll();
    }

    public ItemRepository itemRepository() {
        return itemRepository;
    }

}

