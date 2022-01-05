package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "containers")
public class Containers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //    private String name;
    private String type;
//    private String room;
//    private String description;
//    private float height;
//    private float width;
//    private float length;

    @JsonManagedReference
    @OneToMany(mappedBy = "containers", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Item> items;

    public Containers() {
    }

    public Containers(String type, Set<Item> items) {
        this.type = type;
        this.items = items;
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
