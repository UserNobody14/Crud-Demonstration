package com.blah.crud.crudtest.persistence.entity;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;


//@Table(name = "Property")
@Entity
@Indexed
@Data
@Table(name = "property", uniqueConstraints = {@UniqueConstraint(columnNames = {"propname"})})
public class Property {
    public void setPropID(long propID) {
        this.propID = propID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long propID;
    //IMPORTANT: name this column.
    @Field
    @Column(name = "propname")
    private String propname;
    @Field
    private String address;

    private String poolsize;

    private int price;

    private float avgrating;
    @Field
    private String description;

    @Transient
    private boolean userCanEdit;

    protected Property () {}

    public Property (String description, String propname, String address, String poolsize, int price, float avgrating) {
        this.propname = propname;
        this.description = description;
        this.address = address;
        this.poolsize = poolsize;
        this.price = price;
        this.avgrating = avgrating;
    }
    public Property (String propname) {
        this.propname = propname;
        this.avgrating = 0;
        this.price = 0;
        this.poolsize = null;
        this.address = null;
        this.description = null;
    }

    /*public long getPropID() {
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
    */
}