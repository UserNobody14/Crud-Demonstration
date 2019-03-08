package com.blah.crud.crudtest.services;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationUserService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public ApplicationUser getUser(String user) {
        return new ApplicationUser("x", "x", "a");
    }
}
