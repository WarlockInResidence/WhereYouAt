package com.roadtowizardry.WhereYouAt.controller;

import com.roadtowizardry.WhereYouAt.dao.ContainerRepository;
import com.roadtowizardry.WhereYouAt.dao.ItemRepository;
import com.roadtowizardry.WhereYouAt.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("MainItemController")
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
    public Item create(@RequestBody Item item) {
        return itemRepository.save(item);
//        Optional<Containers> optionalContainer = containerRepository.findById(item.getContainers().getId());
//        if (!optionalContainer.isPresent()) {
//            return ResponseEntity.unprocessableEntity().build();
//        }

//        item.setContainers(optionalContainer.get());

//        Item savedItem = itemRepository.save(item);
//        URI itemName = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(savedItem.getId()).toUri();
//
//        return ResponseEntity.created(itemName).body(savedItem);
    }

    // Read Team - Get
    @GetMapping("/{id}")
    public Object itemById(@PathVariable Long id) {
        return this.itemRepository.findById(id);
    }

    // List - Get all the items
    @GetMapping
    public Iterable<Item> getAll() {
        return this.itemRepository.findAll();
    }

    @GetMapping("containers/{id}")
    public ResponseEntity<Page<Item>> getByContainerId(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(itemRepository.findByContainersId(id, pageable));
    }

}
