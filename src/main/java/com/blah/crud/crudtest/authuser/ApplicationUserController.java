package com.blah.crud.crudtest.authuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


/*public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }*/


    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    @GetMapping
    public List<ApplicationUser> getUsers() {
        return applicationUserRepository.findAll();
    }


/*@PutMapping("/{id}")
    public void editUser(@PathVariable long id, @RequestBody ApplicationUser user) {
        ApplicationUser existingUser = applicationUserRepository.findById(id).get();
        Assert.notNull(existingUser, "ApplicationUser not found");
        //TODO: find all fields available in user and set them in the existing (database) user.
        existingUser.setDescription(user.getDescription());
        applicationUserRepository.save(existingUser);
    }*/


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        ApplicationUser applicationUserToDel = applicationUserRepository.findById(id).get();
        applicationUserRepository.delete(applicationUserToDel);
    }
}

