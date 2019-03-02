package com.blah.crud.crudtest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    //TODO: make a function that gets all ratings for a particular property?
    List<Rating> findByPropID(long propID);
}
