package com.blah.crud.crudtest;


import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prop-ratings/{propID}")
public class PropertyRatingController {

    private RatingRepository ratingRepository;

    public PropertyRatingController (RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingRepository.save(rating);
    }

    @GetMapping
    @ResponseBody
    public List<Rating> getRatings(@PathVariable(name = "propID") Long propID) {
        return ratingRepository.findBypropID(propID);
    }

    @CrossOrigin(origins = { "http://localhost:9000" }, allowCredentials = "true")
    @PutMapping("/{id}")
    public void editRating(@PathVariable long id, @RequestBody Rating rating) {
        Rating existingRating = ratingRepository.findById(id).get();
        Assert.notNull(existingRating, "Rating not found");
        //TODO: authenticate user sending this request with user who created it?
        existingRating.setComment(rating.getComment());
        existingRating.setRating(rating.getRating());
        ratingRepository.save(existingRating);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable long id) {
        Rating ratingToDel = ratingRepository.findById(id).get();
        ratingRepository.delete(ratingToDel);
    }
}
