package com.blah.crud.crudtest.authuser;

import com.auth0.jwt.JWT;
import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.repository.ApplicationUserRepository;
import com.blah.crud.crudtest.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.blah.crud.crudtest.security.SecurityConstants.EXPIRATION_TIME;
import static com.blah.crud.crudtest.security.SecurityConstants.SECRET;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {


    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private DaoAuthenticationProvider authenticationProvider;


    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     DaoAuthenticationProvider authenticationProvider) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationProvider = authenticationProvider;
    }


    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    //todo: secure
    @GetMapping
    public List<ApplicationUser> getUsers() {
        return applicationUserRepository.findAll();
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody ApplicationUser loginRequest) {

        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JWT.create()
                .withSubject(((MyUserPrinciple) authentication.getPrincipal()).getUsername())
                //.withClaim("authorityr", ((ApplicationUser) auth.getPrincipal()).getAuthorityr())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

/*@PutMapping("/{id}")
    public void editUser(@PathVariable long id, @RequestBody ApplicationUser user) {
        ApplicationUser existingUser = applicationUserRepository.findById(id).get();
        Assert.notNull(existingUser, "ApplicationUser not found");
        //TODO: find all fields available in user and set them in the existing (database) user.
        existingUser.setDescription(user.getDescription());
        applicationUserRepository.save(existingUser);
    }*/

    //Return Credentials?

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        ApplicationUser applicationUserToDel = applicationUserRepository.findById(id).get();
        applicationUserRepository.delete(applicationUserToDel);
    }
}

