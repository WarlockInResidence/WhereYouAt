package com.roadtowizardry.WhereYouAt.controller;

import com.roadtowizardry.WhereYouAt.dao.ContainerRepository;
import com.roadtowizardry.WhereYouAt.dao.ItemRepository;
import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.model.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

//@RestController("MainItemController")
@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemRepository itemRepository;
    private final ContainerRepository containerRepository;


    @Autowired
    public ItemController(ItemRepository itemRepository, ContainerRepository containerRepository) {
        this.itemRepository = itemRepository;
        this.containerRepository = containerRepository;
    }

    // Create Team - Post
    @PostMapping
    public ResponseEntity<Items> create(@RequestBody Items items) {
        Optional<Containers> optionalContainer = containerRepository.findById(items.getContainers().getId());
        if (!optionalContainer.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        items.setContainers(optionalContainer.get());

        Items savedItems = itemRepository.save(items);
        URI itemName = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedItems.getId()).toUri();

        return ResponseEntity.created(itemName).body(savedItems);
    }

    // Read Team - Get
    @GetMapping("/{id}")
    public Object itemById(@PathVariable Long id) {
        return this.itemRepository.findById(id);
    }

    // List - Get all the items
    @GetMapping
    public Iterable<Items> getAll() {
        return this.itemRepository.findAll();
    }

    @GetMapping("containers/{id}")
    public ResponseEntity<Page<Items>> getByContainerId(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(itemRepository.findByContainersId(id, pageable));
    }

}
