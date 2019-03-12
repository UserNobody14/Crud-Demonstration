package com.blah.crud.crudtest.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ratingID;
    @Column(name = "propid")
    private long propID;
    @Column(name = "rating")
    private int rating;
    @Column(name = "name")
    private String comment;

    @Transient
    private boolean userCanEdit;

    protected Rating () {}

    public Rating(String comment, int rating, long propID) {
        this.rating = rating;
        this.comment = comment;
        this.propID = propID;
    }
}
