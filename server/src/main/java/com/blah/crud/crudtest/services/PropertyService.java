package com.blah.crud.crudtest.services;

import com.blah.crud.crudtest.persistence.entity.Property;

public interface PropertyService {
    Property get(long id);
    void create(Property property);
    void delete(Property property, long id);
    Property update(Property property, long id);
    //add?
}
