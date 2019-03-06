package com.blah.crud.crudtest.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ratingID;

    @Column(name = "propid")
    private long propID;

    private int rating;

    private String comment;

    protected Rating () {}

    public Rating(String comment, int rating, long propID) {
        this.rating = rating;
        this.comment = comment;
        this.propID = propID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPropID() {
        return propID;
    }

    public void setPropID(long propID) {
        this.propID = propID;
    }

}
