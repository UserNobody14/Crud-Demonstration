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
@RequestMapping("/property")
public class PropertyController {

    private PropertyRepository propertyRepository;

    private RatingRepository ratingRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @PostMapping
    public void addProperty(@RequestBody Property property) {
        propertyRepository.save(property);
    }

    @GetMapping
    public List<Property> getPropertys() {
        return propertyRepository.findAll();
    }

    //Posts a new rating on the property defined by 'id'
    @PostMapping("/{id}/ratings")
    public void addRatingToProperty(@PathVariable long id,@RequestBody Rating rating) {
        rating.setPropID(id);
        ratingRepository.save(rating);
    }
    //Gets all ratings associated with a property.
    @GetMapping("/{id}/ratings")
    public List<Rating> getRatings(@PathVariable long id) {
        return ratingRepository.findByPropID(id);
    }

    @PutMapping("/{id}")
    public void editProperty(@PathVariable long id, @RequestBody Property property) {
        Property existingProperty = propertyRepository.findById(id).get();
        Assert.notNull(existingProperty, "Property not found");
        //TODO: find all fields available in property and set them in the existing (database) property.
        existingProperty.setDescription(property.getDescription());
        propertyRepository.save(existingProperty);
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable long id) {
        Property propertyToDel = propertyRepository.findById(id).get();
        propertyRepository.delete(propertyToDel);
    }
}
