package com.blah.crud.crudtest.persistence.entity;

import javax.persistence.*;


//@Table(name = "Property")
@Entity
@Table(name = "property", uniqueConstraints = {@UniqueConstraint(columnNames = {"propname"})})
public class Property {
    public void setPropID(long propID) {
        this.propID = propID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long propID;

    public String getPropname() {
        return propname;
    }

    public void setPropname(String propname) {
        this.propname = propname;
    }

    //IMPORTANT: name this column.
    @Column(name = "propname")
    private String propname;

    private String address;

    private String poolsize;

    private int price;

    private float avgrating;

    private String description;

    protected Property () {}

    public Property (String description, String propname, String address, String poolsize, int price, float avgrating) {
        this.propname = propname;
        this.description = description;
        this.address = address;
        this.poolsize = poolsize;
        this.price = price;
        this.avgrating = avgrating;
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

    public String getPoolsize() {
        return poolsize;
    }

    public void setPoolsize(String poolsize) {
        this.poolsize = poolsize;
    }

    public float getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(float avgrating) {
        this.avgrating = avgrating;
    }
    //All fields available to properties.
}