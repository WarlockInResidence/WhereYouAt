package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private float height;
    private float width;
    private float length;

    @JsonIgnore
    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Containers> containers;

    public Rooms(String name, String description, float height, float width, float length, Set<Containers> containers) {
        this.name = name;
        this.description = description;
        this.height = height;
        this.width = width;
        this.length = length;
        this.containers = containers;
    }

    public Rooms() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Set<Containers> getContainers() {
        return containers;
    }

    public void setContainers(Set<Containers> containers) {
        this.containers = containers;
    }
}