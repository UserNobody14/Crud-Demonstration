package com.blah.crud.crudtest;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private JdbcMutableAclService aclService;

    public PropertyController(PropertyRepository propertyRepository,
                              RatingRepository ratingRepository) {
        this.propertyRepository = propertyRepository;
        this.ratingRepository = ratingRepository;
    }

    @PostMapping
    public void addProperty(@RequestBody Property property) {
        Assert.notNull(property, "Didn't return a property");
        propertyRepository.save(property);
        //existing code to add new permussions for something.
        Property existingProperty = propertyRepository.findByPropName(property.getPropName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = ((ApplicationUser) auth.getPrincipal()).getUsername();
        //otherstuff
        //log it?
        System.out.println("CRITICALLY: " + currentUser);
        // Prepare the information we'd like in our access control entry (ACE)
        ObjectIdentity oi = new ObjectIdentityImpl(Property.class, existingProperty.getPropID());
        Sid sid = new PrincipalSid(currentUser);
        Permission p = BasePermission.ADMINISTRATION;

// Create or update the relevant ACL
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            acl = aclService.createAcl(oi);
        }

// Now grant some permissions via an access control entry (ACE)
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        aclService.updateAcl(acl);

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
        return ratingRepository.findBypropID(id);
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
