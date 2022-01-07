package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "containers")
public class Containers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private String room;
    private String description;
    private double height;
    private double width;
    private double length;
//    private Size size;


    @JsonManagedReference
    @OneToMany(mappedBy = "containers", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Item> items;


    public Containers(Long id, String type, String room, String description, double height, double width, double length, Set<Item> items) {
        this.id = id;
        this.type = type;
        this.room = room;
        this.description = description;
        this.height = height;
        this.width = width;
        this.length = length;
        //        this.size = size();
        this.items = items;
    }

//    private Size size() {
//    }

    public Containers() {

    }

    public void addItem(Item item) {
        items.add(item);
        item.setContainers(this);
    }

    public void removeComment(Item item) {
        items.remove(item);
        item.setContainers(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    //    public Size getSize() {
//        return size;
//    }
//
//    public void setSize(double height, double width, double length) {
//        this.size = new Size(height, width, length);
//
//    }
}
