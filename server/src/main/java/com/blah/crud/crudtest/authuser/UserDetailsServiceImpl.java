package com.blah.crud.crudtest.authuser;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

//import javax.transaction.Transactional;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    //@Autowired
    //private WebApplicationContext applicationContext;
    //@Autowired
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }
    //TODO: add in thymeleaf and webpack and such from
    //IDEA: extend this to create host vs. guest permissions? org.springframework.security.acls.domain.AbstractPermission

//    public UserDetailsServiceImpl() {
//        super();
//    }

//    @PostConstruct
//    public void completeSetup() {
//        applicationUserRepository = applicationContext.getBean(ApplicationUserRepository.class);
//    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrinciple(applicationUser);
        //return new ApplicationUser(applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.getAuthorityr());
    }

}