package com.blah.crud.crudtest;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.blah.crud.crudtest.persistence.entity.Property;
import com.blah.crud.crudtest.persistence.repository.ApplicationUserRepository;
import com.blah.crud.crudtest.persistence.repository.PropertyRepository;
import com.blah.crud.crudtest.persistence.repository.RatingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DataInitializeAndTest implements CommandLineRunner {

    private final ApplicationUserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final PropertyRepository propertyRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataInitializeAndTest(ApplicationUserRepository userRepository,
                          RatingRepository ratingRepository,
                          PropertyRepository propertyRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.propertyRepository = propertyRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("EasyBeds", "Good Times", "FunPoolStay", "At the park",
                "Budweiser", "Coors Light", "PBR").forEach(name ->
                propertyRepository.save(new Property(name, name, name, name, name.length(), (float) 0.0))
        );
        propertyRepository.findAll().forEach(System.out::println);

        Stream.of("barbara", "joe", "frank", "helen",
                "thomas", "jamie", "rob").forEach(name ->
                userRepository.save(new ApplicationUser(name, bCryptPasswordEncoder.encode("pass"), "ROLE_HOST"))
        );
        userRepository.findAll().forEach(System.out::println);
    }


}