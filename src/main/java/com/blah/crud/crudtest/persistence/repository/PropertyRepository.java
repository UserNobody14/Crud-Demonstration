package com.blah.crud.crudtest.persistence.repository;

import com.blah.crud.crudtest.persistence.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findByPropName(String propName);

    @PreAuthorize("jasPermission(#property, 'WRITE')")
    void delete(@Param("property") Property property);

    //find out how to make it work by
//    @PreAuthorize("jasPermission(#id, 'WRITE')")
//    void delete(@Param("id") long id);

    //@PreAuthorize("hasPermission(#property, 'READ'")
    Property save(@Param("property") Property property);
}
