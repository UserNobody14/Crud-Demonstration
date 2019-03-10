package com.blah.crud.crudtest;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import com.blah.crud.crudtest.services.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyServiceImpl propertyService;

    private PropertyRepository propertyRepository;

    private RatingRepository ratingRepository;

    //Idea: export RatingRepository, proprepos & jdbcmutable to 'propertyServiceImpl'
    //That way all the utilities & background stuff won't clutter the controller.

    private JdbcMutableAclService aclService;

    public PropertyController(PropertyRepository propertyRepository,
                              RatingRepository ratingRepository,
                              JdbcMutableAclService aclService,
                              PropertyServiceImpl propertyService) {
        this.propertyRepository = propertyRepository;
        this.ratingRepository = ratingRepository;
        this.aclService = aclService;
        this.propertyService = propertyService;
    }

    @Transactional
    @PostMapping
    public void addProperty(@RequestBody Property property) {
        Assert.notNull(property, "Didn't return a property");
        //propertyRepository.save(property);//remove this one.
        propertyService.create(property);

    }

    @GetMapping
    public List<Property> getPropertys() {
        return propertyRepository.findAll();
    }

    //Posts a new rating on the property defined by 'id'
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @PostMapping("/{id}/ratings")
    public void addRatingToProperty(@PathVariable Long id,@RequestBody Rating rating) {
        rating.setPropID(id);
        ratingRepository.save(rating);
    }
    //Gets all ratings associated with a property.
    //@RestController
    @GetMapping("/{id}/ratings")
    public List<Rating> getRatings(@PathVariable Long id) {
        return ratingRepository.findBypropID(id);
    }

    @Transactional
    @PreAuthorize("hasRole('HOST')")
    @PutMapping("/{id}")
    public void editProperty(@PathVariable Long id, @RequestBody Property property) {
        property.setPropID(id);
        propertyService.update(property, id);
/*        Property existingProperty = propertyRepository.findById(id).get();
        Assert.notNull(existingProperty, "Property not found");
        //TODO: find all fields available in property and set them in the existing (database) property.
        existingProperty.setDescription(property.getDescription());
        propertyRepository.save(existingProperty);*/
    }

    @Transactional
    @PreAuthorize("hasRole('HOST')")
    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id) {
        Optional<Property> possibleDelete = propertyRepository.findById(id);
        if (possibleDelete.isPresent()) {
            propertyRepository.delete(possibleDelete.get());
        }
    }
}
