package com.blah.crud.crudtest.services;

import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private JdbcMutableAclService jdbcMutableAclService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Property get(long id) {
        return null;
    }

    @Override
    public void create(Property property) {
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
            acl = (MutableAcl) jdbcMutableAclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            acl = jdbcMutableAclService.createAcl(oi);
        }
        // Now grant some permissions via an access control entry (ACE)
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        jdbcMutableAclService.updateAcl(acl);
    }

    @Override
    public void delete(Property property) {

    }

    @Override
    public Property update(Property property) {
        return null;
    }
}
