package com.blah.crud.crudtest;

import com.blah.crud.crudtest.authuser.MyUserPrinciple;
import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.entity.Rating;
import com.blah.crud.crudtest.persistence.repository.ApplicationUserRepository;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import com.blah.crud.crudtest.services.PropertyServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class DataInitializeAndTest implements CommandLineRunner {

    private final ApplicationUserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final PropertyRepository propertyRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PropertyServiceImpl propertyService;

    public DataInitializeAndTest(ApplicationUserRepository userRepository,
                                 RatingRepository ratingRepository,
                                 PropertyRepository propertyRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 PropertyServiceImpl propertyService) {
        this.propertyRepository = propertyRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.propertyService = propertyService;
    }

    @Override
    public void run(String... strings) throws Exception {
        //todo: try making a test of propertyServiceImpl, setting some of these guys as owners?

        //propertyRepository.findAll().forEach(System.out::println);

        Stream.of("barbara", "joe", "frank", "helen",
                "thomas", "jamie", "rob").forEach(name ->
                userRepository.save(new ApplicationUser(name, bCryptPasswordEncoder.encode("pass"), "ROLE_HOST"))
        );
        //userRepository.findAll().forEach(System.out::println);

        Stream.of("timmie", "kate", "Lemmy", "kilmeister",
                "morrow", "don", "cob").forEach(name ->
                userRepository.save(new ApplicationUser(name, bCryptPasswordEncoder.encode("pass"), "ROLE_GUEST"))
        );
        ApplicationUser myAdmin = new ApplicationUser("manager", "pass", "ROLE_GUEST");
        userRepository.save(myAdmin);
        MyUserPrinciple userPrinciple = new MyUserPrinciple(myAdmin);
/*        Property toComment = propertyRepository.findDistinctByPropname("PBR");
        long idTocomment = toComment.getPropID();
        Stream.of("EasyBeds", "Good Times", "FunPoolStay", "At the park",
                "Budweiser", "Coors Light", "PBR").forEach(name ->
                ratingRepository.save(new Rating(name, 3, idTocomment))
        );*/
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrinciple.getUsername(), null, userPrinciple.getAuthorities());
        getContext().setAuthentication(authentication);
        insertOwnedObjects();
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        authorities.stream().forEach(auth ->
                System.out.println(((SimpleGrantedAuthority) auth).getAuthority()));
        SecurityContextHolder.clearContext();
    }

    public void insertOwnedObjects() {
        Stream.of("EasyBeds", "Good Times", "FunPoolStay", "At the park",
                "Budweiser", "Coors Light", "PBR").forEach(name ->
                propertyService.createAndTest(new Property(name, name, name, name, name.length(), (float) 0.0), "rob")
        );
        propertyRepository.findAll().forEach(property ->
                propertyService.createRatingAndTest(new Rating("badPlace2Stay", 2, property.getPropID()), "don"));

    }


}
