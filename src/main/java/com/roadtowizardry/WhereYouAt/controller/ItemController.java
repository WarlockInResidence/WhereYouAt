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
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    // Read Team - Get
    @GetMapping("/{id}")
    public Object itemById(@PathVariable Long id) {
        return this.itemRepository.findById(id);
    }

    // Update - Patch
    @PatchMapping("/{id}")
    public Object updateWithPatchById(@PathVariable Long id,
                                      @RequestBody Item updateEntity) {
//        if (!itemRepository.existsById(id)) return itemRepository.save(updateEntity);

        Item patchItem = itemRepository.findById(id).orElse(null);
        if (patchItem == null) {
            return new Error("Item doesn't exists");
        }
        if (updateEntity.getCategory() != null) patchItem.setCategory((updateEntity.getCategory()));
        if (updateEntity.getType() != null) patchItem.setType((updateEntity.getType()));
        if (updateEntity.getOccasion() != null) patchItem.setOccasion((updateEntity.getOccasion()));
        if (updateEntity.getStyle() != null) patchItem.setStyle((updateEntity.getStyle()));
        if (updateEntity.getSize() != null) patchItem.setSize((updateEntity.getSize()));
        if (updateEntity.getColor() != null) patchItem.setColor((updateEntity.getColor()));

        return itemRepository.save(patchItem);
    }

    // Delete - Delete
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        Item deleteItem = this.itemRepository.findById(id).get();
        if (deleteItem != null) {
            this.itemRepository.deleteById(id);
            return "Successful deletion of item";
        } else
            return "Unable to locate desired item.";
    }

    // List - Get all the items
    @GetMapping
    public Iterable<Item> getAll() {
        return this.itemRepository.findAll();
    }
}
