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

import java.util.List;

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

    @Transactional
    public void createRatingAndTest(Rating r, String applicationUser) {
        ratingRepository.save(r);
//        (r.getRatingID() == null) ? System.out.println("sucks"):
        System.out.println("Rating the Thing");
        //addPermission(r.getRatingID(), new PrincipalSid(applicationUser), BasePermission.WRITE, Rating.class);
        addOwnerShip(r.getRatingID(), new PrincipalSid(applicationUser), Rating.class);
    }
    @Transactional
    public void createAndTest(Property property, String applicationUser) {
        String existingPropname = property.getPropname();
        Property existingProperty = propertyRepository.findDistinctByPropname(existingPropname);
        if(existingProperty == null) {
            propertyRepository.save(property);
            //addPermission(property.getPropID(), new PrincipalSid(applicationUser), BasePermission.WRITE, Property.class);
            addOwnerShip(property.getPropID(), new PrincipalSid(applicationUser), Property.class);

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
        addPermission(existingProperty.getPropID(), new PrincipalSid(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ), BasePermission.WRITE, Property.class);
    }

    public float averageRating(Long propId) {
        List<Rating> ratingList = ratingRepository.findBypropID(propId);
        int number = ratingList.size();
        int sum = ratingList.stream().mapToInt(Rating::getRating).sum();
        return (float) sum / number;
    }

    private void addAvgRating(Long propId, Property p) {
        float newRating = averageRating(propId);
        p.setAvgrating(newRating);
        propertyRepository.save(p);
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
    public void addOwnerShip(long objectId, Sid recipient, Class clazz) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), objectId);

        try {
            acl = (MutableAcl) jdbcMutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = jdbcMutableAclService.createAcl(oid);
        }
        acl.setOwner(recipient);
        jdbcMutableAclService.updateAcl(acl);
    }
    public MutableAcl getMutableAcl(ObjectIdentity oid) {
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) jdbcMutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = null;
        }
        return acl;
    }
    public void makeOwnedItemsEditable(List<Rating> r) {
        //System.out.println("START MAKEOWNEDITEMS");
        //
        r.forEach(rating -> {
            if (getOwner(rating).equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                //System.out.println("SecurityName: " + SecurityContextHolder.getContext().getAuthentication().getName());
                //System.out.println("Owner: " + getOwner(rating));
                rating.setUserCanEdit(true);
            }
            else {
                rating.setUserCanEdit(false);
            }
        });
        //System.out.println("END");
        //return r;
    }

    public String getOwner(Rating r) {
        ObjectIdentity oi = new ObjectIdentityImpl(Rating.class.getCanonicalName(), r.getRatingID());
        MutableAcl acl = getMutableAcl(oi);
        PrincipalSid mysid = (PrincipalSid) jdbcMutableAclService.readAclById(
                new ObjectIdentityImpl(Rating.class.getCanonicalName(),
                        r.getRatingID()))
                .getOwner();
        return mysid.getPrincipal();
    }
    public String getOwner(Property r) {
        ObjectIdentity oi = new ObjectIdentityImpl(Rating.class.getCanonicalName(), r.getPropID());
        MutableAcl acl = getMutableAcl(oi);
        PrincipalSid mysid = (PrincipalSid) jdbcMutableAclService.readAclById(
                new ObjectIdentityImpl(Property.class.getCanonicalName(),
                        r.getPropID()))
                .getOwner();
        return mysid.getPrincipal();
    }
}
