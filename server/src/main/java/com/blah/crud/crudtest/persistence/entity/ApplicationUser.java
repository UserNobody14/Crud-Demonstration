package com.blah.crud.crudtest.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Table(name = "application_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
public class ApplicationUser implements UserDetails, Serializable {

  protected ApplicationUser() {}

  public ApplicationUser(String username, String password, String authority) {
    this.setUsername(username);
    this.setPassword(password);
    this.setAuthorityr(authority);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @Column(name = "USER_NAME")
  private String username;

  @Column(name = "PASSWORD")
  private String password;

  /*
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "USERS_AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
  @OrderBy
  @JsonIgnore
  */

  @Column(name = "authorityr")
  private String authorityr;

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<SimpleGrantedAuthority> getAuthorities() {
      ArrayList a = new ArrayList<SimpleGrantedAuthority>();
      a.add(new SimpleGrantedAuthority(this.authorityr));
      return a;
    //return new ArrayList<Authority>(Arrays.asList(new SimpleGrantedAuthority[] {new SimpleGrantedAuthority(this.authorityr)}));
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
  //end oddity

}
