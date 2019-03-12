package com.blah.crud.crudtest.authuser;

import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Stream;

public class MyUserPrinciple implements UserDetails {

    private ApplicationUser myUser;

    public MyUserPrinciple(ApplicationUser user) {
        this.myUser = user;
    }

    //

    @Override
    public String getUsername() {
        return myUser.getUsername();
    }

    @Override
    public String getPassword() {
        return myUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return establishAuthorities();
        //final List<? extends GrantedAuthority> authorities = establishAuthorities();
        //return authorities;
    }

    @NonNull
    private List<SimpleGrantedAuthority> establishAuthorities() {
        return new ArrayList<SimpleGrantedAuthority>(
                (Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                                new SimpleGrantedAuthority(myUser.getAuthorityr()))));
//        a.addAll(Arrays.asList(new SimpleGrantedAuthority("User"),
//                new SimpleGrantedAuthority(myUser.getAuthorityr())));
//        Stream.of("User", myUser.getAuthorityr()).forEach(authority ->
//                new SimpleGrantedAuthority(authority));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //

    public ApplicationUser getAppUser() {
        return myUser;
    }
}
