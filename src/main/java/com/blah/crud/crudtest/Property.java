package com.blah.crud.crudtest;

import javax.persistence.*;

@Entity
@Table(name = "Property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long propID;

    private long userID;

    private String address;

    private String poolsize;

    private int price;

    private float avgrating;

    private String description;

    protected Property () {}

    public Property (String description) {
        this.description = description;
    }

    public long getPropID() {
        return propID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    //All fields available to properties.
}