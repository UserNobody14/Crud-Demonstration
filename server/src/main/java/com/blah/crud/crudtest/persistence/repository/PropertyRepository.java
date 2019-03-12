package com.blah.crud.crudtest.persistence.repository;

import com.blah.crud.crudtest.persistence.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findDistinctByPropname(String propName);
}
