package com.blah.crud.crudtest.services;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private JdbcMutableAclService jdbcMutableAclService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    //public PropertyServiceImpl() {}

    public PropertyServiceImpl(JdbcMutableAclService jdbcMutableAclService,
                               RatingRepository ratingRepository,
                               PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
        this.jdbcMutableAclService = jdbcMutableAclService;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Property get(long id) {
        return null;
    }

    public void createAndTest(Property property, ApplicationUser applicationUser) {
        String existingPropname = property.getPropname();
        Property existingProperty = propertyRepository.findDistinctByPropname(existingPropname);
        if(existingProperty == null) {
            propertyRepository.save(property);
        }
    }
    @Transactional
    public void createRatingAndTest(Rating r, String applicationUser) {
        ratingRepository.save(r);
        addPermission(r.getRatingID(), new PrincipalSid(applicationUser), BasePermission.WRITE, Rating.class);
    }
    @Transactional
    public void createAndTest(Property property, String applicationUser) {
        String existingPropname = property.getPropname();
        Property existingProperty = propertyRepository.findDistinctByPropname(existingPropname);
        if(existingProperty == null) {
            propertyRepository.save(property);
            addPermission(property.getPropID(), new PrincipalSid(applicationUser), BasePermission.WRITE, Property.class);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HOST')")
    @Override
    public void create(@Param("property") Property property) {
        ///////////////////////////////////////////////////////////////////////
        //This is the acl boilerplate code:: it creates a new permission for the owner
        propertyRepository.save(property);
        //if (propertyRepository. == null) {
        Property existingProperty = propertyRepository.findDistinctByPropname(property.getPropname());
        ObjectIdentity oi = new ObjectIdentityImpl(Property.class, existingProperty.getPropID());
//        }
//        else {
//            ObjectIdentity oi = new ObjectIdentityImpl(Property.class, property.getPropID());
//        }
        //TODO: write a class or service that does these automatically.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = ((String) auth.getName());
        //ArrayList<Authority> aval = auth.getAuthorities();
        //otherstuff
        //log it?
        System.out.println("CURR_USER: " + currentUser);
        // Prepare the information we'd like in our access control entry (ACE)

        Sid sid = new PrincipalSid(currentUser);
        Permission p = BasePermission.WRITE;
        // Create or update the relevant ACL
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) jdbcMutableAclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            acl = jdbcMutableAclService.createAcl(oi);
        }
        // Now grant some permissions via an access control entry (ACE)
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        jdbcMutableAclService.updateAcl(acl);
    }

    @PreAuthorize("hasAuthority('ROLE_HOST') and hasPermission(#property, 'WRITE')")
    @Override
    public void delete(@Param("property") Property property, long id) {
        propertyRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_HOST') and hasPermission(#p, 'WRITE')")
    @Override
    public Property update(@Param("p") Property p, long id) {
        Property existingProperty = propertyRepository.findById(id).get();
        existingProperty.setPropID(p.getPropID());
        existingProperty.setAddress(p.getAddress());
        existingProperty.setDescription(p.getDescription());
        existingProperty.setPoolsize(p.getPoolsize());
        existingProperty.setPropname(p.getPropname());
        return propertyRepository.save(existingProperty);
    }

    public void addPermission(long objectId, Sid recipient, Permission permission, Class clazz) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), objectId);

        try {
            acl = (MutableAcl) jdbcMutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = jdbcMutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        jdbcMutableAclService.updateAcl(acl);
    }
}
