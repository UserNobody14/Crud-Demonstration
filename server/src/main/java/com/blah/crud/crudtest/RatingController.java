package com.blah.crud.crudtest;

import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = { "http://localhost:9000" }, allowCredentials = "true")
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
