package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category; // clothing, tools [add later, not needed for closet version]
    private String type; // shirt, pants
//    private String occasion; // mil, dress, fancy, workout
//    private String length; // long sleeve, short
//    private String color; // red, blue, yellow


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id", nullable = false)
    private Containers containers;

    public Item() {
    }

    public Item(String type, String category, Containers containers) {
        this.type = type;
        this.category = category;
        this.containers = containers;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Containers getContainers() {
        return containers;
    }

    public void setContainers(Containers containers) {
        this.containers = containers;
    }
}




