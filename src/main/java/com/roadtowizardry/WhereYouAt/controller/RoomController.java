package com.roadtowizardry.WhereYouAt.controller;

import com.roadtowizardry.WhereYouAt.dao.ContainerRepository;
import com.roadtowizardry.WhereYouAt.dao.RoomsRepository;
import com.roadtowizardry.WhereYouAt.dao.ItemRepository;
import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.model.Rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomsRepository roomsRepository;
    private final ContainerRepository containerRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public RoomController(RoomsRepository roomsRepository, ContainerRepository containerRepository, ItemRepository itemRepository) {
        this.roomsRepository = roomsRepository;
        this.containerRepository = containerRepository;
        this.itemRepository = itemRepository;
    }

    // Create Team - Post
    @PostMapping
    public ResponseEntity<Rooms> create(@RequestBody Rooms rooms) {
        Rooms savedRooms = roomsRepository.save(rooms);
        URI roomsName = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRooms.getId()).toUri();

        return ResponseEntity.created(roomsName).body(savedRooms);
    }

    // Read Team - Get
    @GetMapping("/{id}")
    public Object roomById(@PathVariable Long id) {
        return this.roomsRepository.findById(id);
    }

    // List - Get all the items
    @GetMapping
    public Iterable<Rooms> getAll() {
        return this.roomsRepository.findAll();
    }

    public ContainerRepository getContainerRepository() {
        return containerRepository;
    }

    public ItemRepository getItemRepository() {
        return itemRepository;
    }
}



