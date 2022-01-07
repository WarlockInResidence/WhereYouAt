package com.roadtowizardry.WhereYouAt.controller;

import com.roadtowizardry.WhereYouAt.dao.ItemRepository;
import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.dao.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

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

    public ItemRepository itemRepository() {
        return itemRepository;
    }

    // Create Team - Post
    @PostMapping
    public ResponseEntity<Containers> create(@RequestBody Containers containers) {
        Containers savedContainers = containerRepository.save(containers);
        URI containerName = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedContainers.getId()).toUri();

        return ResponseEntity.created(containerName).body(savedContainers);
    }

    // Read Team - Get
    @GetMapping("/{id}")
    public Object containerById(@PathVariable Long id) {
        return this.containerRepository.findById(id);
    }

    // reading about this
//    @GetMapping("/containers/{id}")
//    public ResponseEntity<?> getContainerById(@PathVariable Long id) {
//
//        return containerRepository.findById(id)
//                .map(containers -> {
//                    try {
//                        return ResponseEntity
//                                .ok()
//                                .eTag(containers.toString(containers.getVersion))
//                                .location(new URI("/containers/{id}" + containers.getId()))
//                                .body(containers);
//                    } catch (URISyntaxException e) {
//                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//                    }
//                })
//    }

    // Update - Put should only be used to replace in entirety example below
//    @PutMapping("/{id}")
//    public Containers updateWithPutById(@PathVariable Long id,
//                                        @RequestBody Containers updateEntity) {
//
//        if (updateEntity.getId() <= 0) {
//            updateEntity.setId(updateEntity.getId());
//            updateEntity.setType(updateEntity.getType());
//            updateEntity.setRoom(updateEntity.getRoom());
//            updateEntity.setDescription(updateEntity.getDescription());
//            updateEntity.setHeight(updateEntity.getHeight());
//            updateEntity.setWidth(updateEntity.getWidth());
//            updateEntity.setLength(updateEntity.getLength());
//
//        }
//        return containerRepository.save(updateEntity);
//    }


    // Update - Patch
    @PatchMapping("/{id}")
    public Object updateWithPatchById(@PathVariable Long id,
                                      @RequestBody Containers updateEntity) {

        Containers patchContainer = containerRepository.findById(id).orElse(null);
        if (patchContainer == null) {
            return new Error("Container does not exist");
        }
        if (updateEntity.getType() != null) patchContainer.setType((updateEntity.getType()));
        if (updateEntity.getRoom() != null) patchContainer.setRoom((updateEntity.getRoom()));
        if (updateEntity.getDescription() != null) patchContainer.setDescription((updateEntity.getDescription()));

        // here we are extracting the height, width, and length into a size class.
        if (updateEntity.getHeight() != 0)
            patchContainer.setHeight((updateEntity.getHeight()));
        if (updateEntity.getWidth() != 0)
            patchContainer.setWidth((updateEntity.getWidth()));
        if (updateEntity.getLength() != 0)
            patchContainer.setLength((updateEntity.getLength()));

        return containerRepository.save(patchContainer);
    }

    // Delete - Delete  skip checking the delete, just go straight to delete by id then do a try catch based on return value
    @DeleteMapping("/{id}")
    public String deleteContainer(@PathVariable Long id) {
        Optional<Containers> deleteContainer = this.containerRepository.findById(id);
        if (deleteContainer.isPresent()) {
            this.containerRepository.deleteById(id);
            return "Oh NO!!! We only have " + this.containerRepository.count() + " containers remaining.";
        } else
            return "Unable to locate desired container, try another container ID.";
    }

    // List - Get all the items
    @GetMapping
    public Iterable<Containers> getAll() {
        return this.containerRepository.findAll();
    }


}

