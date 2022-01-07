package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String category; // clothing, tools [add later, not needed for closet version]
    private String type; // shirt, pants
    private String occasion; // mil, dress, fancy, workout
    private String length; // long sleeve, short
    private String color; // red, blue, yellow


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id", nullable = false)
    private Containers containers;

    public Items() {
    }

    public Items(String category, String type, String occasion, String length, String color, Containers containers) {
        this.category = category;
        this.type = type;
        this.occasion = occasion;
        this.length = length;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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




