package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category; // clothing, tools [add later, not needed for closet version]
    private String type; // shirt, pants
    private String occasion; // mil, dress, fancy, workout
    private String style; // long sleeve, short
    private String size;  // small, medium, large, 34x33
    private String color; // red, blue, yellow


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id", nullable = false)
    private Containers containers;


    public Item() {
    }

    // multiple arguments within a constructor is called overloading
    public Item(long id, String category, String type) {
        this.id = id;
        this.category = category;
        this.type = type;
    }

    public Item(long id, String category, String type, String occasion, String style, String size, String color, Containers containers) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.occasion = occasion;
        this.style = style;
        this.size = size;
        this.color = color;
        this.containers = containers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Containers getContainers() {
        return containers;
    }

    public void setContainers(Containers containers) {
        this.containers = containers;
    }
}




