package com.blah.crud.crudtest.persistence.repository;

import com.blah.crud.crudtest.persistence.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findDistinctByPropname(String propName);
}
