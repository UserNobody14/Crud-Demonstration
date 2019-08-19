package com.blah.crud.crudtest;

import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import com.blah.crud.crudtest.services.PropertySearchService;
import com.blah.crud.crudtest.services.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO:

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyServiceImpl propertyService;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertySearchService propertySearchService;
    @Autowired
    private RatingRepository ratingRepository;

    //Idea: export RatingRepository, proprepos & jdbcmutable to 'propertyServiceImpl'
    //That way all the utilities & background stuff won't clutter the controller.
    @Autowired
    private JdbcMutableAclService aclService;

/*    public PropertyController(PropertyRepository propertyRepository,
                              RatingRepository ratingRepository,
                              JdbcMutableAclService aclService,
                              PropertyServiceImpl propertyService,
                              HibernateSearchService searchService) {
        this.propertyRepository = propertyRepository;
        this.ratingRepository = ratingRepository;
        this.aclService = aclService;
        this.propertyService = propertyService;
        this.searchservice = searchService;
    }*/
    //can a nested class have a rest controller?

    @Transactional
    @PostMapping
    public void addProperty(@RequestBody Property property) {
        Assert.notNull(property, "Didn't return a property");
        //propertyRepository.save(property);//remove this one.
        propertyService.create(property);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<Property> getPropertys() {
        return propertyRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public Property getPropertySingular(@PathVariable Long id) {
        return propertyRepository.findById(id).orElse(new Property(null, null, null, null, 0, 0));
    }

    //Posts a new rating on the property defined by 'id'
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @PostMapping("/{id}/ratings")
    public void addRatingToProperty(@PathVariable Long id,@RequestBody Rating rating) {
        rating.setPropID(id);
        propertyService.createRatingAndTest(rating, SecurityContextHolder.getContext().getAuthentication().getName());
        //ratingRepository.save(rating);
    }
    //Gets all ratings associated with a property.
    //@RestController
    @GetMapping("/{id}/ratings")
    public List<Rating> getRatings(@PathVariable Long id) {
        //TODO: make this process the list by whether the user has permission to, updating userCanEdit for each.
        List<Rating> ratingList = ratingRepository.findBypropID(id);
        ratingList.forEach(rating -> rating.setUserCanEdit(false));
        propertyService.makeOwnedItemsEditable(ratingList);
        //setWhichAreEditable(ratingList);
        /*ratingList.forEach(rating -> {
            Acl thisRatingsAcl = aclService.readAclById(new ObjectIdentityImpl(Rating.class, rating.getRatingID()));
            rating.setUserCanEdit(thisRatingsAcl.isGranted(Collections.singletonList(BasePermission.WRITE),
                    Collections.singletonList(new PrincipalSid(auth.getName())), true));
        });*/
        return ratingList;
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
    @PreAuthorize("hasRole('HOST') and hasPermission(#id, 'com.blah.crud.crudtest.persistence.entity.Property', 'WRITE')")
    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id) {

//        Optional<Property> possibleDelete = propertyRepository.findById(id);
//        if (possibleDelete.isPresent()) {
//            propertyRepository.delete(possibleDelete.get());
    }
    @RequestMapping(value = "/search/{searchString}", method = RequestMethod.GET)
    public List<Property> search(@PathVariable(value = "searchString") String q, Model model) {
        List<Property> searchResults = null;
        System.out.println("string " + q);
        try {
            searchResults = propertySearchService.search(q);

        } catch (Exception ex) {
            // here you should handle unexpected errors
            // ...
            // throw ex;
            System.out.println("uh oh." + ex.toString());
        }
        model.addAttribute("search", searchResults);
        return searchResults;

    }
    /*
    @RequestMapping(value = "/search-em", method = RequestMethod.GET)
    public String searchAlt(@RequestPart(value = "search") String q, Model model) {
        List<Property> searchResults = null;
        try {
            searchResults = searchservice.fuzzySearch(q);

        } catch (Exception ex) {
            // here you should handle unexpected errors
            // ...
            // throw ex;
        }
        model.addAttribute("search", searchResults);
        return "index";

    }*/
}
