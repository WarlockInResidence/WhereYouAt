package com.roadtowizardry.WhereYouAt.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String category; // clothing, tools [add later, not needed for closet version]
    private String type; // shirt, pants
    private String occasion; // mil, dress, fancy, workout
    private String length; // long sleeve, short
    private String color; // red, blue, yellow


    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "containers_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("containers_id")
    private Containers containers;


    public Item(String category, String type, String occasion, String length, String color, Containers containers) {
        this.category = category;
        this.type = type;
        this.occasion = occasion;
        this.length = length;
        this.color = color;
        this.containers = containers;
    }

    public Item() {

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

    public Containers getContainer() {
        return containers;
    }

    public void setContainer(Containers containers) {
        this.containers = containers;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", occasion='" + occasion + '\'' +
                ", length='" + length + '\'' +
                ", color='" + color + '\'' +
                ", containers=" + containers +
                '}';
    }
}




