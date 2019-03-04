package com.blah.crud.crudtest.persistence.entity;

//import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "USER_AUTHORITIES")
public class Authority implements GrantedAuthority {

    protected Authority() {}

    public Authority(String name) {
        this.setName(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}