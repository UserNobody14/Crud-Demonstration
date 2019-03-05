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
        /*
        //existing code to add new permussions for something.

        ///////////////////////////////////////////////////////////////////////
        //This is the acl boilerplate code:: it creates a new permission for the owner
        //if (propertyRepository. == null) {
            Property existingProperty = propertyRepository.findByPropName(property.getPropName());
            ObjectIdentity oi = new ObjectIdentityImpl(Property.class, existingProperty.getPropID());
//        }
//        else {
//            ObjectIdentity oi = new ObjectIdentityImpl(Property.class, property.getPropID());
//        }
        //TODO: write a class or service that does these automatically.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = ((String) auth.getName());
        //otherstuff
        //log it?
        System.out.println("CURR_USER: " + currentUser);
        // Prepare the information we'd like in our access control entry (ACE)

        Sid sid = new PrincipalSid(currentUser);
        Permission p = BasePermission.WRITE;
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
        */

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
        property.setPropID(id);

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
