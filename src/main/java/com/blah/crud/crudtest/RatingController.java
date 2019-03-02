package com.blah.crud.crudtest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingRepository.save(rating);
    }

    @GetMapping
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    @PutMapping("/{id}")
    public void editRating(@PathVariable long id, @RequestBody Rating rating) {
        Rating existingRating = ratingRepository.findById(id).get();
        Assert.notNull(existingRating, "Rating not found");
        //TODO: authenticate user sending this request with user who created it?
        existingRating.setComment(rating.getComment());
        ratingRepository.save(existingRating);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable long id) {
        Rating ratingToDel = ratingRepository.findById(id).get();
        ratingRepository.delete(ratingToDel);
    }
}
