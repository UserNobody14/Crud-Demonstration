package com.blah.crud.crudtest.persistence.repository;

import com.blah.crud.crudtest.persistence.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    //TODO: make a function that gets all ratings for a particular property?
    List<Rating> findBypropID(long propID);
}
