package com.blah.crud.crudtest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /*@PutMapping("/{id}")
    public void editUser(@PathVariable long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id).get();
        Assert.notNull(existingUser, "User not found");
        //TODO: find all fields available in user and set them in the existing (database) user.
        existingUser.setDescription(user.getDescription());
        userRepository.save(existingUser);
    }*/

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        User userToDel = userRepository.findById(id).get();
        userRepository.delete(userToDel);
    }
}
