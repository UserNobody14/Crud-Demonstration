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
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

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
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userPrinciple.getUsername(),
                        null, userPrinciple.getAuthorities());
        MyUserPrinciple katy = new MyUserPrinciple(userRepository.findByUsername("kate"));
        UsernamePasswordAuthenticationToken authentication2 =
                new UsernamePasswordAuthenticationToken(katy.getUsername(), null, katy.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        insertOwnedObjects();
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        authorities.forEach(auth ->
                System.out.println(((SimpleGrantedAuthority) auth).getAuthority()));
        SecurityContextHolder.clearContext();
        ////////////////////////////////
        //Test the ability to pre-filter items by the current users ability to edit them.
        getRatings(authentication2);
    }

    public void insertOwnedObjects() {
        /*Stream.of("EasyBeds", "Good Times", "FunPoolStay", "At the park",
                "Budweiser", "Coors Light", "PBR").forEach(name ->
                propertyService.createAndTest(new Property(name, name, name, name, name.length(), (float) 0.0), "rob")
        );*/
        altAddProperties();
        propertyRepository.findAll().forEach(property -> {
            propertyService.createRatingAndTest(new Rating("badPlace2Stay",
                    2, property.getPropID()), "don");
            propertyService.createRatingAndTest(new Rating( "sucks", 3, property.getPropID()),
                    "kate");
        });

    }
    private static <T1, T2> void iterateSimultaneously(Iterable<T1> c1, Iterable<T2> c2, BiConsumer<T1, T2> consumer) {
        Iterator<T1> i1 = c1.iterator();
        Iterator<T2> i2 = c2.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            consumer.accept(i1.next(), i2.next());
        }
    }
    //
    private void altAddProperties() {
        List<Property> addingProperties =
                Arrays.asList(new Property("Seaside"),
                        new Property("Great waterworks"),
                        new Property("Fun times Resort"),
                        new Property("Six flags of water parks"),
                        new Property("Great bear resort"));
        //Stream.of("543 Southside Drive").forEach(address -> propertyIterator.next().setAddress(address));

        Iterator<String> addresses = Stream.of("543 Southside Drive", "542 Blixxard hollow", "422 Bargnard Way",
        "412 Bagrassman Drive", "Blakely Street", "731 Gromagon Street").iterator();
        Iterator<String> descriptions = Stream.of("A great place to stay with family, easy to reach.",
                "An easy seasinde escape", "You'll love it here, we gaurantee.", "our pools are the best!",
                "Beaches fit for a king!", "The disneyland of water!").iterator();
        Iterator<String> poolsizes = Stream.of("big", "small", "miniscule", "massive", "medium", "small").iterator();

        for (Property p : addingProperties) {
            p.setAddress(addresses.next());
            p.setPoolsize(poolsizes.next());
            p.setDescription(descriptions.next());
            propertyService.createAndTest(p, "rob");
        }
    }
    private void getRatings(UsernamePasswordAuthenticationToken auth) {
        System.out.println("Seeking RatingsLists.............");
        SecurityContextHolder.getContext().setAuthentication(auth);
        List<Rating> r = ratingRepository.findAll();
        ArrayList<Rating> r2 = new ArrayList<Rating>(r);
        ArrayList<Rating> r3 = new ArrayList<Rating>(r);
        r.forEach(rating -> rating.setUserCanEdit(false));
        r2.forEach(rating -> rating.setUserCanEdit(false));
        r3.forEach(rating -> rating.setUserCanEdit(false));

//        r2.forEach(rating -> {
//            System.out.println(rating.isUserCanEdit());
//            System.out.println(propertyService.getOwner(rating));
//        });
//        setWhichAreEditable(r2);
        //hmakeOwnedItemsEditable(r);
        r.forEach(rating -> System.out.println(rating.isUserCanEdit()));
//        System.out.println("now trying r3");
//        r3.forEach(rating -> System.out.println(rating.isUserCanEdit()));
        SecurityContextHolder.clearContext();
    }


    @PreFilter("filterObject.owner == authentication.name")
    public void setWhichAreEditable(List<Rating> rList) {
        System.out.println("FilteringObject");
        rList.forEach(rating -> rating.setUserCanEdit(true));
        rList.forEach(rating -> System.out.println(rating.isUserCanEdit()));
        System.out.println("FilteringObject");
    }

    @PreFilter("hasPermission(filterObject, 'WRITE')")
    public void altSetEditability(List<Rating> rlist) {
        for (Rating r : rlist
             ) {
            r.setUserCanEdit(true);
        }
    }


}
